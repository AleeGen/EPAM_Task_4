package by.training.composite.parser.impl;

import by.training.composite.composite.Symbol;
import by.training.composite.composite.TextComponent;
import by.training.composite.composite.TextComposite;
import by.training.composite.composite.TextLevel;
import by.training.composite.service.ExpressionParser;
import by.training.composite.parser.ComponentTextParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser implements ComponentTextParser {

    private static final String REGEX_GROUP = "(-?[0-9(][0-9.+\\-*/()]+(?:[0-9)]))|(\\p{L}+)|(\\d+)|([,.\\-;!?()])";
    private static final String REGEX_SYMBOL = ".";

    @Override
    public TextComposite parse(String str) {
        TextComposite lexemeComposite = new TextComposite();
        lexemeComposite.setLevel(TextLevel.LEXEME);
        Pattern pattern = Pattern.compile(REGEX_GROUP);
        Matcher matcher = pattern.matcher(str);
        String found;
        while (matcher.find()) {
            TextComposite partComposite = new TextComposite();
            if (matcher.group(1) != null) {
                found = matcher.group(1);
                String result = ExpressionParser.parse(found);
                if (result.equals(found)) {
                    for (TextComponent a : parseSymbol(result).getComponents()) {
                        lexemeComposite.add(a);
                    }
                    continue;
                } else {
                    partComposite = parseSymbol(result);
                    partComposite.setLevel(TextLevel.DIGIT);
                }
            } else if (matcher.group(2) != null) {
                partComposite = parseSymbol(matcher.group(2));
                partComposite.setLevel(TextLevel.WORD);
            } else if (matcher.group(3) != null) {
                partComposite = parseSymbol(matcher.group(3));
                partComposite.setLevel(TextLevel.DIGIT);
            } else if (matcher.group(4) != null) {
                partComposite = parseSymbol(matcher.group(4));
                partComposite.setLevel(TextLevel.PUNCTUATION);
            }
            lexemeComposite.add(partComposite);
        }
        return lexemeComposite;
    }

    private TextComposite parseSymbol(String result) {
        TextComposite digitComposite = new TextComposite();
        Pattern pattern = Pattern.compile(REGEX_SYMBOL);
        Matcher matcher = pattern.matcher(result);
        SymbolParser parser = new SymbolParser();
        while (matcher.find()) {
            String found = matcher.group();
            Symbol symbol = parser.parse(found);
            digitComposite.add(symbol);
        }
        return digitComposite;
    }
}
