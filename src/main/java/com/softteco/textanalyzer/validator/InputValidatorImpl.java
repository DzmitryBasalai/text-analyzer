package com.softteco.textanalyzer.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class InputValidatorImpl implements InputValidator {

    @Value("${input.line.delimiter}")
    private String lineDelimiter;

    @Value("${input.line.text}")
    private int textLine;

    @Value("${input.line.questions}")
    private int[] questionLines;

    @Value(("${input.line.answers}"))
    private int answersLine;

    @Override
    public void validate(String input) {
        String[] lines = input.split(lineDelimiter);
        if (lines.length < 3 || lines.length != answersLine + 1) {
            throw new RuntimeException(
                    String.format("invalid input %s. Expected format: line %s-text; lines %s-questions; line %s-answers"
                            , input
                            , textLine
                            , Arrays.toString(questionLines)
                            , answersLine));
        }
    }

}
