package org.astdea.data;

import org.astdea.data.versions.Component;
import org.astdea.data.versions.initialising.VersionCompManager;
import org.astdea.io.output.OPN;

import java.util.Set;

public class CompChangeAffectedCounter {

    private final AffType affType;
    private final boolean outgoing;

    private int existingAndAffected;
    private int existingAndNotAffected;
    private int nonExisting;
    private int changed11AndAffected;
    private int changed11AndNotAffected;
    private int changed1NAndAllAffectedWithoutOriginal;
    private int changed1NAndSomeAffectedWithoutOriginal;
    private int changed1NAndOneAffectedWithoutOriginal;
    private int changed1NAndNoneAffectedWithoutOriginal;
    private int changed1NAndAllAffectedWithOriginal;
    private int changed1NAndSomeAffectedWithOriginal;
    private int changed1NAndOneAffectedWithOriginal;
    private int changed1NAndNoneAffectedWithOriginal;

    private double existingAndAffectedPerComp;
    private double existingAndNotAffectedPerComp;
    private double nonExistingPerComp;
    private double changed11AndAffectedPerComp;
    private double changed11AndNotAffectedPerComp;
    private double changed1NAndAllAffectedPerCompWithoutOriginal;
    private double changed1NAndSomeAffectedPerCompWithoutOriginal;
    private double changed1NAndOneAffectedPerCompWithoutOriginal;
    private double changed1NAndNoneAffectedPerCompWithoutOriginal;
    private double changed1NAndAllAffectedPerCompWithOriginal;
    private double changed1NAndSomeAffectedPerCompWithOriginal;
    private double changed1NAndOneAffectedPerCompWithOriginal;
    private double changed1NAndNoneAffectedPerCompWithOriginal;

    public CompChangeAffectedCounter(AffType affType, boolean outgoing) {
        this.affType = affType;
        this.outgoing = outgoing;
    }

    //Outgoing: 1=Old, 2=New
    //Incoming: 1=New, 2=Old
    public void count(VersionCompManager vcm1, VersionCompManager vcm2, Set<String> sharedComps2) {
        Set<Component> comps1 = vcm1.getAffected(affType);
        if (!comps1.isEmpty()) {
            countAbs(vcm2, sharedComps2, comps1);
            countRel(comps1.size());
        }
    }

    private void countAbs(VersionCompManager vcm2, Set<String> sharedComps2, Set<Component> comps1) {
        for (Component comp : comps1) {
            if (outgoing && comp.hasSuccessors() || !outgoing && comp.hasPredecessors()) {
                countRenamed(vcm2, comp);
            }
            else {
                countNotRenamed(vcm2, sharedComps2, comp);
            }
        }
    }

    private void countNotRenamed(VersionCompManager vcm2, Set<String> sharedComps2, Component comp) {
        String fqcn = comp.getFqcn();
        if (vcm2.isFqcnAffected(affType, fqcn)) {
            existingAndAffected++;
        }
        else if (sharedComps2.contains(fqcn)) {
            existingAndNotAffected++;
        }
        else {
            nonExisting++;
        }
    }

    private void countRenamed(VersionCompManager vcm2, Component comp) {
        Set<String> fqcns = outgoing? comp.getSuccessors() : comp.getPredecessors();
        if (fqcns.size() == 1) {
            countRenamed11(vcm2, fqcns);
        }
        else if (vcm2.isFqcnAffected(affType, comp.getFqcn())) {
            countRenamed1NWithOriginal(vcm2, fqcns);
        }
        else {
            countRenamed1NWithoutOriginal(vcm2, fqcns);
        }
    }

    private void countRenamed1NWithOriginal(VersionCompManager vcm2, Set<String> fqcns) {
        int affected = 0;
        for (String fqcn : fqcns) {
            if (vcm2.isFqcnAffected(affType, fqcn)) {
                affected++;
            }
        }
        if (affected == fqcns.size()) {
            changed1NAndAllAffectedWithOriginal++;
        }
        else if (affected == 1) {
            changed1NAndOneAffectedWithOriginal++;
        }
        else if (affected == 0) {
            changed1NAndNoneAffectedWithOriginal++;
        }
        else {
            changed1NAndSomeAffectedWithOriginal++;
        }
    }

