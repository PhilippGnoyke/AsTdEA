package org.astdea.logic.tracker.projecttracker;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.CdMappings;
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
        for (int versionAId = 0; versionAId < versions.size() - 1; versionAId++)
        {
            Version versionA = versions.get(versionAId);
            Version versionB = versions.get(versionAId + 1);
            VersionPairTracker<IntraType, InterType, MappingsMapType> pairTracker =
                instantiateVersionPairTracker(versionA, versionB, mappings);
            mappings = pairTracker.track();
            mappings.addToSmellsWOSuccessor(pairTracker.getUnmappedIntrasA());
            mappings.addToSmellsWOPredecessor(pairTracker.getUnmappedIntrasB());
        }
        return mappings.buildInterVersionSmells();
    }

    protected abstract VersionPairTracker<IntraType, InterType, MappingsMapType> instantiateVersionPairTracker
        (Version versionA, Version versionB, MappingsMapType mappings);

    public MappingsMapType getMappings() {return mappings;}

}
