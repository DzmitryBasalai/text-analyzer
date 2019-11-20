package com.softteco.textanalyzer.service;

import com.softteco.textanalyzer.model.ParsedInput;
import com.softteco.textanalyzer.validator.ValidateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TextAnalyzerServiceImpl implements TextAnalyzerService {

    private final InputParserService inputParserService;
    private final SentenceAnalyzerService sentenceAnalyzerService;

    @Override
    public String getOrderedAnswers(@ValidateInput String input) {

        ParsedInput parsedInput = inputParserService.parse(input);

        return parsedInput.getQuestions().stream()
                .map(q -> getAnswer(parsedInput.getTextSentences(), q, parsedInput.getAnswers()))
                .collect(Collectors.joining("\n"));
    }

    private String getAnswer(Collection<String> textSentences, String question, Collection<String> answers) {
        Set<String> targetSentences = getTargetSentences(textSentences, answers);
        return sentenceAnalyzerService.getSentencesOrderedByScore(targetSentences, question).stream()
                .map(s -> getAnswer(s, answers))
                .findFirst()
                .orElse("NOT FOUND");
    }

    private Set<String> getTargetSentences(Collection<String> textSentences, Collection<String> answers) {
        return textSentences.stream()
                .filter(s -> answers.stream().anyMatch(a -> s.toLowerCase().contains(a.toLowerCase())))
                .collect(Collectors.toSet());
    }

    private String getAnswer(String sentence, Collection<String> answers) {
        return answers.stream()
                .filter(a -> sentence.toLowerCase().contains(a.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("One of the answers should be exact substrings of a TARGET sentence '%s' ", sentence)));
    }

}
