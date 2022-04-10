package by.training.composite.parser.impl;

import by.training.composite.composite.TextComposite;
import by.training.composite.composite.TextLevel;
import by.training.composite.parser.ComponentTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser implements ComponentTextParser {

    private static final String REGEX_LEXEME = "[^\s]+";

    @Override
    public TextComposite parse(String str) {
        TextComposite sentenceComposite = new TextComposite();
        sentenceComposite.setLevel(TextLevel.SENTENCE);
        Pattern pattern = Pattern.compile(REGEX_LEXEME);
        Matcher matcher = pattern.matcher(str);
        LexemeParser parser = new LexemeParser();
        while (matcher.find()) {
            String lexeme = matcher.group();
            TextComposite lexemeComposite = parser.parse(lexeme);
            sentenceComposite.add(lexemeComposite);
        }
        return sentenceComposite;
    }
}
