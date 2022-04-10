package by.training.composite.composite;

import java.util.List;

public interface TextComponent {
    boolean add(TextComponent component);

    boolean remove(TextComponent component);

    String getText();

    TextLevel getTextLevel();

    List<TextComponent> getComponents();
}
