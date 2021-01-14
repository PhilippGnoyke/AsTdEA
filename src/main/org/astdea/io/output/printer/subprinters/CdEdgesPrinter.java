package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.inputoutput.IOUtils;
import org.astdea.io.output.OPN;
import org.astdea.logic.mapping.CdMappings;

import java.io.IOException;
import java.util.Set;

public class CdEdgesPrinter extends SmellSubPrinter<InterVersionCd> implements PrinterCore
{
    CdMappings mappings;

    public CdEdgesPrinter(Set<InterVersionCd> inters, CdMappings mappings)
    {
        super(inters);
        this.mappings = mappings;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (InterVersionCd inter : inters)
        {
            printer.print(inter.get(OPN.ID));
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
