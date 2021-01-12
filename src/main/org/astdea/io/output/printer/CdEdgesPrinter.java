package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.inputoutput.IOUtils;
import org.astdea.io.output.OPN;
import org.astdea.logic.mapping.CdMappings;

import java.io.IOException;
import java.util.Set;

class CdEdgesPrinter extends SubPrinter implements PrinterCore
{
    private Level level;
    CdMappings mappings;

    public CdEdgesPrinter(Project project, Level level)
    {
        super(project);
        this.level = level;
        this.mappings = project.getCdMappingsMap(level);
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (InterVersionCd inter : project.getCds(level))
        {
            printer.print(inter.get(OPN.PROPERTY_ID));
            printer.print(buildInter(inter));
            printer.println();
        }
    }

    private StringBuilder buildInter(InterVersionCd inter)
    {
        StringBuilder compNames = new StringBuilder();
        for (Set<IntraVersionCd> intrasInVersion : inter.getIntraVersionSmells())
        {
            for (IntraVersionCd intra : intrasInVersion)
            {
                buildIntra(intra, compNames);
            }
        }
        return compNames;
    }

    private void buildIntra(IntraVersionCd intra, StringBuilder compNames)
    {
        IntraId oldId = intra.getIntraId();
        Set<IntraVersionCd> edges = mappings.getByOldId(oldId);
        if (edges != null)
        {
            for (IntraVersionCd edge : edges)
            {
                compNames.append(oldId).append("->");
                compNames.append(edge.getIntraId());
                compNames.append(IOUtils.DELIMITER);
            }
        }
    }
}
