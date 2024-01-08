package org.example.domain;

import org.example.Grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParsingTree {
    private class Node {
        public Term term;
        public List<Node> children;

        public Node(Term term) {
            this.term = term;
            this.children = new ArrayList<>();
        }
    }

    private Grammar grammar;
    private List<Integer> outputStackList;
    private Node root;
    private int currentOutputIndex;

    public ParsingTree(Grammar grammar, Stack<Integer> outputStack) {
        this.grammar = grammar;
        this.outputStackList = outputStack;
        this.root = new Node(grammar
            .getProductions()
            .get(this.outputStackList.getFirst() - 1)
            .getSourceNonTerminals()
            .getFirst()
        );
    }

    public void generateTree() {
        currentOutputIndex = 0;
        root = generateTreeRecursive(root);
    }

    private Node generateTreeRecursive(Node parent) {
        if (currentOutputIndex < outputStackList.size()) {
            Integer productionIndex = outputStackList.get(currentOutputIndex++);
            Production production = grammar.getProductions().get(productionIndex - 1);
            for (Term term : production.getResultingTerms()) {
                Node child = new Node(term);
                if (term instanceof NonTerminal) {
                    child = generateTreeRecursive(child);
                }
                parent.children.add(child);
            }

            return parent;
        }
        else {
            return null;
        }
    }
}
