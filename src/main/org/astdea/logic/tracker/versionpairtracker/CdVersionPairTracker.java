package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.utils.MathUtils;

import java.util.HashSet;
import java.util.Set;

public class CdVersionPairTracker extends VersionPairTracker<IntraVersionCd, InterVersionCd, CdMappings>
{
    public CdVersionPairTracker(Version versionA, Version versionB, CdMappings mappings, Level level)
    {
        this.mappings = mappings;
        unmappedIntrasA = new HashSet<>(versionA.getCds(level));
        unmappedIntrasB = new HashSet<>(versionB.getCds(level));
    }

    @Override
    public CdMappings track()
    {
        Set<IntraVersionCd> allIntrasA = new HashSet<>(unmappedIntrasA);
        Set<IntraVersionCd> allIntrasB = new HashSet<>(unmappedIntrasB);
        for (IntraVersionCd intraA : allIntrasA)
        {
            for (IntraVersionCd intraB : allIntrasB)
            {
                if (MathUtils.intersectionAtLeast2(intraA.getComps(), intraB.getComps()))
                {
                    mappings.put(intraA, intraB);
                    unmappedIntrasA.remove(intraA);
                    unmappedIntrasB.remove(intraB);
                }
            }
        }
        return mappings;
    }
}
