package by.training.composite.composite;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent {

    private final List<TextComponent> components;
    private TextLevel level;

    public TextComposite() {
        components = new ArrayList<>();
    }

    public TextComposite(TextLevel level) {
        components = new ArrayList<>();
        this.level = level;
    }

    public TextComposite(List<TextComponent> components) {
        this.components = components;
    }

    @Override
    public boolean add(TextComponent component) {

        return components.add(component);
    }

    @Override
    public boolean remove(TextComponent component) {
        components.remove(component);
        return components.remove(component);
    }

    public List<TextComponent> getComponents() {
        return components;
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        String strStart = "";
        if (level == TextLevel.TEXT) {
            strStart = "\n\t";
        } else if (level == TextLevel.PARAGRAPH || level == TextLevel.SENTENCE) {
            strStart = "\s";
        }
        boolean firstElement = true;
        for (TextComponent component : components) {
            if (firstElement && level != TextLevel.TEXT) {
                result.append(component.getText());
            } else {
                result.append(strStart).append(component.getText());
            }
            firstElement = false;
        }
        if (level == TextLevel.TEXT) {
            result.delete(0, 1);
        }
        return result.toString();
    }

    @Override
    public TextLevel getTextLevel() {
        return level;
    }

    public void setLevel(TextLevel level) {
        this.level = level;
    }

}
