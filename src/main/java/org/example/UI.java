package org.example;

import com.inamik.text.tables.GridTable;
import com.inamik.text.tables.grid.Util;
import org.example.domain.NonTerminal;
import org.example.domain.ParsingTable;
import org.example.domain.ParsingTree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class UI {

//    src/main/resources/g1.txt
//    src/main/resources/g2.txt
    private Grammar grammar;

    public UI() {
        grammar = new Grammar("g7.txt");
        try {
            grammar.readGrammarFromFile();
            System.out.println("g7.txt loaded by default");
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
            System.out.println("10. Print LL(1) parsing table");
            System.out.println("11. Parse string using LL(1) parsing table");

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
                    break;
                }
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
                case 10:
                    GridTable table = grammar.getLL1ParsingTable().getTable();
                    Util.print(table);

                    String conflictsTable = grammar.getLL1ParsingTable().getConflictsString();
                    System.out.println(conflictsTable);

                    break;
                case 11:
                    System.out.println("Enter string to parse: ");
                    String stringToParse = scanner.nextLine();
                    stringToParse = scanner.nextLine();

                    ParsingTable parsingTable = grammar.getLL1ParsingTable();
                    Stack<Integer> outputStack = parsingTable.parse(stringToParse);
                    if (outputStack != null) {
                        ParsingTree parsingTree = new ParsingTree(grammar, outputStack);
                        parsingTree.generateTree();
                    }
                  break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
}
