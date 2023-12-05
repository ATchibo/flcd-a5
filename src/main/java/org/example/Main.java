package org.example;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        Grammar g = new Grammar("src/main/resources/grammar.in");

        try {
            g.readGrammarFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(g);
    }
}