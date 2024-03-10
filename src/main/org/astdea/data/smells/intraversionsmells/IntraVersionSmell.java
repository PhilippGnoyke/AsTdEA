package org.astdea.data.smells.intraversionsmells;

import java.util.Objects;

public abstract class IntraVersionSmell
{
    private int smellId;
    private int versionId;
    private IntraId intraId;
    private double pageRank;
    private int order;
    private int size;
    private int age = 0;
    private int remainingAge = 0;

    public IntraVersionSmell(int smellId, int versionId, double pageRank, int order, int size)
    {
        this.smellId = smellId;
        this.versionId = versionId;
        this.pageRank = pageRank;
        this.order = order;
        this.size = size;
        this.intraId = new IntraId(versionId, smellId);
    }

    public int getSmellId() { return smellId; }

    public int getVersionId() { return versionId; }

    public double getPageRank() { return pageRank; }

    public int getOrder() { return order; }

    public int getSize() { return size; }

    public IntraId getIntraId() { return intraId; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public int getRemainingAge() { return remainingAge; }

    public void setRemainingAge(int remainingAge) { this.remainingAge = remainingAge; }

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
