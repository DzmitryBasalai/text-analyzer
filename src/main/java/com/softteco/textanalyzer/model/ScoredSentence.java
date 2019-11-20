package com.softteco.textanalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ScoredSentence {
    private String sentence;
    private Integer score;
}
