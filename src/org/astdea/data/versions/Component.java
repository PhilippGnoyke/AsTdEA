package org.astdea.data.versions;

import java.util.Objects;
import java.util.Set;

public class Component implements Comparable<Component> {

    private final String fqcn;
    private Set<String> successors;
    private Set<String> predecessors;

    public Component(String fqcn) {
        this.fqcn = fqcn;
    }

    public void addSuccessors(Set<String> successors) {
        this.successors = successors;
    }

    public void addPredecessors(Set<String> predecessors) {
        this.predecessors = predecessors;
    }

    public Set<String> getSuccessors() {
        return successors;
    }

    public Set<String> getPredecessors() {
        return predecessors;
    }

    public String getFqcn() {
        return fqcn;
    }

    public boolean hasSuccessors() {
        return successors != null && !successors.isEmpty();
    }

    public boolean hasPredecessors() {
        return predecessors != null && !predecessors.isEmpty();
    }

    public boolean fcqnOrSuccessorsMatch(Component potentialSuccessor)
    {
        if (this.fqcn.equals(potentialSuccessor.fqcn)) {
            return true;
        }
        return successorsMatch(potentialSuccessor);
    }

    public boolean successorsMatch(Component potentialSuccessor)
    {
        return this.successors != null && this.successors.contains(potentialSuccessor.fqcn);
    }

    @Override
    public int compareTo(Component other) {
        return this.fqcn.compareTo(other.fqcn);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component other = (Component) o;
        return Objects.equals(fqcn, other.fqcn);
    }


    @Override
    public int hashCode() {
        return fqcn != null ? fqcn.hashCode() : 0;
    }

}
