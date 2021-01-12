package org.astdea.data.smells.intraversionsmells;

// Linear evolution (directed path graph)
public abstract class IntraVersionLinEvoType extends IntraVersionSmell
{
    private String mainComp;

    public IntraVersionLinEvoType(int smellId, int versionId, double pageRank, String mainComp)
    {
        super(smellId, versionId, pageRank);
        this.mainComp = mainComp;
    }

    public String getMainComp() {return mainComp;}
}
