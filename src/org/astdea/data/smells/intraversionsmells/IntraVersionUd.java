package org.astdea.data.smells.intraversionsmells;

import org.astdea.data.versions.Component;

import java.util.HashSet;
import java.util.Set;

public class IntraVersionUd extends IntraVersionLinEvoType
{
    private Set<Component> lessStablePacks;

    public IntraVersionUd(int smellId, int version, double pageRank, int order, int size, Component mainComp, Set<Component> lessStablePacks)
    {
        super(smellId, version, pageRank,order, size, mainComp);
        this.lessStablePacks = lessStablePacks;
    }

    public Set<Component> getLessStablePacks() {return lessStablePacks;}

    public HashSet<Component> getLessStablePacksHashSet() {return (HashSet<Component>) lessStablePacks;}
}

