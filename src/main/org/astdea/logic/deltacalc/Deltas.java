package org.astdea.logic.deltacalc;

public class Deltas
{
    private int[] intros;
    private int[] removs;

    public Deltas(int analysedVersions)
    {
        this.intros = new int[analysedVersions];
        this.removs = new int[analysedVersions + 1];
    }

    public void incrIntros(int versionId) {intros[versionId] += 1;}

    public void incrRemovs(int versionId) {removs[versionId] += 1;}

    public void addIntros(int versionId, int val) {intros[versionId] += val;}

    public void addRemovs(int versionId, int val) {removs[versionId] += val;}

    public int getIntro(int versionId) {return intros[versionId];}

    public int getRemov(int versionId) {return removs[versionId];}
}
