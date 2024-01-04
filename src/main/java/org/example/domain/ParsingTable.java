package org.example.domain;

import com.inamik.text.tables.Cell;
import com.inamik.text.tables.GridTable;
import com.inamik.text.tables.grid.Border;
import org.example.Grammar;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class ParsingTable {

    private Grammar grammar;
    private HashMap<Term, HashMap<Terminal, String>> parsingTableMap;

    public ParsingTable(Grammar grammar) {
        this.grammar = grammar;
        parsingTableMap = new HashMap<>();
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
                    toAdd = (rowTerminal == Grammar.DOLLAR ? "accept" : "pop");
                }
                parsingTableMap.get(rowTerminal).put(columnTerminal, toAdd);
            }
        }
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

        table = Border.SINGLE_LINE.apply(table);

        return table;
    }
}
