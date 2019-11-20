package com.softteco.textanalyzer.service;

import java.util.Collection;


public interface SentenceAnalyzerService {

    Collection<String> getSentencesOrderedByScore(Collection<String> textSentences, String question);
}
