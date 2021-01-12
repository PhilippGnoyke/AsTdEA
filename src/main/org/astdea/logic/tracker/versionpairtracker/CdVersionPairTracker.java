package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.utils.MathUtils;

import java.util.HashSet;

public class CdVersionPairTracker extends VersionPairTracker<IntraVersionCd, InterVersionCd, CdMappings>
{
    public CdVersionPairTracker(Version versionA, Version versionB, CdMappings mappings, Level level)
    {
        this.mappings = mappings;
        remainIntrasA = new HashSet<>(versionA.getCds(level));
        remainIntrasB = new HashSet<>(versionB.getCds(level));
    }

    public CdMappings track()
    {
        for (IntraVersionCd intraA : remainIntrasA)
        {
            for (IntraVersionCd intraB : remainIntrasB)
            {
                if (MathUtils.intersectionAtLeast2(intraA.getComps(), intraB.getComps()))
                {
                    mappings.put(intraA, intraB);
                }
            }
        }
        return mappings;
    }
}
