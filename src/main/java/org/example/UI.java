package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class UI {

//    src/main/resources/g1.txt
//    src/main/resources/g2.txt
    private Grammar g;

    public UI() {}

    public void run() {

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Read grammar from file");
            System.out.println("2. Print grammar");
            System.out.println("3. Print terminals");
            System.out.println("4. Print non-terminals");
            System.out.println("5. Print productions");
            System.out.println("6. Print productions of a given non-terminal");
            System.out.println("7. Check if grammar is LL(1)");

            Scanner scanner = new Scanner(System.in);
            int command = scanner.nextInt();

            switch (command) {
                case 0:
                    return;
                case 1:
                    String filePath = scanner.next();
                    g = new Grammar(filePath);

                    try {
                        g.readGrammarFromFile();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    break;
                case 2:
                    System.out.println(g);
                    break;
                case 3:
                    System.out.println(g.getTerminals());
                    break;
                case 4:
                    System.out.println(g.getNonTerminals());
                    break;
                case 5:
                    System.out.println(g.getProductions());
                    break;
                case 6:
                    System.out.println("Enter non-terminal: ");
                    String nonTerminal = scanner.next();
                    System.out.println(g.getProductionsOfNonTerminal(nonTerminal));
                    break;
                case 7:
                    System.out.println(g.isLL1());
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
