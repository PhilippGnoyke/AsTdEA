package org.astdea.data.smells.intraversionsmells;

import java.util.Set;

public class IntraVersionCd extends IntraVersionSmell
{
    private Set<String> comps;
    private int removalWeight;

    public IntraVersionCd(int smellId, int version, double pageRank, Set<String> comps)
    {
        super(smellId, version, pageRank);
        this.comps = comps;
    }

    public Set<String> getComps() {return comps;}

    public void initRemovalWeight() {removalWeight = 1;}

    public int getRemovalWeight() {return removalWeight;}

    public void addRemovalWeight(int weight) {removalWeight += weight;}
}



