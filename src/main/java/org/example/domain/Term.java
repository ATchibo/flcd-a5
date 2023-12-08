package org.example.domain;

public abstract class Term {
    protected String name;

    public Term(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Term) {
            Term other = (Term) obj;
            return this.name.equals(other.name);
        }
        if (obj instanceof String) {
            String other = (String) obj;
            return this.name.equals(other);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
