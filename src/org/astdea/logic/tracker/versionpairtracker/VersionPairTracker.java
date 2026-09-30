package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.CompChanges;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.Mappings;
import org.astdea.utils.ComponentCollectionMathUtils;
import org.astdea.utils.MathUtilsComponent;

import java.util.Set;

public abstract class VersionPairTracker<IntraType extends IntraVersionSmell, InterType extends InterVersionSmell,
    MappingsType extends Mappings>
{
    protected Set<IntraType> unmappedIntrasA;
    protected Set<IntraType> unmappedIntrasB;
    protected MappingsType mappings;
    protected Level level;
    protected Version versionOld;
    protected TransitionCounter transitionCounter = new TransitionCounter();
    protected static ComponentCollectionMathUtils componentCollectionMathUtils = ComponentCollectionMathUtils.getInstance();
    protected static MathUtilsComponent mathUtils = MathUtilsComponent.getInstance();


    public abstract MappingsType track();

    public Set<IntraType> getUnmappedIntrasA() {return unmappedIntrasA;}

    public Set<IntraType> getUnmappedIntrasB() {return unmappedIntrasB;}
}
