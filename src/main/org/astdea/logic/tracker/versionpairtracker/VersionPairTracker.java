package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.logic.mapping.Mappings;

import java.util.Set;

public abstract class VersionPairTracker<IntraType extends IntraVersionSmell, InterType extends InterVersionSmell,
    MappingsType extends Mappings>
{
    protected Set<IntraType> unmappedIntrasA;
    protected Set<IntraType> unmappedIntrasB;
    protected MappingsType mappings;

    public abstract MappingsType track();

    public Set<IntraType> getUnmappedIntrasA() {return unmappedIntrasA;}

    public Set<IntraType> getUnmappedIntrasB() {return unmappedIntrasB;}
}