    private void countRenamed1NWithoutOriginal(VersionCompManager vcm2, Set<String> fqcns) {
        int affected = 0;
        for (String fqcn : fqcns) {
            if (vcm2.isFqcnAffected(affType, fqcn)) {
                affected++;
            }
        }
        if (affected == fqcns.size()) {
            changed1NAndAllAffectedWithoutOriginal++;
        }
        else if (affected == 1) {
            changed1NAndOneAffectedWithoutOriginal++;
        }
        else if (affected == 0) {
            changed1NAndNoneAffectedWithoutOriginal++;
        }
        else {
            changed1NAndSomeAffectedWithoutOriginal++;
        }
    }

    private void countRenamed11(VersionCompManager vcm2, Set<String> fqcns) {
        String fqcn = fqcns.iterator().next();
        if (vcm2.isFqcnAffected(affType, fqcn)) {
            changed11AndAffected++;
        }
        else {
            changed11AndNotAffected++;
        }
    }

    private void countRel(int compCount) {
        existingAndAffectedPerComp = (double) existingAndAffected / compCount;
        existingAndNotAffectedPerComp = (double) existingAndNotAffected / compCount;
        nonExistingPerComp = (double) nonExisting / compCount;
        changed11AndAffectedPerComp = (double) changed11AndAffected / compCount;
        changed11AndNotAffectedPerComp = (double) changed11AndNotAffected / compCount;
        changed1NAndAllAffectedPerCompWithOriginal = (double) changed1NAndAllAffectedWithOriginal / compCount;
        changed1NAndSomeAffectedPerCompWithOriginal = (double) changed1NAndSomeAffectedWithOriginal / compCount;
        changed1NAndOneAffectedPerCompWithOriginal = (double) changed1NAndOneAffectedWithOriginal / compCount;
        changed1NAndNoneAffectedPerCompWithOriginal = (double) changed1NAndNoneAffectedWithOriginal / compCount;
        changed1NAndAllAffectedPerCompWithoutOriginal = (double) changed1NAndAllAffectedWithoutOriginal / compCount;
        changed1NAndSomeAffectedPerCompWithoutOriginal = (double) changed1NAndSomeAffectedWithoutOriginal / compCount;
        changed1NAndOneAffectedPerCompWithoutOriginal = (double) changed1NAndOneAffectedWithoutOriginal / compCount;
        changed1NAndNoneAffectedPerCompWithoutOriginal = (double) changed1NAndNoneAffectedWithoutOriginal / compCount;
    }

    public void addCounterToProject(CompChangeAffectedCounter counter) {
        this.existingAndAffected += counter.existingAndAffected;
        this.existingAndNotAffected += counter.existingAndNotAffected;
        this.nonExisting += counter.nonExisting;
        this.changed11AndAffected += counter.changed11AndAffected;
        this.changed11AndNotAffected += counter.changed11AndNotAffected;
        this.changed1NAndAllAffectedWithOriginal += counter.changed1NAndAllAffectedWithOriginal;
        this.changed1NAndSomeAffectedWithOriginal += counter.changed1NAndSomeAffectedWithOriginal;
        this.changed1NAndOneAffectedWithOriginal += counter.changed1NAndOneAffectedWithOriginal;
        this.changed1NAndNoneAffectedWithOriginal += counter.changed1NAndNoneAffectedWithOriginal;
        this.changed1NAndAllAffectedWithoutOriginal += counter.changed1NAndAllAffectedWithoutOriginal;
        this.changed1NAndSomeAffectedWithoutOriginal += counter.changed1NAndSomeAffectedWithoutOriginal;
        this.changed1NAndOneAffectedWithoutOriginal += counter.changed1NAndOneAffectedWithoutOriginal;
        this.changed1NAndNoneAffectedWithoutOriginal += counter.changed1NAndNoneAffectedWithoutOriginal;
        this.existingAndAffectedPerComp += counter.existingAndAffectedPerComp;
        this.existingAndNotAffectedPerComp += counter.existingAndNotAffectedPerComp;
        this.nonExistingPerComp += counter.nonExistingPerComp;
        this.changed11AndAffectedPerComp += counter.changed11AndAffectedPerComp;
        this.changed11AndNotAffectedPerComp += counter.changed11AndNotAffectedPerComp;
        this.changed1NAndAllAffectedPerCompWithOriginal += counter.changed1NAndAllAffectedPerCompWithOriginal;
        this.changed1NAndSomeAffectedPerCompWithOriginal += counter.changed1NAndSomeAffectedPerCompWithOriginal;
        this.changed1NAndOneAffectedPerCompWithOriginal += counter.changed1NAndOneAffectedPerCompWithOriginal;
        this.changed1NAndNoneAffectedPerCompWithOriginal += counter.changed1NAndNoneAffectedPerCompWithOriginal;
        this.changed1NAndAllAffectedPerCompWithoutOriginal += counter.changed1NAndAllAffectedPerCompWithoutOriginal;
        this.changed1NAndSomeAffectedPerCompWithoutOriginal += counter.changed1NAndSomeAffectedPerCompWithoutOriginal;
        this.changed1NAndOneAffectedPerCompWithoutOriginal += counter.changed1NAndOneAffectedPerCompWithoutOriginal;
        this.changed1NAndNoneAffectedPerCompWithoutOriginal += counter.changed1NAndNoneAffectedPerCompWithoutOriginal;
    }

