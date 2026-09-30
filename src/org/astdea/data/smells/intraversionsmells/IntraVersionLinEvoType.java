package org.astdea.data.smells.intraversionsmells;

import org.astdea.data.versions.Component;

// Linear evolution (directed path graph)
public abstract class IntraVersionLinEvoType extends IntraVersionSmell
{
    private Component mainComp;

    public IntraVersionLinEvoType(int smellId, int versionId, double pageRank,int order, int size, Component mainComp)
    {
        super(smellId, versionId, pageRank, order, size);
        this.mainComp = mainComp;
    }

    public Component getMainComp() {return mainComp;}
}
