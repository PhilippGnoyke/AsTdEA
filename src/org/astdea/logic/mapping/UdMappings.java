package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionUd;
import org.astdea.data.smells.interversionsmells.TimeManager;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;

import java.util.List;

public class UdMappings extends LinEvoTypeMappings<IntraVersionUd, InterVersionUd>
{
    public UdMappings(TimeManager timeManager) { super(timeManager); }

    protected InterVersionUd instantiateInter(int versionOfIntroduction, List<IntraVersionUd> intraVersionSmells)
    {
        return new InterVersionUd(timeManager,versionOfIntroduction, intraVersionSmells);
    }
}
