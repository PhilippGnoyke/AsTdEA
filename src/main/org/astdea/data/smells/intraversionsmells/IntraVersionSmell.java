package org.astdea.data.smells.intraversionsmells;

import java.util.Objects;

public abstract class IntraVersionSmell
{
    private int smellId;
    private int versionId;
    private IntraId intraId;
    private double pageRank;

    public IntraVersionSmell(int smellId, int versionId, double pageRank)
    {
        this.smellId = smellId;
        this.versionId = versionId;
        this.pageRank = pageRank;
        this.intraId = new IntraId(versionId, smellId);
    }

    public int getSmellId() {return smellId;}

    public int getVersionId() {return versionId;}

    public double getPageRank() {return pageRank;}

    public IntraId getIntraId() {return intraId;}

    @Override
    public boolean equals(Object other)
    {
        if (this == other) { return true; }
        if (other == null || getClass() != other.getClass()) { return false; }
        IntraVersionSmell that = (IntraVersionSmell) other;
        return intraId.equals(that.intraId);
    }

    @Override
    public int hashCode() {return Objects.hash(intraId);}
}
