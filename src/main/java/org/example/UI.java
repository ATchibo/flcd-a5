package org.example;

import org.example.domain.NonTerminal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class UI {

//    src/main/resources/g1.txt
//    src/main/resources/g2.txt
    private Grammar grammar;

    public UI() {
        grammar = new Grammar("g6.txt");
        try {
            grammar.readGrammarFromFile();
            System.out.println("g1.txt loaded by default");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void run() {

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Read grammar from file");
            System.out.println("2. Print grammar");
            System.out.println("3. Print terminals");
            System.out.println("4. Print non-terminals");
            System.out.println("5. Print productions");
            System.out.println("6. Print productions of a given non-terminal");
            System.out.println("7. Check if grammar is CFG");
            System.out.println("8. Print FIRST for a given non-terminal");
            System.out.println("9. Print FOLLOW for a given non-terminal");

            Scanner scanner = new Scanner(System.in);
            int command = scanner.nextInt();

            switch (command) {
                case 0:
                    return;
                case 1:
                    System.out.println("Enter file name:");
                    String fileName = scanner.next();
                    grammar = new Grammar(fileName);

                    try {
                        grammar.readGrammarFromFile();
                        System.out.println("File read successfully");
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    } catch (Exception e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println(grammar);
                    break;
                case 3:
                    System.out.println(grammar.getTerminals());
                    break;
                case 4:
                    System.out.println(grammar.getNonTerminals());
                    break;
                case 5:
                    System.out.println(grammar.getProductions());
                    break;
                case 6: {
                    System.out.println("Enter non-terminal: ");
                    String nonTerminal = scanner.next();
                    System.out.println(grammar.getProductionsOfNonTerminal(nonTerminal));
                }
                    break;
                case 7:
                    System.out.println(grammar.isContextFreeGrammar());
                    break;
                case 8:
                    System.out.println("Enter non-terminal: ");
                    String nonTerminalName = scanner.next();
                    NonTerminal nonTerminal = grammar.getNonTerminalFromString(nonTerminalName);
                    if (nonTerminal == null) {
                        System.out.println("Non-terminal " + nonTerminalName + " not found in the current grammar");
                    }
                    else {
                        System.out.println(grammar.getFirst(nonTerminal));
                    }
                    break;
                case 9:
                    System.out.println("Enter non-terminal: ");
                    String nonTerminalName2 = scanner.next();
                    NonTerminal nonTerminal2 = grammar.getNonTerminalFromString(nonTerminalName2);
                    if (nonTerminal2 == null) {
                        System.out.println("Non-terminal " + nonTerminalName2 + " not found in the current grammar");
                    }
                    else {
                        System.out.println(grammar.getFollow(nonTerminal2));
                    }
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
