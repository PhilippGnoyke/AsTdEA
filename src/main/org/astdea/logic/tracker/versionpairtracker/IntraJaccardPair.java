package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;

public class IntraJaccardPair<IntraType extends IntraVersionLinEvoType> implements Comparable
{
    private IntraType smellA;
    private IntraType smellB;
    private double jaccard;
    private double prDiff;

    public IntraJaccardPair(IntraType smellA, IntraType smellB, double jaccard)
    {
        this.smellA = smellA;
        this.smellB = smellB;
        this.jaccard = jaccard;
        this.prDiff = Math.abs(smellA.getPageRank() - smellB.getPageRank());
    }

    public IntraType getSmellA() {return smellA;}

    public IntraType getSmellB() {return smellB;}

    @Override
    public int compareTo(Object other)
    {
        int comp = -1;
        if (other instanceof IntraJaccardPair)
        {
            IntraJaccardPair otherPair = (IntraJaccardPair) other;
            comp = Double.compare(this.jaccard, otherPair.jaccard);
            if (comp == 0)
            {
                comp = Double.compare(this.prDiff, otherPair.prDiff);
            }
        }
        return comp;
    }
}
