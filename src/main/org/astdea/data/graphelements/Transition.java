package org.astdea.data.graphelements;

import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.Shape;

public class Transition
{

    private IntraVersionCd sourceIntra;
    private IntraVersionCd targetIntra;
    private TransitionType transitionType;
    private int sourceVersion;
    private int targetVersion;
    private Shape shapeSource;
    private Shape shapeTarget;

    public Transition(IntraVersionCd sourceIntra, IntraVersionCd targetIntra, TransitionType transitionType)
    {
        this.sourceIntra = sourceIntra;
        this.targetIntra = targetIntra;
        this.transitionType = transitionType;
        this.sourceVersion = sourceIntra.getVersionId();
        this.targetVersion = targetIntra.getVersionId();
        this.shapeSource = sourceIntra.getShape();
        this.shapeTarget = targetIntra.getShape();
    }

    public IntraVersionCd getSourceIntra() { return sourceIntra; }

    public IntraVersionCd getTargetIntra() { return targetIntra; }

    public TransitionType getTransitionType() { return transitionType; }

    public int getSourceVersion() { return sourceVersion; }

    public int getTargetVersion() { return targetVersion; }

    public Shape getShapeSource() { return shapeSource; }

    public Shape getShapeTarget() { return shapeTarget; }
}
