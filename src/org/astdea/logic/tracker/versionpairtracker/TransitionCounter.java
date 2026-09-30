package org.astdea.logic.tracker.versionpairtracker;

import org.astdea.io.output.OPN;

public class TransitionCounter {

    private int fcqnTransitions;
    private int compChangeTransitions;

    public void incrementFqcn() {
        fcqnTransitions++;
    }

    public void incrementCompChange() {
        compChangeTransitions++;
    }

    public String[] getRowNames() {
        return new String[]
            {
                OPN.FQCN_TRANSITION,
                OPN.COMP_CHANGE_TRANSITION,
            };
    }

    public int getAbsVal(String field) {
        return switch (field) {
            case OPN.FQCN_TRANSITION -> fcqnTransitions;
            case OPN.COMP_CHANGE_TRANSITION -> compChangeTransitions;
            default -> -1;
        };
    }

    public void addCounterToProject(TransitionCounter counter) {
        this.fcqnTransitions += counter.fcqnTransitions;
        this.compChangeTransitions += counter.compChangeTransitions;
    }

}
