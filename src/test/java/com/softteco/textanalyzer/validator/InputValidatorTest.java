package com.softteco.textanalyzer.validator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RunWith(SpringRunner.class)
class InputValidatorTest {

    @Autowired
    private InputValidator inputValidator;

    @Test
    void validatePositive() {
        String input = "text_line\n" +
                "q_line_0\n" +
                "q_line_1\n" +
                "q_line_2\n" +
                "q_line_3\n" +
                "q_line_4\n" +
                "a_line";
        inputValidator.validate(input);
    }

    @Test
    void validateNegative() {
        String input = "text_line\n" +
                "q_line_1\n" +
                "q_line_2\n" +
                "q_line_3\n" +
                "q_line_4\n" +

                "a_line";
        assertThrows(RuntimeException.class, () ->
                inputValidator.validate(input)
        );
    }

}
