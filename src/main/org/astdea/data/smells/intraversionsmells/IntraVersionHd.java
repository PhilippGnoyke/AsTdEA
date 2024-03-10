package org.astdea.data.smells.intraversionsmells;

import java.util.HashSet;
import java.util.Set;

public class IntraVersionHd extends IntraVersionLinEvoType
{
    private Set<String> affComps;
    private Set<String> effComps;

    public IntraVersionHd(int smellId, int version, double pageRank, int order, int size, String mainComp,
                          Set<String> affComps, Set<String> effComps)
    {
        super(smellId, version, pageRank, order, size, mainComp);
        this.affComps = affComps;
        this.effComps = effComps;
    }

    public Set<String> getAffComps() {return affComps;}

    public Set<String> getEffComps() {return effComps;}

    public HashSet<String> getAffCompsHashSet() {return (HashSet<String>) affComps;}

    public HashSet<String> getEffCompsHashSet() {return (HashSet<String>) effComps;}
}
