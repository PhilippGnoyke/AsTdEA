package org.astdea.data.smells.interversionsmells;

import org.astdea.io.output.OPN;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;

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

    protected final TimeManager timeManager;

    public InterVersionSmell(TimeManager timeManager, int versionOfIntroduction, CollectionType intraVersionSmells)
    {
        this.timeManager = timeManager;
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
        timeOfIntroduction = timeManager.getVersionTime(versionOfIntroduction);
        versionOfRemoval = versionOfIntroduction + intraVersionSmells.size();
        timeOfRemoval = timeManager.getVersionTime(versionOfRemoval);
        lifeSpanInVersions = versionOfRemoval - versionOfIntroduction;
        lifeSpanInDays = (int) ChronoUnit.DAYS.between(timeOfIntroduction, timeOfRemoval);
        versionOrientedRelativeLifespan = (double) lifeSpanInVersions / timeManager.getAnalysedTimeSpanInVersions();
        timeOrientedRelativeLifespan = (double) lifeSpanInDays / timeManager.getAnalysedTimeSpanInDays();
        presentInFirstVersion = (versionOfIntroduction == 0);
        presentInLastVersion = (versionOfRemoval == timeManager.getAnalysedTimeSpanInVersions());
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) { return true; }
        if (other == null || getClass() != other.getClass()) { return false; }
        InterVersionSmell<?> that = (InterVersionSmell<?>) other;
        return smellId == that.smellId;
    }

    @Override
    public int hashCode() {return Objects.hash(smellId);}

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
