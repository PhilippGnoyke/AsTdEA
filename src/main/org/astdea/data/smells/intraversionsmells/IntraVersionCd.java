package org.astdea.data.smells.intraversionsmells;

import java.util.HashSet;
import java.util.Set;

public class IntraVersionCd extends IntraVersionSmell
{
    private int numSubcycles;
    private Shape shape;
    private Set<String> comps;
    private int removalWeight;
    private Set<IntraVersionCd> allPreds;
    private Set<IntraVersionCd> allSuccs;

    private int numDirectPreds = 0;
    private int numDirectSuccs = 0;

    public IntraVersionCd(int smellId, int version, double pageRank, int order, int size,
                          int numSubcycles, Shape shape, Set<String> comps)
    {
        super(smellId, version, pageRank, order, size);
        this.comps = comps;
        this.numSubcycles = numSubcycles;
        this.shape = shape;
        this.allPreds = new HashSet<>();
        this.allSuccs = new HashSet<>();
    }

    public Set<String> getComps() {return comps;}

    public void initRemovalWeight() {removalWeight = 1;}

    public int getRemovalWeight() {return removalWeight;}

    public void addRemovalWeight(int weight) {removalWeight += weight;}

    public int getNumSubcycles() {return numSubcycles;}

    public Shape getShape() {return shape;}

    public int getNumDirectPreds() { return numDirectPreds; }

    public void setNumDirectPreds(int numDirectPreds) { this.numDirectPreds = numDirectPreds; }

    public int getNumAllPreds() { return allPreds.size(); }

    public int getNumDirectSuccs() { return numDirectSuccs; }

    public void setNumDirectSuccs(int numDirectSuccs) { this.numDirectSuccs = numDirectSuccs; }

    public int getNumAllSuccs() { return allSuccs.size(); }

    public void addPreds(Set<IntraVersionCd> preds) { allPreds.addAll(preds); }

    public void addSuccs(Set<IntraVersionCd> succs) { allSuccs.addAll(succs); }

    public void addPred(IntraVersionCd pred)  { allPreds.add(pred); }

    public void addSucc(IntraVersionCd succ)  { allPreds.add(succ); }

    public Set<IntraVersionCd> getAllPreds() { return allPreds; }

    public Set<IntraVersionCd> getAllSuccs() { return allSuccs; }
}



