package org.astdea.data.smells.interversionsmells;

import org.astdea.data.graphelements.MergeSplit;
import org.astdea.data.graphelements.Transition;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.output.OPN;
import org.astdea.utils.MathUtils;

import java.util.List;
import java.util.Set;

public class InterVersionCd extends InterVersionSmell<List<Set<IntraVersionCd>>>
{
    private Set<Transition> transitions;
    private Set<MergeSplit> merges;
    private Set<MergeSplit> splits;

    private int familyOrder;
    private double medianFamilyWidth;
    private int maxFamilyWidth = 0;
    private int minFamilyWidth = MathUtils.INFINITY;
    private int totalTransitions;
    private int pureTransitions;
    private int mergeOnlyTransitions;
    private int splitOnlyTransitions;
    private int mergeAndSplitTransitions;
    private int mergesCount;
    private int splitsCount;
    private double simpleMergesShareUnweighted;
    private double simpleSplitsShareUnweighted;
    private double simpleMergesShareWeighted;
    private double simpleSplitsShareWeighted;
    private int incomingEdgeCountLargestMerge;
    private int outgoingEdgeCountLargestSplit;

    @Override
    public List<Set<IntraVersionCd>> getIntraVersionSmells() {return intraVersionSmells;}

    public InterVersionCd(TimeManager timeManager,
                          int versionOfIntroduction,
                          List<Set<IntraVersionCd>> intraVersionSmells,
                          Set<Transition> transitions,
                          Set<MergeSplit> merges,
                          Set<MergeSplit> splits)
    {
        super(timeManager,versionOfIntroduction, intraVersionSmells);
        this.transitions = transitions;
        this.merges = merges;
        this.splits = splits;
        calcWidths(intraVersionSmells);
    }

    public void calcMergesSplits()
    {
        mergesCount = merges.size();
        splitsCount = splits.size();
        int simpleMergesCount = 0;
        int simpleSplitsCount = 0;
        for (MergeSplit merge : merges)
        {
            int edgeCount = merge.incomingOrOutgoingEdgeCount();
            if (edgeCount == 2) { simpleMergesCount++; }
            incomingEdgeCountLargestMerge = Math.max(edgeCount, incomingEdgeCountLargestMerge);
        }
        for (MergeSplit split : splits)
        {
            int edgeCount = split.incomingOrOutgoingEdgeCount();
            if (split.incomingOrOutgoingEdgeCount() == 2) { simpleSplitsCount++; }
            outgoingEdgeCountLargestSplit = Math.max(edgeCount, outgoingEdgeCountLargestSplit);
        }
        simpleMergesShareUnweighted = (double) simpleMergesCount / mergesCount;
        simpleSplitsShareUnweighted = (double) simpleSplitsCount / splitsCount;
        simpleMergesShareWeighted = (double) simpleMergesCount * 2 / (mergeOnlyTransitions + mergeAndSplitTransitions);
        simpleSplitsShareWeighted = (double) simpleSplitsCount * 2 / (mergeOnlyTransitions + mergeAndSplitTransitions);
    }

    public void setTransitionCounts(int pureTransitions, int mergeOnlyTransitions,
                                    int splitOnlyTransitions, int mergeAndSplitTransitions)
    {
        this.pureTransitions = pureTransitions;
        this.mergeOnlyTransitions = mergeOnlyTransitions;
        this.splitOnlyTransitions = splitOnlyTransitions;
        this.mergeAndSplitTransitions = mergeAndSplitTransitions;
        totalTransitions = pureTransitions + mergeOnlyTransitions + splitOnlyTransitions + mergeAndSplitTransitions;
        calcMergesSplits();
    }


    private void calcWidths(List<Set<IntraVersionCd>> intraVersionSmells)
    {
        double[] widths = new double[lifeSpanInVersions];
        for (int i = 0; i < lifeSpanInVersions; i++)
        {
            int width = intraVersionSmells.get(i).size();
            familyOrder += width;
            maxFamilyWidth = Math.max(maxFamilyWidth, width);
            minFamilyWidth = Math.min(minFamilyWidth, width);
            widths[i] = width;
        }
        medianFamilyWidth = MathUtils.median(widths);
    }

    public Set<Transition> getTransitions() { return transitions; }

    public Set<MergeSplit> getMerges() { return merges; }

    public Set<MergeSplit> getSplits() { return splits; }

    @Override
    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.FAMILY_ORDER -> familyOrder;
                case OPN.MEDIAN_FAMILY_WIDTH -> medianFamilyWidth;
                case OPN.MAX_FAMILY_WIDTH -> maxFamilyWidth;
                case OPN.MIN_FAMILY_WIDTH -> minFamilyWidth;
                case OPN.FAMILY_SIZE -> totalTransitions;
                case OPN.PURE_TRANSITIONS -> pureTransitions;
                case OPN.MERGE_ONLY_TRANSITIONS -> mergeOnlyTransitions;
                case OPN.SPLIT_ONLY_TRANSITIONS -> splitOnlyTransitions;
                case OPN.MERGE_AND_SPLIT_TRANSITIONS -> mergeAndSplitTransitions;
                case OPN.MERGES_COUNT -> mergesCount;
                case OPN.SPLITS_COUNT -> splitsCount;
                case OPN.SIMPLE_MERGES_SHARE_UNWEIGHTED -> simpleMergesShareUnweighted;
                case OPN.SIMPLE_SPLITS_SHARE_UNWEIGHTED -> simpleSplitsShareUnweighted;
                case OPN.SIMPLE_MERGES_SHARE_WEIGHTED -> simpleMergesShareWeighted;
                case OPN.SIMPLE_SPLITS_SHARE_WEIGHTED -> simpleSplitsShareWeighted;
                case OPN.INCOMING_EDGE_COUNT_LARGEST_MERGE -> incomingEdgeCountLargestMerge;
                case OPN.OUTGOING_EDGE_COUNT_LARGEST_SPLIT -> outgoingEdgeCountLargestSplit;

                default -> super.get(fieldName);
            };
    }
}
