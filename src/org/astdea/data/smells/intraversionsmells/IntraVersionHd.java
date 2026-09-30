package org.astdea.data.smells.intraversionsmells;

import org.astdea.data.versions.Component;

import java.util.HashSet;
import java.util.Set;

public class IntraVersionHd extends IntraVersionLinEvoType
{
    private Set<Component> affComps;
    private Set<Component> effComps;

    public IntraVersionHd(int smellId, int version, double pageRank, int order, int size, Component mainComp,
                          Set<Component> affComps, Set<Component> effComps)
    {
        super(smellId, version, pageRank, order, size, mainComp);
        this.affComps = affComps;
        this.effComps = effComps;
    }

    public Set<Component> getAffComps() {return affComps;}

    public Set<Component> getEffComps() {return effComps;}

    public HashSet<Component> getAffCompsHashSet() {return (HashSet<Component>) affComps;}

    public HashSet<Component> getEffCompsHashSet() {return (HashSet<Component>) effComps;}
}
