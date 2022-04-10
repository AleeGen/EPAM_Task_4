package by.training.composite.composite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Symbol implements TextComponent {

    private static final Logger logger = LogManager.getLogger();
    private static final TextLevel level = TextLevel.SYMBOL;
    private SymbolType type;
    private char symbolElement;

    private final List<TextComponent> component = new ArrayList<>();

    public Symbol(char symbol) {
        this.symbolElement = symbol;
        component.add(this);
    }

    public Symbol(SymbolType type, char symbol) {
        this.symbolElement = symbol;
        this.type = type;
        component.add(this);
    }

    @Override
    public boolean add(TextComponent component) {
        logger.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(TextComponent component) {
        logger.error("Unsupported operation exception");
        throw new UnsupportedOperationException();
    }

    @Override
    public String getText() {
        return String.valueOf(symbolElement);
    }

    @Override
    public TextLevel getTextLevel() {
        return level;
    }

    @Override
    public List<TextComponent> getComponents() {
        return component;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    public char getSymbolElement() {
        return symbolElement;
    }

    public void setSymbolElement(char symbolElement) {
        this.symbolElement = symbolElement;
    }

}
