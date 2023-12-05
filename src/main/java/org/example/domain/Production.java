package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Production {

    private NonTerminal sourceNonTerminal;
    private List<Term> resultingTerms;

    public Production(NonTerminal sourceNonTerminal) {
        this.sourceNonTerminal = sourceNonTerminal;
        resultingTerms = new ArrayList<>();
    }

    public Production(NonTerminal sourceNonTerminal, List<Term> terms) {
        this.sourceNonTerminal = sourceNonTerminal;
        this.resultingTerms = terms;
    }

    public NonTerminal getSourceNonTerminal() {
        return sourceNonTerminal;
    }

    public List<Term> getResultingTerms() {
        return resultingTerms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(sourceNonTerminal).append(" -> ");
        for (Term t: resultingTerms)
            sb.append(t);

        return sb.toString();
    }
}
