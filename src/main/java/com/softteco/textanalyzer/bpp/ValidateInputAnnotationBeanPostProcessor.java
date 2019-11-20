package com.softteco.textanalyzer.bpp;

import com.softteco.textanalyzer.validator.ValidateInput;
import com.softteco.textanalyzer.validator.InputValidator;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.reflect.Proxy.newProxyInstance;


@Component
public class ValidateInputAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Collection<Pair<Method, Collection<Integer>>>> map = new HashMap<>();

    private InputValidator inputValidator;

    public ValidateInputAnnotationBeanPostProcessor(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();

        Collection<Pair<Method, Collection<Integer>>> list = Stream.of(declaredMethods)
                .map(method -> {
                    final Annotation[][] paramAnnotations = method.getParameterAnnotations();
                    final Class[] paramTypes = method.getParameterTypes();
                    Collection<Integer> paramPositions = new ArrayList<>();
                    for (int i = 0; i < paramAnnotations.length; i++) {
                        for (Annotation a : paramAnnotations[i]) {
                            if (a instanceof ValidateInput && String.class.equals(paramTypes[i])) {
                                paramPositions.add(i);
                            }
                        }
                    }
                    return new ImmutablePair<>(method, paramPositions);
                })
                .filter(p -> !p.getRight().isEmpty())
                .collect(Collectors.toList());

        if (!list.isEmpty()) {
            map.put(beanName, list);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        Collection<Pair<Method, Collection<Integer>>> pairs = map.get(beanName);
        if (Objects.isNull(pairs)) {
            return bean;
        }

        return newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {


            Pair<Method, Collection<Integer>> methodCollectionPair = pairs.stream()
                    .filter(p -> isSameMethods(p.getLeft(), method))
                    .findFirst()
                    .orElse(null);

            if (methodCollectionPair == null) {
                return method.invoke(bean, args);
            } else {
                for (Integer i : methodCollectionPair.getRight()) {
                    inputValidator.validate((String) args[i]);
                }
                return method.invoke(bean, args);
            }
        });
    }

    private boolean isSameMethods(Method methodA, Method methodB) {
        if (methodA == null || methodB == null) {
            return false;
        }

        List<?> parameterTypesA = Arrays.asList(methodA.getParameterTypes());
        List<?> parameterTypesB = Arrays.asList(methodB.getParameterTypes());

        return methodA.getName().equals(methodB.getName())
                && parameterTypesA.equals(parameterTypesB);
    }
}
