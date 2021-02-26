package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.interversionsmells.InterVersionUd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.UdMappings;
import org.astdea.utils.MathUtils;

import java.util.HashSet;

public class UdVersionPairTracker extends LinEvoTypeVersionPairTracker<IntraVersionUd, InterVersionUd, UdMappings>
{
    public UdVersionPairTracker(Version versionA, Version versionB, UdMappings mappings)
    {
        this.mappings = mappings;
        unmappedIntrasA = new HashSet<>(versionA.getUds());
        unmappedIntrasB = new HashSet<>(versionB.getUds());
    }

    @Override
    protected double calcJaccOfPair(IntraVersionUd intraA, IntraVersionUd intraB)
    {
        return MathUtils.jaccard(intraA.getLessStablePacksHashSet(), intraB.getLessStablePacksHashSet());
    }
}
