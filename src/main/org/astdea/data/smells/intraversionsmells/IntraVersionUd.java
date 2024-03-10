package org.astdea.data.smells.intraversionsmells;

import java.util.HashSet;
import java.util.Set;

public class IntraVersionUd extends IntraVersionLinEvoType
{
    private Set<String> lessStablePacks;

    public IntraVersionUd(int smellId, int version, double pageRank, int order, int size, String mainComp, Set<String> lessStablePacks)
    {
        super(smellId, version, pageRank,order, size, mainComp);
        this.lessStablePacks = lessStablePacks;
    }

    public Set<String> getLessStablePacks() {return lessStablePacks;}

    public HashSet<String> getLessStablePacksHashSet() {return (HashSet<String>) lessStablePacks;}
}