    public String[] getRowNames() {
        if (outgoing) {
            return getRowNamesOutgoing();
        }
        else {
            return getRowNamesIncoming();
        }
    }

    private static String[] getRowNamesIncoming() {
        return new String[]
            {
                OPN.EXISTING_AFFECTED,
                OPN.EXISTING_NOT_AFFECTED,
                OPN.INTRODUCED,
                OPN.CHANGED11_AFFECTED,
                OPN.CHANGED11_NOT_AFFECTED,
                OPN.CHANGEDN1_ALL_AFFECTED_WITH_RESULT,
                OPN.CHANGEDN1_SOME_AFFECTED_WITH_RESULT,
                OPN.CHANGEDN1_ONE_AFFECTED_WITH_RESULT,
                OPN.CHANGEDN1_NONE_AFFECTED_WITH_RESULT,
                OPN.CHANGEDN1_ALL_AFFECTED_WITHOUT_RESULT,
                OPN.CHANGEDN1_SOME_AFFECTED_WITHOUT_RESULT,
                OPN.CHANGEDN1_ONE_AFFECTED_WITHOUT_RESULT,
                OPN.CHANGEDN1_NONE_AFFECTED_WITHOUT_RESULT
            };
    }

    private static String[] getRowNamesOutgoing() {
        return new String[]
            {
                OPN.EXISTING_AFFECTED,
                OPN.EXISTING_NOT_AFFECTED,
                OPN.REMOVED,
                OPN.CHANGED11_AFFECTED,
                OPN.CHANGED11_NOT_AFFECTED,
                OPN.CHANGED1N_ALL_AFFECTED_WITH_ORIGINAL,
                OPN.CHANGED1N_SOME_AFFECTED_WITH_ORIGINAL,
                OPN.CHANGED1N_ONE_AFFECTED_WITH_ORIGINAL,
                OPN.CHANGED1N_NONE_AFFECTED_WITH_ORIGINAL,
                OPN.CHANGED1N_ALL_AFFECTED_WITHOUT_ORIGINAL,
                OPN.CHANGED1N_SOME_AFFECTED_WITHOUT_ORIGINAL,
                OPN.CHANGED1N_ONE_AFFECTED_WITHOUT_ORIGINAL,
                OPN.CHANGED1N_NONE_AFFECTED_WITHOUT_ORIGINAL
            };
    }

