package by.training.composite;

import by.training.composite.composite.*;
import by.training.composite.parser.impl.TextParser;
import by.training.composite.service.impl.TextComponentServiceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final String PATH = ClassLoader.getSystemResource("text.txt").getPath();

    public static void main(String[] args) throws IOException {

        StringBuilder text = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))) {
            while (bufferedReader.ready()) {
                text.append(bufferedReader.readLine()).append("\n");
            }
        }
        TextComposite textComposite = new TextParser().parse(text.toString());
        TextComponentServiceImpl service = new TextComponentServiceImpl();
        service.sortParagraphs(textComposite);
    }
}
