package org.astdea.data.smells.interversionsmells;

import org.astdea.data.smells.intraversionsmells.IntraVersionHd;

import java.util.List;

public class InterVersionHd extends InterVersionLinEvoType<IntraVersionHd>
{
    public InterVersionHd(TimeManager timeManager,int versionOfIntroduction, List<IntraVersionHd> intraVersionSmells)
    {
        super(timeManager,versionOfIntroduction, intraVersionSmells);
    }
}
