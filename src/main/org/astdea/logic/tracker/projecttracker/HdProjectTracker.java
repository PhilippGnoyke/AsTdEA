package org.astdea.logic.tracker.projecttracker;

import org.astdea.data.smells.interversionsmells.InterVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.HdMappings;
import org.astdea.logic.tracker.versionpairtracker.HdVersionPairTracker;

public class HdProjectTracker extends SmellProjectTracker<IntraVersionHd, InterVersionHd, HdMappings>
{
    public HdProjectTracker()
    {
        super(new HdMappings());
    }

    public HdVersionPairTracker instantiateVersionPairTracker
        (Version versionA, Version versionB, HdMappings mappings)
    {
        return new HdVersionPairTracker(versionA, versionB, mappings);
    }
}
