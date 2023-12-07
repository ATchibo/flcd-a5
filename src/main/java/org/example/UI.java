package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class UI {

//    src/main/resources/g1.txt
//    src/main/resources/g2.txt
    private Grammar grammar;

    public UI() throws FileNotFoundException {
        grammar = new Grammar("g1.txt");
        grammar.readGrammarFromFile();
        System.out.println("g1.txt loaded by default");
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
                case 6:
                    System.out.println("Enter non-terminal: ");
                    String nonTerminal = scanner.next();
                    System.out.println(grammar.getProductionsOfNonTerminal(nonTerminal));
                    break;
                case 7:
                    System.out.println(grammar.isContextFreeGrammar());
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
