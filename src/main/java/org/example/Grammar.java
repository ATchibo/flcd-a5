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


    private final Terminal EPSILON = new Terminal("ε");

    public Grammar(String fileName) {
        this.filePath = "src/main/resources/" + fileName;

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
                String[] production = line.split(" -> ");
                String[] sourceNonTerminals = production[0].split(" ");

                List<NonTerminal> sourceNonTerminalsList = new ArrayList<>();
                for (String sourceNonTerminal: sourceNonTerminals)
                    sourceNonTerminalsList.add(new NonTerminal(sourceNonTerminal));

                String[] resultingTerms = production[1].split(" ");
                List<Term> resultingTermsList = new ArrayList<>();

                for (String resultingTerm: resultingTerms)
                    if (Arrays.asList(nonTerminals).contains(resultingTerm))
                        resultingTermsList.add(new NonTerminal(resultingTerm));
                    else resultingTermsList.add(new Terminal(resultingTerm));

                this.productions.add(new Production(sourceNonTerminalsList, resultingTermsList));

                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Production> getProductionsOfNonTerminal(String nonTerminal) {
        List<Production> result = new ArrayList<>();

        for (Production p: productions) {
            if (p.getSourceNonTerminals().size() != 1)
                continue;

            if (p.getSourceNonTerminals().get(0).getName().equals(nonTerminal))
                result.add(p);
        }

        return result;
    }


    public boolean isContextFreeGrammar() {
        return productions.stream()
                .map(Production::getSourceNonTerminals)
                .allMatch(nonterminals -> nonterminals.size() == 1);
    }

    public List<Terminal> getFirst(NonTerminal nonTerminal) {
        List<Terminal> result = new ArrayList<>();

        return result;
    }

    public List<Terminal> getFollow(NonTerminal nonTerminal) {
        List<Terminal> result = new ArrayList<>();

        return result;
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
