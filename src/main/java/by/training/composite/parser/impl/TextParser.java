package by.training.composite.parser.impl;


import by.training.composite.composite.TextComposite;
import by.training.composite.composite.TextLevel;
import by.training.composite.parser.ComponentTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements ComponentTextParser {

    private static final String REGEX_PARAGRAPH = "(\s{4}.+)|(\t.+)";

    @Override
    public TextComposite parse(String str) {
        TextComposite textComposite = new TextComposite();
        textComposite.setLevel(TextLevel.TEXT);
        Pattern pattern = Pattern.compile(REGEX_PARAGRAPH);
        Matcher matcher = pattern.matcher(str);
        ParagraphParser parser = new ParagraphParser();
        while (matcher.find()) {
            String paragraph = matcher.group();
            TextComposite paragraphComposite = parser.parse(paragraph);
            textComposite.add(paragraphComposite);
        }
        return textComposite;
    }

}




