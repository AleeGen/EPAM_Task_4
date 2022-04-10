package by.training.composite.service;

import by.training.composite.composite.TextComponent;
import by.training.composite.composite.TextComposite;
import by.training.composite.exception.CustomException;

import java.util.Map;

public interface TextComponentService {
    boolean sortParagraphs(TextComponent component) throws CustomException;
    TextComponent findSentences(TextComponent component);
    boolean deleteSentences(TextComposite component, int minCountWord);
    Map<String,Integer> searchIdenticalWords(TextComponent component) throws CustomException;
    Map<String,Integer> countVowelConsonant(TextComponent component) throws CustomException;

}
