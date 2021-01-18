package org.astdea.data.smells.interversionsmells;

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
        timeOfIntroduction = TimeManager.getVersionTime(versionOfIntroduction);
        versionOfRemoval = versionOfIntroduction + intraVersionSmells.size();
        timeOfRemoval = TimeManager.getVersionTime(versionOfRemoval);
        lifeSpanInVersions = versionOfRemoval - versionOfIntroduction;
        lifeSpanInDays = Period.between(timeOfIntroduction, timeOfRemoval).getDays();
        versionOrientedRelativeLifespan = (double) lifeSpanInVersions / TimeManager.getAnalysedTimeSpanInVersions();
        timeOrientedRelativeLifespan = (double) lifeSpanInDays / TimeManager.getAnalysedTimeSpanInDays();
        presentInFirstVersion = (versionOfIntroduction == 0);
        presentInLastVersion = (versionOfRemoval == TimeManager.getAnalysedTimeSpanInVersions());
    }

    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.ID -> smellId;
                case OPN.VERSION_OF_INTRODUCTION -> versionOfIntroduction;
                case OPN.TIME_OF_INTRODUCTION -> timeOfIntroduction;
                case OPN.VERSION_OF_REMOVAL -> versionOfRemoval;
                case OPN.TIME_OF_REMOVAL -> timeOfRemoval;
                case OPN.LIFE_SPAN_IN_VERSIONS -> lifeSpanInVersions;
                case OPN.LIFE_SPAN_IN_DAYS -> lifeSpanInDays;
                case OPN.VERSION_ORIENTED_RELATIVE_LIFESPAN -> versionOrientedRelativeLifespan;
                case OPN.TIME_ORIENTED_RELATIVE_LIFESPAN -> timeOrientedRelativeLifespan;
                case OPN.PRESENT_IN_FIRST_VERSION -> presentInFirstVersion;
                case OPN.PRESENT_IN_LAST_VERSION -> presentInLastVersion;
                default -> null;
            };
    }
}
