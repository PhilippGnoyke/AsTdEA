package org.astdea.data.smells.intraversionsmells;

import java.util.Objects;

public final class IntraId
{
    private final int versionId;
    private final int smellId;

    public IntraId(int versionId, int smellId)
    {
        this.versionId = versionId;
        this.smellId = smellId;
    }

    public int getVersionId() {return versionId;}

    public int getSmellId() {return smellId;}

    @Override
    public boolean equals(Object other)
    {
        if (this == other) { return true; }
        if (other == null || getClass() != other.getClass()) { return false; }
        IntraId intraId = (IntraId) other;
        return versionId == intraId.versionId &&
            smellId == intraId.smellId;
    }

    @Override
    public int hashCode() {return Objects.hash(versionId, smellId);}

    @Override
    public String toString() {return versionId + "." + smellId;}

}
