package org.astdea.data.smells.intraversionsmells;

// Linear evolution (directed path graph)
public abstract class IntraVersionLinEvoType extends IntraVersionSmell
{
    private String mainComp;

    public IntraVersionLinEvoType(int smellId, int versionId, double pageRank,int order, int size, String mainComp)
    {
        super(smellId, versionId, pageRank, order, size);
        this.mainComp = mainComp;
    }

    public String getMainComp() {return mainComp;}
}
