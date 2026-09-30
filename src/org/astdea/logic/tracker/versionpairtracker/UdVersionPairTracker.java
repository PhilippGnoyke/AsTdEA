package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.AffType;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionUd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.UdMappings;

import java.util.HashSet;

public class UdVersionPairTracker extends LinEvoTypeVersionPairTracker<IntraVersionUd, InterVersionUd, UdMappings>
{
    public UdVersionPairTracker(Version versionA, Version versionB, UdMappings mappings)
    {
        this.mappings = mappings;
        this.versionOld = versionA;
        this.level = Level.PACK;
        unmappedIntrasA = new HashSet<>(versionA.getUds().values());
        unmappedIntrasB = new HashSet<>(versionB.getUds().values());
        versionA.addTransitionCounter(AffType.UDC,transitionCounter);
        versionA.addTransitionCounter(AffType.UDAE,transitionCounterAffEff);
    }

    @Override
    protected double calcJaccOfPair(IntraVersionUd intraA, IntraVersionUd intraB)
    {
        return componentCollectionMathUtils.jaccard(intraA.getLessStablePacksHashSet(), intraB.getLessStablePacksHashSet());
    }

    @Override
    protected double calcPureFqcnJaccOfPair(IntraVersionUd intraA, IntraVersionUd intraB)
    {
        return mathUtils.jaccard(intraA.getLessStablePacksHashSet(), intraB.getLessStablePacksHashSet());
    }
}
