package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionHd;
import org.astdea.data.smells.interversionsmells.TimeManager;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;

import java.util.List;

public class HdMappings extends LinEvoTypeMappings<IntraVersionHd, InterVersionHd>
{
    public HdMappings(TimeManager timeManager) { super(timeManager); }

    protected InterVersionHd instantiateInter(int versionOfIntroduction, List<IntraVersionHd> intraVersionSmells)
    {
        return new InterVersionHd(timeManager,versionOfIntroduction, intraVersionSmells);
    }
}
