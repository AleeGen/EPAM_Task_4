package by.training.composite.service.impl;

import by.training.composite.composite.*;
import by.training.composite.exception.CustomException;
import by.training.composite.service.TextComponentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TextComponentServiceImpl implements TextComponentService {

    private static final Logger logger = LogManager.getLogger();
    private static final String REGEX_VOWEL = "[eyuioaуеоаыяию]";
    private final TextComponent sentenceList = new TextComposite();
    private final Map<String, Integer> collectionWord = new HashMap<>();
    private final List<TextComponent> deleteComponent = new ArrayList<>();
    private int maxLetter = 0;
    private int vowel = 0;
    private int consonant = 0;

    @Override
    public boolean sortParagraphs(TextComponent component) {
        if (component.getTextLevel() == TextLevel.TEXT) {
            component.getComponents().sort(Comparator.comparingInt(o -> o.getComponents().size()));
        } else return false;
        return true;
    }

    @Override
    public TextComponent findSentences(TextComponent component) {
        if (component.getTextLevel() == TextLevel.PARAGRAPH) {
            for (var sentence : component.getComponents()) {
                findSentencesSupport(sentence);
            }
        } else if (component.getTextLevel() == TextLevel.TEXT) {
            for (var paragraph : component.getComponents()) {
                for (var sentence : paragraph.getComponents()) {
                    findSentencesSupport(sentence);
                }
            }
        }
        return sentenceList;
    }

    @Override
    public boolean deleteSentences(TextComposite component, int minCountWord) {
        if (component.getTextLevel() == TextLevel.TEXT) {
            for (var paragraph : component.getComponents()) {
                deleteSentenceSupport(paragraph, component, minCountWord);
            }
        } else if (component.getTextLevel() == TextLevel.PARAGRAPH) {
            deleteSentenceSupport(component, component, minCountWord);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Integer> searchIdenticalWords(TextComponent component) {
        Map<String, Integer> result = new HashMap<>();
        if (component.getTextLevel() == TextLevel.SENTENCE) {
            checkingAvailabilityWord(component);
        } else if (component.getTextLevel() == TextLevel.PARAGRAPH) {
            for (var sentence : component.getComponents()) {
                checkingAvailabilityWord(sentence);
            }
        } else if (component.getTextLevel() == TextLevel.TEXT) {
            for (var paragraph : component.getComponents()) {
                for (var sentence : paragraph.getComponents()) {
                    checkingAvailabilityWord(sentence);
                }
            }
        }
        for (var word : collectionWord.entrySet()) {
            if (word.getValue() > 1) {
                result.put(word.getKey(), word.getValue());
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> countVowelConsonant(TextComponent component) throws CustomException {
        if (component.getTextLevel() != TextLevel.SENTENCE) {
            logger.error("{} is not sentence", component);
            throw new CustomException();
        }
        for (var lexeme : component.getComponents()) {
            for (var element : lexeme.getComponents()) {
                if (element.getTextLevel() == TextLevel.SYMBOL) {
                    countingLetter(element);
                } else {
                    for (var symbol : element.getComponents()) {
                        countingLetter(symbol);
                    }
                }
            }
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("Vowel", vowel);
        result.put("Consonant", consonant);
        return result;
    }


    private void findSentencesSupport(TextComponent sentence) {
        for (var lexeme : sentence.getComponents()) {
            for (var element : lexeme.getComponents()) {
                if (element.getTextLevel() == TextLevel.WORD) {
                    if (element.getComponents().size() > maxLetter) {
                        maxLetter = element.getComponents().size();
                        sentenceList.getComponents().clear();
                        sentenceList.add(sentence);
                    } else if (element.getComponents().size() == maxLetter) {
                        sentenceList.add(sentence);
                    }
                }
            }
        }
    }

    private void countingLetter(TextComponent element) {
        if (((Symbol) element).getType() == SymbolType.LETTER) {
            if (element.getText().matches(REGEX_VOWEL)) {
                vowel++;
            } else {
                consonant++;
            }
        }
    }

    private void checkingAvailabilityWord(TextComponent sentence) {
        for (var lexeme : sentence.getComponents()) {
            for (var word : lexeme.getComponents()) {
                if (word.getTextLevel() == TextLevel.WORD) {
                    String key = word.getText().toLowerCase(Locale.ROOT);
                    if (collectionWord.containsKey(key)) {
                        collectionWord.put(key, collectionWord.get(key) + 1);
                    } else collectionWord.put(key, 1);
                }
            }
        }
    }

    private void deleteSentenceSupport(TextComponent paragraph, TextComponent component, int minCountWord) {
        for (var sentence : paragraph.getComponents()) {
            if (sentence.getComponents().size() < minCountWord) {
                deleteComponent.add(sentence);
            }
        }
        for (var sentence : deleteComponent) {
            component.remove(sentence);
        }
    }
}

