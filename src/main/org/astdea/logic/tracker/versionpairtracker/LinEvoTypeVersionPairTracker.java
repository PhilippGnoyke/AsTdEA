package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;
import org.astdea.logic.mapping.LinEvoTypeMappings;
import org.astdea.logic.tracker.LinEvoTypeTrackState;

import java.util.*;

public abstract class LinEvoTypeVersionPairTracker<IntraType extends IntraVersionLinEvoType,
    InterType extends InterVersionLinEvoType, MappingsMapType extends LinEvoTypeMappings<IntraType, InterType>>
    extends VersionPairTracker<IntraType, InterType, MappingsMapType>
{
    private final static double JACC_THRESH = 0.6;

    public MappingsMapType track()
    {
        trackMainNameEquality();
        trackSetSimilarity();
        return mappings;
    }

    private void trackMainNameEquality()
    {
        LinEvoTypeTrackState<IntraType> trackStateA = initTrackState(remainIntrasA);
        LinEvoTypeTrackState<IntraType> trackStateB = initTrackState(remainIntrasB);

        boolean stop = false;
        while (!stop)
        {
            String mainCompA = trackStateA.getMainComp();
            String mainCompB = trackStateB.getMainComp();
            if (mainCompA.equals(mainCompB))
            {
                IntraType intraA = trackStateA.getIntra();
                IntraType intraB = trackStateB.getIntra();
                updateMappings(intraA, intraB);
                stop = trackStateA.update() || trackStateB.update();
            }
            else if (mainCompA.compareTo(mainCompB) > 0)
            {
                stop = trackStateB.update();
            }
            else
            {
                stop = trackStateA.update();
            }
        }
    }

    protected void updateMappings(IntraType intraA, IntraType intraB)
    {
        mappings.put(intraA, intraB);
        remainIntrasA.remove(intraA);
        remainIntrasB.remove(intraB);
    }

    private LinEvoTypeTrackState<IntraType> initTrackState(Set<IntraType> intrasSet)
    {
        List<IntraType> intrasList = new ArrayList<>(intrasSet);
        intrasList.sort(Comparator.comparing(IntraType::getMainComp));
        return new LinEvoTypeTrackState<>(intrasList);
    }

    private void trackSetSimilarity()
    {
        TreeSet<IntraJaccardPair<IntraType>> jaccPairs = buildJaccPairs();
        for (IntraJaccardPair<IntraType> jaccPair : jaccPairs.descendingSet())
        {
            IntraType intraA = jaccPair.getSmellA();
            IntraType intraB = jaccPair.getSmellB();
            if (remainIntrasA.contains(intraA) && remainIntrasB.contains(intraB))
            {
                updateMappings(intraA, intraB);
            }
        }
    }

    private TreeSet<IntraJaccardPair<IntraType>> buildJaccPairs()
    {
        List<IntraType> intrasA = new ArrayList<>(remainIntrasA);
        List<IntraType> intrasB = new ArrayList<>(remainIntrasB);
        TreeSet<IntraJaccardPair<IntraType>> jaccPairs = new TreeSet<>();
        for (IntraType intraA : intrasA)
        {
            for (IntraType intraB : intrasB)
            {
                double jacc = calcJaccOfPair(intraA, intraB);
                if (jacc >= JACC_THRESH)
                {
                    jaccPairs.add(new IntraJaccardPair<>(intraA, intraB, jacc));
                }
            }
        }
        return jaccPairs;
    }

    protected abstract double calcJaccOfPair(IntraType intraA, IntraType intraB);
}
