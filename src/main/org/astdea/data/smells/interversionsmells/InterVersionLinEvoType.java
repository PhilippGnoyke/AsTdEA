package org.astdea.data.smells.interversionsmells;

import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;

import java.util.List;

public abstract class InterVersionLinEvoType<IntraType extends IntraVersionLinEvoType>
    extends InterVersionSmell<List<IntraType>>
{
    public InterVersionLinEvoType(int versionOfIntroduction, List<IntraType> intraVersionSmells)
    {
        super(versionOfIntroduction, intraVersionSmells);
    }
}
