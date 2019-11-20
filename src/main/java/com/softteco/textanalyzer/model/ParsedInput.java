package com.softteco.textanalyzer.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;


@Getter
@Builder
public class ParsedInput {
    private Collection<String> textSentences;
    private Collection<String> questions;
    private Collection<String> answers;
}
