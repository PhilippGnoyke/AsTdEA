package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.data.AffType;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.versions.Component;
import org.astdea.data.versions.Version;
import org.astdea.logic.mapping.CdMappings;

import java.util.HashSet;
import java.util.Set;

public class CdVersionPairTracker extends VersionPairTracker<IntraVersionCd, InterVersionCd, CdMappings> {

    public CdVersionPairTracker(Version versionA, Version versionB, CdMappings mappings, Level level) {
        this.mappings = mappings;
        this.versionOld = versionA;
        this.level = level;
        unmappedIntrasA = new HashSet<>(versionA.getCds(level).values());
        unmappedIntrasB = new HashSet<>(versionB.getCds(level).values());
        if (level == Level.CLASS) {
            versionA.addTransitionCounter(AffType.CCD, transitionCounter);
        }
        else {
            versionA.addTransitionCounter(AffType.PCD, transitionCounter);
        }
    }

    @Override
    public CdMappings track() {
        Set<IntraVersionCd> allIntrasA = new HashSet<>(unmappedIntrasA);
        Set<IntraVersionCd> allIntrasB = new HashSet<>(unmappedIntrasB);
        for (IntraVersionCd intraA : allIntrasA) {
            for (IntraVersionCd intraB : allIntrasB) {
                Set<Component> compsOld = intraA.getComps();
                Set<Component> compsNew = intraB.getComps();
                if (componentCollectionMathUtils.intersectionAtLeast2(compsOld, compsNew)) {
                    mappings.put(intraA, intraB);
                    unmappedIntrasA.remove(intraA);
                    unmappedIntrasB.remove(intraB);
                    if (mathUtils.intersectionAtLeast2(compsOld, compsNew)) {
                        transitionCounter.incrementFqcn();
                    }
                    else {
                        transitionCounter.incrementCompChange();
                        intraA.setSuccessorViaRefactoringChange();
                        intraB.setPredecessorViaRefactoringChange();
                    }
                }
            }
        }
        return mappings;
    }
}
