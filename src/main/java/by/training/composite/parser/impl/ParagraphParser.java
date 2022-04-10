package by.training.composite.parser.impl;


import by.training.composite.composite.TextComposite;
import by.training.composite.composite.TextLevel;
import by.training.composite.parser.ComponentTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser implements ComponentTextParser {

    private static final String REGEX_SENTENCE = "\\p{Upper}.*?(?:[?!.])|(?:[.]{3})";

    @Override
    public TextComposite parse(String str) {
        TextComposite paragraphComposite = new TextComposite();
        paragraphComposite.setLevel(TextLevel.PARAGRAPH);
        Pattern pattern = Pattern.compile(REGEX_SENTENCE);
        Matcher matcher = pattern.matcher(str);
        SentenceParser parser = new SentenceParser();
        while (matcher.find()) {
            String sentence = matcher.group();
            TextComposite sentenceComposite = parser.parse(sentence);
            paragraphComposite.add(sentenceComposite);
        }
        return paragraphComposite;
    }
}
