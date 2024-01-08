package org.example.domain;

import com.inamik.text.tables.Cell;
import com.inamik.text.tables.GridTable;
import com.inamik.text.tables.grid.Border;
import org.example.Grammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class ParsingTable {

    private Grammar grammar;
    private HashMap<Term, HashMap<Terminal, String>> parsingTableMap;
    private HashMap<Term, HashMap<Terminal, String>> conflictMap;

    public ParsingTable(Grammar grammar) {
        this.grammar = grammar;
        parsingTableMap = new HashMap<>();
        conflictMap = new HashMap<>();
        computeParsingTable();
    }

    private void computeParsingTable() {
        Set<NonTerminal> nonTerminals = grammar.getNonTerminals();
        Set<Terminal> terminals = grammar.getTerminals();
        terminals.remove(Grammar.EPSILON);
        terminals.add(Grammar.DOLLAR);

        for (NonTerminal nonTerminal : nonTerminals) {
            parsingTableMap.put(nonTerminal, new HashMap<>());

            for (Terminal terminal : terminals) {
                parsingTableMap.get(nonTerminal).put(terminal, "");
            }
        }

        for (Terminal rowTerminal : terminals) {
            parsingTableMap.put(rowTerminal, new HashMap<>());

            for (Terminal columnTerminal : terminals) {
                String toAdd = "";
                if (rowTerminal == columnTerminal) {
                    toAdd = (rowTerminal == Grammar.DOLLAR ? "acc" : "pop");
                }
                parsingTableMap.get(rowTerminal).put(columnTerminal, toAdd);
            }
        }

        int i = 1;
        for (Production production : grammar.getProductions()) {
            NonTerminal sourceNonTerminal = production.getSourceNonTerminals().getFirst();
            String toAdd = production.getResultingTerms().stream()
                    .map(Term::toString)
                    .reduce("", (a, b) -> a + b);
            toAdd += "," + i;
            Set<Terminal> resultOfFirst = new HashSet<>();
            grammar.computeFirstForProduction(production, sourceNonTerminal, resultOfFirst);

            Set<Terminal> targetTerminals = resultOfFirst;
            if (resultOfFirst.contains(Grammar.EPSILON)) {
                targetTerminals = grammar.getFollow(sourceNonTerminal);
            }

            for (Terminal terminal : targetTerminals) {
                if (parsingTableMap.get(sourceNonTerminal).get(terminal) != null &&
                        !parsingTableMap.get(sourceNonTerminal).get(terminal).isEmpty()) {

                    if (!conflictMap.containsKey(sourceNonTerminal)) {
                        conflictMap.put(sourceNonTerminal, new HashMap<>());
                    }
                    conflictMap.get(sourceNonTerminal).put(terminal, toAdd);
                }

                if (!terminal.equals(Grammar.EPSILON)) {
                    parsingTableMap.get(sourceNonTerminal).put(terminal, toAdd);
                }
                else {
                    parsingTableMap.get(sourceNonTerminal).put(Grammar.DOLLAR, toAdd);
                }
            }

            i++;
        }
    }

    public void parse(String inputSequence) {

        Stack<Terminal> inputStack = new Stack<>();
        Stack<Term> workingStack = new Stack<>();
        Stack<Integer> outputStack = new Stack<>();

        inputStack.push(Grammar.DOLLAR);
        String[] inputSequenceSplit = inputSequence.split(" ");
        for (int i = inputSequenceSplit.length - 1; i >= 0; i--) {
            inputStack.push(new Terminal(inputSequenceSplit[i]));
        }

        workingStack.push(Grammar.DOLLAR);
        workingStack.push(grammar.getStartingSymbol());

        while (!inputStack.isEmpty()) {
            System.out.println("Input stack: " + inputStack);
            System.out.println("Working stack: " + workingStack);
            System.out.println("Output stack: " + outputStack);
            System.out.println("--------------------\n");

            while (workingStack.peek().equals(Grammar.EPSILON)) {
                workingStack.pop();
            }

            Term top = workingStack.peek();
            Terminal inputTop = inputStack.peek();
            String productionString = parsingTableMap.get(top).get(inputTop);

            if (productionString.equals("pop")) {
                workingStack.pop();
                inputStack.pop();
            }
            else {
                if (productionString.isEmpty()) { // is error
                    System.out.println("Input not accepted");
                    return;
                }
                else if (productionString.equals("acc")) {
                    System.out.println("Input accepted");

                    System.out.println("Output stack:");
                    System.out.println(outputStack);

                    return;
                }
                else {
                    String[] productionStringSplit = productionString.split(",");
                    int productionIndex = Integer.parseInt(productionStringSplit[1]);
                    Production production = grammar.getProductions().get(productionIndex - 1);
                    workingStack.pop();
                    for (int j = production.getResultingTerms().size() - 1; j >= 0; j--) {
                        workingStack.push(production.getResultingTerms().get(j));
                    }
                    outputStack.push(productionIndex);
                }
            }
        }

        System.out.println("Input not accepted");
    }

    public GridTable getTable() {
        GridTable table = new GridTable(
                parsingTableMap.size() + 1,
                parsingTableMap.get(Grammar.DOLLAR).size() + 1);
        // dollar is chosen as random, just want to know the size of cols

        table.put(0, 0, Cell.of(""));

        int i = 1;
        for (Terminal terminal: parsingTableMap.get(Grammar.DOLLAR).keySet())
            table.put(0, i++, Cell.of(terminal.toString()));

        i = 1;
        for (Term term : parsingTableMap.keySet())
            table.put(i++, 0, Cell.of(term.toString()));

        i = 1;
        for (Term rowTerm : parsingTableMap.keySet()) {
            int col = 1;
            for (String colTerminalValue : parsingTableMap.get(rowTerm).values()) {
                table.put(i, col++, Cell.of(colTerminalValue));
            }
            i++;
        }

        table = Border.SINGLE_LINE.apply(table);

        return table;
    }

    public String getConflictsString() {
        StringBuilder sb = new StringBuilder();

        for (Term nonTerminal : conflictMap.keySet()) {
            sb.append("Non-terminal ").append(nonTerminal).append(" has conflicts on terminals: ");
            sb.append(conflictMap.get(nonTerminal).keySet().stream()
                    .map(Terminal::toString)
                    .collect(Collectors.joining(", ")));
            sb.append("\n");
        }

        return sb.toString();
    }
}
