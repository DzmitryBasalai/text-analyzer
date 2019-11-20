package com.softteco.textanalyzer.service;

import com.softteco.textanalyzer.model.ParsedInput;


public interface InputParserService {

    ParsedInput parse(String input);
}
