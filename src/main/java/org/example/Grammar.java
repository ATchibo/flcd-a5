package org.example;

import org.example.domain.NonTerminal;
import org.example.domain.Production;
import org.example.domain.Term;
import org.example.domain.Terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grammar {

    private String filePath;

    private Set<NonTerminal> nonTerminals;
    private Set<Terminal> terminals;
    private NonTerminal startingSymbol;
    private List<Production> productions;


    public Grammar(String filePath) {
        this.filePath = filePath;

        this.nonTerminals = new HashSet<>();
        this.terminals = new HashSet<>();
        this.productions = new ArrayList<>();
    }

    public void readGrammarFromFile() throws FileNotFoundException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        try {
            String line = reader.readLine();

            String[] nonTerminals = line.split(" ");
            for (String nonTerminal: nonTerminals)
                this.nonTerminals.add(new NonTerminal(nonTerminal));

            line = reader.readLine();

            String[] terminals = line.split(" ");
            for (String terminal: terminals)
                this.terminals.add(new Terminal(terminal));

            line = reader.readLine();

            this.startingSymbol = new NonTerminal(line);

            line = reader.readLine();

            while (line != null) {
                String[] production = line.split(" ");
                NonTerminal sourceNonTerminal = new NonTerminal(production[0]);

                List<Term> resultingTerms = new ArrayList<>();
                for (int i = 2; i < production.length; i++)
                    if (Arrays.asList(nonTerminals).contains(production[i]))
                        resultingTerms.add(new NonTerminal(production[i]));
                    else resultingTerms.add(new Terminal(production[i]));

                this.productions.add(new Production(sourceNonTerminal, resultingTerms));

                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<NonTerminal> getNonTerminals() {
        return this.nonTerminals;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public NonTerminal getStartingSymbol() {
        return startingSymbol;
    }

    public Set<Terminal> getTerminals() {
        return terminals;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Non terminals: ");
        for (NonTerminal t: nonTerminals)
            sb.append(t).append(" ");
        sb.append("\n");

        sb.append("Terminals: ");
        for (Terminal t: terminals)
            sb.append(t).append(" ");
        sb.append("\n");

        sb.append("Starting symbol: ").append(startingSymbol).append("\n");

        sb.append("Productions: \n");
        for (Production p: productions)
            sb.append(p).append("\n");

        return sb.toString();
    }
}
