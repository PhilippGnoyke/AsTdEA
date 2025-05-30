package org.astdea.logic.tracker.projecttracker;

import org.astdea.data.smells.interversionsmells.InterVersionUd;
import org.astdea.data.smells.interversionsmells.TimeManager;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.UdMappings;
import org.astdea.logic.tracker.versionpairtracker.UdVersionPairTracker;

public class UdProjectTracker extends SmellProjectTracker<IntraVersionUd, InterVersionUd, UdMappings>
{
    public UdProjectTracker(TimeManager timeManager) {super(timeManager,new UdMappings(timeManager));}

    @Override
    protected UdVersionPairTracker instantiateVersionPairTracker
        (Version versionA, Version versionB, UdMappings mappings)
    {
        return new UdVersionPairTracker(versionA, versionB, mappings);
    }
}
