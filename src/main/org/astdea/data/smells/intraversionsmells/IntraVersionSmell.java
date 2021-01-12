package org.astdea.data.smells.intraversionsmells;

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
}
