package org.astdea.data.smells.interversionsmells;

import org.astdea.data.smells.intraversionsmells.IntraVersionUd;

import java.util.List;

public class InterVersionUd extends InterVersionLinEvoType<IntraVersionUd>
{
    public InterVersionUd(int versionOfIntroduction, List<IntraVersionUd> intraVersionSmells)
    {
        super(versionOfIntroduction, intraVersionSmells);
    }
}
