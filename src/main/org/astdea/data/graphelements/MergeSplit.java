package org.astdea.data.graphelements;

import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import java.util.Set;

import static org.astdea.utils.MathUtils.calcChange;

public class MergeSplit
{
    private int sourceVersion;
    private int targetVersion;
    IntraVersionCd targetOrSource;
    Set<IntraVersionCd> sourcesOrTargets;
    int overallOrderSet = 0;
    int overallSizeSet = 0;
    int overallSubcyclesSet = 0;
    double overallOrderChange = 0;
    double overallSizeChange = 0;
    double overallSubcyclesChange = 0;

    public MergeSplit(IntraVersionCd targetOrSource, Set<IntraVersionCd> sourcesOrTargets)
    {
        this.targetOrSource = targetOrSource;
        this.sourcesOrTargets = sourcesOrTargets;
        calcProps();
    }

    private void calcProps()
    {
        int version1 = targetOrSource.getVersionId();
        int version2 = sourcesOrTargets.iterator().next().getVersionId();
        sourceVersion = Math.min(version1,version2);
        targetVersion = Math.max(version1,version2);
        for (IntraVersionCd intra : sourcesOrTargets)
        {
            overallOrderSet += intra.getOrder();
            overallSizeSet += intra.getSize();
            overallSubcyclesSet += intra.getNumSubcycles();
        }
        overallOrderChange = calcChange(overallOrderSet,targetOrSource.getOrder());
        overallSizeChange = calcChange(overallSizeSet,targetOrSource.getSize());
        overallSubcyclesChange = calcChange(overallSubcyclesSet,targetOrSource.getNumSubcycles());
    }

    public int incomingOrOutgoingEdgeCount() { return sourcesOrTargets.size(); }

    public IntraVersionCd getTargetOrSource() { return targetOrSource; }

    public Set<IntraVersionCd> getSourcesOrTargets() { return sourcesOrTargets; }

    public int getOverallOrderSet() { return overallOrderSet; }

    public int getOverallSizeSet() { return overallSizeSet; }

    public int getOverallSubcyclesSet() { return overallSubcyclesSet; }

    public double getOverallOrderChange() { return overallOrderChange; }

    public double getOverallSizeChange() { return overallSizeChange; }

    public double getOverallSubcyclesChange() { return overallSubcyclesChange; }

    public int getSourceVersion() { return sourceVersion; }

    public int getTargetVersion() { return targetVersion; }
}
