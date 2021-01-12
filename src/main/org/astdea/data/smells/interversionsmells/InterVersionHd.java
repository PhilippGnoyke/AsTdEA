package org.astdea.data.smells.interversionsmells;

import org.astdea.data.smells.intraversionsmells.IntraVersionHd;

import java.util.List;

public class InterVersionHd extends InterVersionLinEvoType<IntraVersionHd>
{
    public InterVersionHd(int versionOfIntroduction, List<IntraVersionHd> intraVersionSmells)
    {
        super(versionOfIntroduction, intraVersionSmells);
    }
}
