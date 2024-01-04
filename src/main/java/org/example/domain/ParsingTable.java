package org.example.domain;

import com.inamik.text.tables.Cell;
import com.inamik.text.tables.GridTable;
import com.inamik.text.tables.grid.Border;

import java.util.Set;

public class ParsingTable {

    private Set<Terminal> terminals;
    private Set<NonTerminal> nonTerminals;

    public ParsingTable(Set<Terminal> terminals, Set<NonTerminal> nonTerminals) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
    }

    public GridTable getTable() {

        GridTable table = new GridTable(
                terminals.size() + nonTerminals.size() + 2,
                terminals.size() + 2);

        table.put(0, 0, Cell.of("\\"));

        int i = 1;
        for (Terminal terminal: terminals)
            table.put(0, i++, Cell.of(terminal.toString()));
        table.put(0, i, Cell.of("$"));

        i = 1;
        for (NonTerminal nonTerminal: nonTerminals)
            table.put(i++, 0, Cell.of(nonTerminal.toString()));
        for (Terminal terminal: terminals)
            table.put(i++, 0, Cell.of(terminal.toString()));
        table.put(i, 0, Cell.of("$"));

        table = Border.SINGLE_LINE.apply(table);

        return table;
    }
}
