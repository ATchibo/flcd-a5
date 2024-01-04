package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Production {

    private List<NonTerminal> sourceNonTerminals;
    private List<Term> resultingTerms;

    public Production() {
        this.sourceNonTerminals = new ArrayList<>();
        this.resultingTerms = new ArrayList<>();
    }

    public Production(List<NonTerminal> sourceNonTerminals, List<Term> terms) {
        this.sourceNonTerminals = sourceNonTerminals;
        this.resultingTerms = terms;
    }

    public List<NonTerminal> getSourceNonTerminals() {
        return sourceNonTerminals;
    }

    public List<Term> getResultingTerms() {
        return resultingTerms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (NonTerminal t: sourceNonTerminals)
            sb.append(t);
        sb.append(" -> ");
        for (Term t: resultingTerms)
            sb.append(t);

        return sb.toString();
    }
}
