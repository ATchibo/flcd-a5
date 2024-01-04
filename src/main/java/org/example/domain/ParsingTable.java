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
    private HashMap<Pair<Term, Term>, String> parsingTableMap;

    public ParsingTable(Grammar grammar) {
        this.grammar = grammar;
        parsingTableMap = new HashMap<>();
        computeParsingTable();
    }

    private void computeParsingTable() {
        Set<Term> terms = grammar.getTerminalsAndNonTerminals();
        terms.remove(Grammar.EPSILON);
        for (Term term : terms) {
            parsingTableMap.put(new Pair<>(term, term), "pop");
        }
        parsingTableMap.put(new Pair<>(Grammar.DOLLAR, Grammar.DOLLAR), "accept");
    }

    public GridTable getTable() {
        Set<Terminal> terminals = grammar.getTerminals();
        terminals.remove(Grammar.EPSILON);
        Set<NonTerminal> nonTerminals = grammar.getNonTerminals();

        GridTable table = new GridTable(
                terminals.size() + nonTerminals.size() + 2,
                terminals.size() + 2);

        table.put(0, 0, Cell.of(""));

        int i = 1;
        for (Terminal terminal: terminals)
            table.put(0, i++, Cell.of(terminal.toString()));
        table.put(0, i, Cell.of(Grammar.DOLLAR.toString()));

        i = 1;
        for (NonTerminal nonTerminal: nonTerminals)
            table.put(i++, 0, Cell.of(nonTerminal.toString()));
        for (Terminal terminal: terminals)
            table.put(i++, 0, Cell.of(terminal.toString()));
        table.put(i, 0, Cell.of(Grammar.DOLLAR.toString()));

        table = Border.SINGLE_LINE.apply(table);

        return table;
    }
}
