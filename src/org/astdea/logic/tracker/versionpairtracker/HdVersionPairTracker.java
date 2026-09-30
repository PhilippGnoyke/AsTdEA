package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.AffType;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.versions.Component;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.HdMappings;
import org.astdea.utils.MathUtils;
import org.astdea.utils.OverlapUtils;

import java.util.HashSet;
import java.util.Set;

public class HdVersionPairTracker extends LinEvoTypeVersionPairTracker<IntraVersionHd, InterVersionHd, HdMappings>
{
    public HdVersionPairTracker(Version versionA, Version versionB, HdMappings mappings)
    {
        this.mappings = mappings;
        this.versionOld = versionA;
        this.level = Level.CLASS;
        unmappedIntrasA = new HashSet<>(versionA.getHds().values());
        unmappedIntrasB = new HashSet<>(versionB.getHds().values());
        versionA.addTransitionCounter(AffType.HDC,transitionCounter);
        versionA.addTransitionCounter(AffType.HDAE,transitionCounterAffEff);
    }

    @Override
    protected double calcJaccOfPair(IntraVersionHd intraA, IntraVersionHd intraB)
    {
        return calcJaccOfPairCore(intraA,intraB,componentCollectionMathUtils);
    }

    @Override
    protected double calcPureFqcnJaccOfPair(IntraVersionHd intraA, IntraVersionHd intraB)
    {
        return calcJaccOfPairCore(intraA,intraB,mathUtils);
    }

    protected double calcJaccOfPairCore(IntraVersionHd intraA, IntraVersionHd intraB, OverlapUtils<Component> overlapUtils)
    {
        Set<Component> affA = intraA.getAffCompsHashSet();
        Set<Component> affB = intraB.getAffCompsHashSet();
        Set<Component> effA = intraA.getEffCompsHashSet();
        Set<Component> effB = intraB.getEffCompsHashSet();
        int intersectionAff = overlapUtils.sizeOfIntersection(affA, affB);
        int intersectionEff = overlapUtils.sizeOfIntersection(effA, effB);
        int unionAff = overlapUtils.sizeOfUnion(affA, affB, intersectionAff);
        int unionEff = overlapUtils.sizeOfUnion(effA, effB, intersectionEff);
        double jaccAff = overlapUtils.jaccard(affA, affB, intersectionAff);
        double jaccEff = overlapUtils.jaccard(effA, effB, intersectionEff);
        return MathUtils.weightedHarmonicMeanOfTwo(jaccAff, jaccEff, unionAff, unionEff);
    }

}
