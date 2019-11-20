package com.softteco.textanalyzer.service;

import com.softteco.textanalyzer.model.ParsedInput;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Service
public class InputParserServiceImpl implements InputParserService {

    @Value("${input.line.delimiter}")
    private String lineDelimiter;

    @Value("${input.line.text}")
    private int textLine;

    @Value("${input.line.questions}")
    private int[] questionLines;

    @Value(("${input.line.answers}"))
    private int answersLine;

    @Override
    public ParsedInput parse(String input) {
        String[] lines = input.split(lineDelimiter);
        return ParsedInput.builder()
                .textSentences(parseTextSentences(lines))
                .questions(parseQuestions(lines))
                .answers(parseAnswers(lines))
                .build();
    }

    private Collection<String> parseTextSentences(String[] lines) {
        return Stream.of(lines[textLine].split("\\."))
                .filter(StringUtils::isNoneBlank)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private Collection<String> parseQuestions(String[] lines) {
        return IntStream.of(questionLines)
                .boxed()
                .map(i -> lines[i])
                .filter(StringUtils::isNoneBlank)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private Collection<String> parseAnswers(String[] lines) {
        return Stream.of(lines[answersLine].split(";"))
                .filter(StringUtils::isNoneBlank)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
