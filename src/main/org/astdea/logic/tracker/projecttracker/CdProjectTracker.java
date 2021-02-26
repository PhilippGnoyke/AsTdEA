package org.astdea.logic.tracker.projecttracker;

import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.logic.tracker.versionpairtracker.CdVersionPairTracker;

public class CdProjectTracker extends SmellProjectTracker<IntraVersionCd, InterVersionCd, CdMappings>
{
    private Level level;

    public CdProjectTracker(Level level, int totalNumOfIntras)
    {
        super(new CdMappings(totalNumOfIntras));
        this.level = level;
    }

    @Override
    protected CdVersionPairTracker instantiateVersionPairTracker
        (Version versionA, Version versionB, CdMappings mappings)
    {
        return new CdVersionPairTracker(versionA, versionB, mappings, level);
    }
}
