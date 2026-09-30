package org.astdea.io.output.printer.compappender;

import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.IOUtils;

import java.util.Set;

public class CdCompAppender implements CompAppender<InterVersionCd>
{
    @Override
    public void appendComps(InterVersionCd smell, StringBuilder compNames)
    {
        for (Set<IntraVersionCd> compsInVersion : smell.getIntraVersionSmells())
        {
            for (IntraVersionCd comp : compsInVersion)
            {
                compNames.append(comp.getIntraId()).append("-");
                compNames.append(comp.getRemovalWeight()).append(IOUtils.DELIMITER);
            }
        }
    }
}
