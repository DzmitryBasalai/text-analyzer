package com.softteco.textanalyzer.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
class SentenceAnalyzerServiceTest {

    @Autowired
    private SentenceAnalyzerService sentenceAnalyzerService;

    @Test
    void getSentencesOrderedByScore() {
        String s_0 = "Unlike their closest relatives, horses and donkeys, zebras have never been truly domesticated";
        String s_1 = "Grévy's zebra and the mountain zebra are endangered";
        String s_2 = "There are three species of zebras: the plains zebra, the Grévy's zebra and the mountain zebra";

        String q = "Which Zebras are endangered?";


        Collection<String> result = sentenceAnalyzerService.getSentencesOrderedByScore(Arrays.asList(s_0, s_1, s_2), q);

        assertEquals(Arrays.asList(s_1, s_2, s_0), result);
    }
}