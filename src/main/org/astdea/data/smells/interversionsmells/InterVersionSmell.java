package org.astdea.data.smells.interversionsmells;

import org.astdea.data.versions.VersionTimeManager;
import org.astdea.io.output.OPN;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

public abstract class InterVersionSmell<CollectionType extends Collection>
{
    protected int smellId;
    protected int versionOfIntroduction;
    protected LocalDate timeOfIntroduction;
    protected int versionOfRemoval;
    protected LocalDate timeOfRemoval;
    protected int lifeSpanInVersions;
    protected int lifeSpanInDays;
    protected double versionOrientedRelativeLifespan;
    protected double timeOrientedRelativeLifespan;
    protected boolean presentInFirstVersion;
    protected boolean presentInLastVersion;

    protected CollectionType intraVersionSmells;

    public InterVersionSmell(int versionOfIntroduction, CollectionType intraVersionSmells)
    {
        this.versionOfIntroduction = versionOfIntroduction;
        this.intraVersionSmells = intraVersionSmells;
        smellId = InterVersionSmellIdManager.assignId();
        calcAndInitSmellProps();
    }

    public CollectionType getIntraVersionSmells() {return intraVersionSmells;}

    public int getVersionOfIntroduction() {return versionOfIntroduction;}

    public int getVersionOfRemoval() {return versionOfRemoval;}

    private void calcAndInitSmellProps()
    {
        timeOfIntroduction = VersionTimeManager.getVersionTime(versionOfIntroduction);
        versionOfRemoval = versionOfIntroduction + intraVersionSmells.size();
        timeOfRemoval = VersionTimeManager.getVersionTime(versionOfRemoval);
        lifeSpanInVersions = versionOfRemoval - versionOfIntroduction;
        lifeSpanInDays = Period.between(timeOfIntroduction, timeOfRemoval).getDays();
        versionOrientedRelativeLifespan = (double) lifeSpanInVersions / VersionTimeManager.getAnalysedTimeSpanInVersions();
        timeOrientedRelativeLifespan = (double) lifeSpanInDays / VersionTimeManager.getAnalysedTimeSpanInDays();
        presentInFirstVersion = (versionOfIntroduction == 0);
        presentInLastVersion = (versionOfRemoval == VersionTimeManager.getAnalysedTimeSpanInVersions());
    }

    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.PROPERTY_ID -> smellId;
                case OPN.PROPERTY_VERSION_OF_INTRODUCTION -> versionOfIntroduction;
                case OPN.PROPERTY_TIME_OF_INTRODUCTION -> timeOfIntroduction;
                case OPN.PROPERTY_VERSION_OF_REMOVAL -> versionOfRemoval;
                case OPN.PROPERTY_TIME_OF_REMOVAL -> timeOfRemoval;
                case OPN.PROPERTY_LIFE_SPAN_IN_VERSIONS -> lifeSpanInVersions;
                case OPN.PROPERTY_LIFE_SPAN_IN_DAYS -> lifeSpanInDays;
                case OPN.PROPERTY_VERSION_ORIENTED_RELATIVE_LIFESPAN -> versionOrientedRelativeLifespan;
                case OPN.PROPERTY_TIME_ORIENTED_RELATIVE_LIFESPAN -> timeOrientedRelativeLifespan;
                case OPN.PROPERTY_PRESENT_IN_FIRST_VERSION -> presentInFirstVersion;
                case OPN.PROPERTY_PRESENT_IN_LAST_VERSION -> presentInLastVersion;
                default -> null;
            };
    }
}
