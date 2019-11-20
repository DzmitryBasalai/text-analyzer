package com.softteco.textanalyzer.service;

import com.softteco.textanalyzer.model.ParsedInput;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
class InputParserServiceTest {

    @Autowired
    private InputParserService inputParserService;

    @Test
    void parse() {
        String input = "text sentence_0. sentence_1.\n" +
                "  q line_0\n" +
                " q_line_1    \n" +
                "         q_line_2     \n" +
                " q   line_3 \n" +
                " q_line_4  \n" +
                "a_0;   a_1; a_2";
        ParsedInput result = inputParserService.parse(input);
        assertEquals(Arrays.asList("text sentence_0", "sentence_1"), result.getTextSentences());
        assertEquals(Arrays.asList("q line_0", "q_line_1", "q_line_2", "q   line_3", "q_line_4"), result.getQuestions());
        assertEquals(Arrays.asList("a_0", "a_1", "a_2"), result.getAnswers());
    }
}
