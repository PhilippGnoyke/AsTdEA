package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;
import org.astdea.data.versions.Component;
import org.astdea.logic.mapping.LinEvoTypeMappings;
import org.astdea.logic.tracker.LinEvoTypeTrackState;

import java.util.*;

public abstract class LinEvoTypeVersionPairTracker<IntraType extends IntraVersionLinEvoType,
    InterType extends InterVersionLinEvoType, MappingsMapType extends LinEvoTypeMappings<IntraType, InterType>>
    extends VersionPairTracker<IntraType, InterType, MappingsMapType> {

    private final static double JACC_THRESH = 0.6;
    protected TransitionCounter transitionCounterAffEff = new TransitionCounter();

    @Override
    public MappingsMapType track() {
        if (!unmappedIntrasA.isEmpty() && !unmappedIntrasB.isEmpty()) {
            trackMainNameEquality();
            trackRenamedNameEquality();
            trackSetSimilarity();
        }
        return mappings;
    }

    private void trackMainNameEquality() {
        LinEvoTypeTrackState<IntraType> trackStateA = initTrackState(unmappedIntrasA);
        LinEvoTypeTrackState<IntraType> trackStateB = initTrackState(unmappedIntrasB);

        boolean stop = false;
        while (!stop) {
            Component mainCompA = trackStateA.getMainComp();
            Component mainCompB = trackStateB.getMainComp();
            if (mainCompA.equals(mainCompB)) {
                IntraType intraA = trackStateA.getIntra();
                IntraType intraB = trackStateB.getIntra();
                updateMappings(intraA, intraB);
                stop = trackStateA.update() || trackStateB.update();
                transitionCounter.incrementFqcn();
            }
            else if (mainCompA.compareTo(mainCompB) > 0) {
                stop = trackStateB.update();
            }
            else {
                stop = trackStateA.update();
            }
        }
    }

    private void trackRenamedNameEquality() {
        Set<IntraType> unmappedIntrasAWithSuccs = new HashSet<>();
        Set<IntraType> unmappedIntrasBWithPreds = new HashSet<>();

        for (IntraType intraA : unmappedIntrasA) {
            if (intraA.getMainComp().hasSuccessors()) {
                unmappedIntrasAWithSuccs.add(intraA);
            }
        }
        for (IntraType intraB : unmappedIntrasB) {
            if (intraB.getMainComp().hasPredecessors()) {
                unmappedIntrasBWithPreds.add(intraB);
            }
        }

        for (IntraType intraA : unmappedIntrasAWithSuccs) {
            IntraType match = null;
            for (IntraType intraB : unmappedIntrasBWithPreds) {
                if (intraA.getMainComp().successorsMatch(intraB.getMainComp())) {
                    updateMappings(intraA, intraB);
                    match = intraB;
                    transitionCounter.incrementCompChange();
                    intraA.setSuccessorViaRefactoringChange();
                    intraB.setPredecessorViaRefactoringChange();
                    break;
                }
            }
            if (match != null) {
                unmappedIntrasBWithPreds.remove(match);
            }
        }
    }

    protected void updateMappings(IntraType intraA, IntraType intraB) {
        mappings.put(intraA, intraB);
        unmappedIntrasA.remove(intraA);
        unmappedIntrasB.remove(intraB);
    }

    private LinEvoTypeTrackState<IntraType> initTrackState(Set<IntraType> intrasSet) {
        List<IntraType> intrasList = new ArrayList<>(intrasSet);
        intrasList.sort(Comparator.comparing(IntraType::getMainComp));
        return new LinEvoTypeTrackState<>(intrasList);
    }

    private void trackSetSimilarity() {
        TreeSet<IntraJaccardPair<IntraType>> jaccPairs = buildJaccPairs();
        for (IntraJaccardPair<IntraType> jaccPair : jaccPairs.descendingSet()) {
            IntraType intraA = jaccPair.getSmellA();
            IntraType intraB = jaccPair.getSmellB();
            if (unmappedIntrasA.contains(intraA) && unmappedIntrasB.contains(intraB)) {
                if (calcPureFqcnJaccOfPair(intraA, intraB) >= JACC_THRESH) {
                    transitionCounterAffEff.incrementFqcn();
                }
                else {
                    transitionCounterAffEff.incrementCompChange();
                }
                updateMappings(intraA, intraB);
            }
        }
    }

    private TreeSet<IntraJaccardPair<IntraType>> buildJaccPairs() {
        List<IntraType> intrasA = new ArrayList<>(unmappedIntrasA);
        List<IntraType> intrasB = new ArrayList<>(unmappedIntrasB);
        TreeSet<IntraJaccardPair<IntraType>> jaccPairs = new TreeSet<>();
        for (IntraType intraA : intrasA) {
            for (IntraType intraB : intrasB) {
                double jacc = calcJaccOfPair(intraA, intraB);
                if (jacc >= JACC_THRESH) {
                    jaccPairs.add(new IntraJaccardPair<>(intraA, intraB, jacc));
                }
            }
        }
        return jaccPairs;
    }

    protected abstract double calcJaccOfPair(IntraType intraA, IntraType intraB);

    protected abstract double calcPureFqcnJaccOfPair(IntraType intraA, IntraType intraB);
}
