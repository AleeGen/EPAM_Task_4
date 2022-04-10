package by.training.composite.parser.impl;

import by.training.composite.composite.*;
import by.training.composite.parser.ComponentTextParser;

public class SymbolParser implements ComponentTextParser {

    @Override
    public Symbol parse(String str) {
        Symbol symbol = new Symbol(str.charAt(0));
        if (Character.isLetter(str.charAt(0))) {
            symbol.setType(SymbolType.LETTER);
        } else if (Character.isDigit(str.charAt(0))) {
            symbol.setType(SymbolType.DIGIT);
        } else {
            symbol.setType(SymbolType.PUNCTUATION);
        }
        return symbol;
    }
}
