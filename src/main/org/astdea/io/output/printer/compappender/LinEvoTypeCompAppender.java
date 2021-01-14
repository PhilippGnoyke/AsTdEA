package org.astdea.io.output.printer.compappender;

import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;
import org.astdea.io.inputoutput.IOUtils;

public class LinEvoTypeCompAppender<IntraType extends IntraVersionLinEvoType,
    InterType extends InterVersionLinEvoType<IntraType>> implements CompAppender<InterType>
{
    @Override
    public void appendComps(InterType smell, StringBuilder compNames)
    {
        for (IntraType comp : smell.getIntraVersionSmells())
        {
            compNames.append(comp.getIntraId());
            compNames.append(IOUtils.DELIMITER);
        }
    }
}
