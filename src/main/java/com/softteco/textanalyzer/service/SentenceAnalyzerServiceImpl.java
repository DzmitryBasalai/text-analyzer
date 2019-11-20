package com.softteco.textanalyzer.service;

import com.softteco.textanalyzer.model.ScoredSentence;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;


@Service
public class SentenceAnalyzerServiceImpl implements SentenceAnalyzerService {

    @Override
    public Collection<String> getSentencesOrderedByScore(Collection<String> textSentences, String question) {
        return textSentences.stream()
                .map(s -> new ScoredSentence(s, FuzzySearch.tokenSetRatio(question.toLowerCase(), s.toLowerCase())))
                .sorted(Comparator.comparing(ScoredSentence::getScore).reversed())
                .map(ScoredSentence::getSentence)
                .collect(Collectors.toList());
    }
}
