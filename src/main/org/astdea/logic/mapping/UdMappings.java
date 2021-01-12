package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionUd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;

import java.util.List;

public class UdMappings extends LinEvoTypeMappings<IntraVersionUd, InterVersionUd>
{
    protected InterVersionUd instantiateInter(int versionOfIntroduction, List<IntraVersionUd> intraVersionSmells)
    {
        return new InterVersionUd(versionOfIntroduction, intraVersionSmells);
    }
}
