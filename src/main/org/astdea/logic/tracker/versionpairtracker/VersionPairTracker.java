package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.logic.mapping.Mappings;

import java.util.Set;

public abstract class VersionPairTracker<IntraType extends IntraVersionSmell, InterType extends InterVersionSmell,
    MappingsMapType extends Mappings>
{
    protected Set<IntraType> remainIntrasA;
    protected Set<IntraType> remainIntrasB;
    protected MappingsMapType mappings;

    public abstract MappingsMapType track();
}
