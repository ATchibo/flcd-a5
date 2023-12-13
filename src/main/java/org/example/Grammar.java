package org.example;

import org.example.domain.NonTerminal;
import org.example.domain.Production;
import org.example.domain.Term;
import org.example.domain.Terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public void readGrammarFromFile() throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

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
            for (String sourceNonTerminal: sourceNonTerminals) {
                NonTerminal t = new NonTerminal(sourceNonTerminal);
                if (!this.nonTerminals.contains(t))
                    throw new RuntimeException("Invalid grammar: non-terminal " + t + " not found");
                sourceNonTerminalsList.add(t);
            }

            String[] resultingTerms = production[1].split(" ");
            List<Term> resultingTermsList = new ArrayList<>();

            for (String resultingTerm: resultingTerms)
                if (Arrays.asList(nonTerminals).contains(resultingTerm))
                    resultingTermsList.add(new NonTerminal(resultingTerm));
                else if (Arrays.asList(terminals).contains(resultingTerm))
                    resultingTermsList.add(new Terminal(resultingTerm));
                else throw new RuntimeException("Invalid grammar: term " + resultingTerm + " not found");

            this.productions.add(new Production(sourceNonTerminalsList, resultingTermsList));

            line = reader.readLine();
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

    public NonTerminal getNonTerminalFromString(String nonTerminalName) {
        for (NonTerminal nonTerminal : nonTerminals) {
            if (nonTerminal.getName().equals(nonTerminalName)) {
                return nonTerminal;
            }
        }
        return null;
    }


    public boolean isContextFreeGrammar() {
        return productions.stream()
                .map(Production::getSourceNonTerminals)
                .allMatch(nonterminals -> nonterminals.size() == 1);
    }

    // should be used only for cfg grammars
    public Set<Terminal> getFirst(Term term) {
        Set<Terminal> result = new HashSet<>();

        if (term instanceof Terminal) {
            result.add((Terminal)term);
        }
        else if (term instanceof NonTerminal) {
            List<Production> productions = getProductionsOfNonTerminal(term.getName());
            for (Production production : productions) {
                List<Term> resultingTerms = production.getResultingTerms();
                if (resultingTerms.size() == 1 && resultingTerms.getFirst().equals(EPSILON)) {
                    result.add(EPSILON);
                }
                else {
                    boolean allContainEpsilon = true;
                    for (Term resultingTerm : resultingTerms) {

                        if (resultingTerm instanceof NonTerminal) {
                            NonTerminal t = (NonTerminal) resultingTerm;
                            if (t.equals(term)) {
                                continue;
                            }
                        }

                        Set<Terminal> localResult = getFirst(resultingTerm);
                        if (!localResult.contains(EPSILON)) {
                            result.addAll(localResult);
                            allContainEpsilon = false;
                            break;
                        }
                        else {
                            localResult.remove(EPSILON);
                            result.addAll(localResult);
                        }
                    }

                    if (allContainEpsilon) {
                        result.add(EPSILON);
                    }
                }
            }
        }
        else {
            throw new RuntimeException("getFirst accepts Terminal or NonTerminal classes only");
        }

        return result;
    }

    public List<Terminal> getFollow(NonTerminal nonTerminal) {
        List<Terminal> result = new ArrayList<>();

        //yay

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
