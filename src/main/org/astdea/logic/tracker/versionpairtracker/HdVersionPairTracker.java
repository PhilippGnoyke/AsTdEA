package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.interversionsmells.InterVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.versions.Version;
import org.astdea.io.output.LogUtil;
import org.astdea.logic.mapping.HdMappings;
import org.astdea.utils.MathUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HdVersionPairTracker extends LinEvoTypeVersionPairTracker<IntraVersionHd, InterVersionHd, HdMappings>
{
    public HdVersionPairTracker(Version versionA, Version versionB, HdMappings mappings)
    {
        this.mappings = mappings;
        unmappedIntrasA = new HashSet<>(versionA.getHds().values());
        unmappedIntrasB = new HashSet<>(versionB.getHds().values());
    }

    @Override
    protected double calcJaccOfPair(IntraVersionHd intraA, IntraVersionHd intraB)
    {
        Set<String> affA = intraA.getAffCompsHashSet();
        Set<String> affB = intraB.getAffCompsHashSet();
        Set<String> effA = intraA.getEffCompsHashSet();
        Set<String> effB = intraB.getEffCompsHashSet();
        int intersectionAff = MathUtils.sizeOfIntersection(affA, affB);
        int intersectionEff = MathUtils.sizeOfIntersection(effA, effB);
        int unionAff = MathUtils.sizeOfUnion(affA, affB, intersectionAff);
        int unionEff = MathUtils.sizeOfUnion(effA, effB, intersectionEff);
        double jaccAff = MathUtils.jaccard(affA, affB, intersectionAff);
        double jaccEff = MathUtils.jaccard(effA, effB, intersectionEff);
        return MathUtils.weightedHarmonicMeanOfTwo(jaccAff, jaccEff, unionAff, unionEff);
    }
}