    public int getAbsVal(String field) {
        return switch (field) {
            case OPN.EXISTING_AFFECTED -> existingAndAffected;
            case OPN.EXISTING_NOT_AFFECTED -> existingAndNotAffected;
            case OPN.REMOVED -> nonExisting;
            case OPN.INTRODUCED -> nonExisting;
            case OPN.CHANGED11_AFFECTED -> changed11AndAffected;
            case OPN.CHANGED11_NOT_AFFECTED -> changed11AndNotAffected;
            case OPN.CHANGED1N_ALL_AFFECTED_WITH_ORIGINAL -> changed1NAndAllAffectedWithOriginal;
            case OPN.CHANGED1N_SOME_AFFECTED_WITH_ORIGINAL -> changed1NAndSomeAffectedWithOriginal;
            case OPN.CHANGED1N_ONE_AFFECTED_WITH_ORIGINAL -> changed1NAndOneAffectedWithOriginal;
            case OPN.CHANGED1N_NONE_AFFECTED_WITH_ORIGINAL -> changed1NAndNoneAffectedWithOriginal;
            case OPN.CHANGED1N_ALL_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndAllAffectedWithoutOriginal;
            case OPN.CHANGED1N_SOME_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndSomeAffectedWithoutOriginal;
            case OPN.CHANGED1N_ONE_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndOneAffectedWithoutOriginal;
            case OPN.CHANGED1N_NONE_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndNoneAffectedWithoutOriginal;
            case OPN.CHANGEDN1_ALL_AFFECTED_WITH_RESULT -> changed1NAndAllAffectedWithOriginal;
            case OPN.CHANGEDN1_SOME_AFFECTED_WITH_RESULT -> changed1NAndSomeAffectedWithOriginal;
            case OPN.CHANGEDN1_ONE_AFFECTED_WITH_RESULT -> changed1NAndOneAffectedWithOriginal;
            case OPN.CHANGEDN1_NONE_AFFECTED_WITH_RESULT -> changed1NAndNoneAffectedWithOriginal;
            case OPN.CHANGEDN1_ALL_AFFECTED_WITHOUT_RESULT -> changed1NAndAllAffectedWithoutOriginal;
            case OPN.CHANGEDN1_SOME_AFFECTED_WITHOUT_RESULT -> changed1NAndSomeAffectedWithoutOriginal;
            case OPN.CHANGEDN1_ONE_AFFECTED_WITHOUT_RESULT -> changed1NAndOneAffectedWithoutOriginal;
            case OPN.CHANGEDN1_NONE_AFFECTED_WITHOUT_RESULT -> changed1NAndNoneAffectedWithoutOriginal;
            default -> -1;
        };
    }

    public double getRelVal(String field) {
        return switch (field) {
            case OPN.EXISTING_AFFECTED -> existingAndAffectedPerComp;
            case OPN.EXISTING_NOT_AFFECTED -> existingAndNotAffectedPerComp;
            case OPN.REMOVED -> nonExistingPerComp;
            case OPN.INTRODUCED -> nonExistingPerComp;
            case OPN.CHANGED11_AFFECTED -> changed11AndAffectedPerComp;
            case OPN.CHANGED11_NOT_AFFECTED -> changed11AndNotAffectedPerComp;
            case OPN.CHANGED1N_ALL_AFFECTED_WITH_ORIGINAL -> changed1NAndAllAffectedPerCompWithOriginal;
            case OPN.CHANGED1N_SOME_AFFECTED_WITH_ORIGINAL -> changed1NAndSomeAffectedPerCompWithOriginal;
            case OPN.CHANGED1N_ONE_AFFECTED_WITH_ORIGINAL -> changed1NAndOneAffectedPerCompWithOriginal;
            case OPN.CHANGED1N_NONE_AFFECTED_WITH_ORIGINAL -> changed1NAndNoneAffectedPerCompWithOriginal;
            case OPN.CHANGED1N_ALL_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndAllAffectedPerCompWithoutOriginal;
            case OPN.CHANGED1N_SOME_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndSomeAffectedPerCompWithoutOriginal;
            case OPN.CHANGED1N_ONE_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndOneAffectedPerCompWithoutOriginal;
            case OPN.CHANGED1N_NONE_AFFECTED_WITHOUT_ORIGINAL -> changed1NAndNoneAffectedPerCompWithoutOriginal;
            case OPN.CHANGEDN1_ALL_AFFECTED_WITH_RESULT -> changed1NAndAllAffectedPerCompWithOriginal;
            case OPN.CHANGEDN1_SOME_AFFECTED_WITH_RESULT -> changed1NAndSomeAffectedPerCompWithOriginal;
            case OPN.CHANGEDN1_ONE_AFFECTED_WITH_RESULT -> changed1NAndOneAffectedPerCompWithOriginal;
            case OPN.CHANGEDN1_NONE_AFFECTED_WITH_RESULT -> changed1NAndNoneAffectedPerCompWithOriginal;
            case OPN.CHANGEDN1_ALL_AFFECTED_WITHOUT_RESULT -> changed1NAndAllAffectedPerCompWithoutOriginal;
            case OPN.CHANGEDN1_SOME_AFFECTED_WITHOUT_RESULT -> changed1NAndSomeAffectedPerCompWithoutOriginal;
            case OPN.CHANGEDN1_ONE_AFFECTED_WITHOUT_RESULT -> changed1NAndOneAffectedPerCompWithoutOriginal;
            case OPN.CHANGEDN1_NONE_AFFECTED_WITHOUT_RESULT -> changed1NAndNoneAffectedPerCompWithoutOriginal;
            default -> -1;
        };
    }
}
