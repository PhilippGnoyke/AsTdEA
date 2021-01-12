package org.astdea.logic.tracker.projecttracker;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.Mappings;
import org.astdea.logic.tracker.versionpairtracker.VersionPairTracker;

import java.util.List;
import java.util.Set;

public abstract class SmellProjectTracker<IntraType extends IntraVersionSmell, InterType extends InterVersionSmell,
    MappingsMapType extends Mappings<IntraType, InterType, ?>>
{
    protected MappingsMapType mappings;

    protected SmellProjectTracker(MappingsMapType mappings) {this.mappings = mappings;}

    public Set<InterType> track(List<Version> versions)
    {
        for (int i = 0; i < versions.size() - 1; i++)
        {
            Version versionA = versions.get(i);
            Version versionB = versions.get(i + 1);
            VersionPairTracker<IntraType, InterType, MappingsMapType> pairTracker =
                instantiateVersionPairTracker(versionA, versionB, mappings);
            mappings = pairTracker.track();
        }
        return mappings.buildInterVersionSmells();
    }

    public abstract VersionPairTracker<IntraType, InterType, MappingsMapType> instantiateVersionPairTracker
        (Version versionA, Version versionB, MappingsMapType mappings);
}
