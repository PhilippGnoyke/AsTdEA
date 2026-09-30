package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.io.output.OPN;
import org.astdea.io.output.printer.compappender.CompAppender;

import java.io.IOException;
import java.util.Set;

public class CompsPrinter<InterType extends InterVersionSmell> extends SmellSubPrinter<InterType> implements PrinterCore
{
    private CompAppender<InterType> compAppender;

    public CompsPrinter(Set<InterType> inters, CompAppender<InterType> compAppender)
    {
        super(inters);
        this.compAppender = compAppender;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (InterType smell : inters)
        {
            printer.print(smell.get(OPN.ID));
            StringBuilder compNames = new StringBuilder();
            compAppender.appendComps(smell, compNames);
            compNames.deleteCharAt(compNames.length() - 1);
            printer.print(compNames);
            printer.println();
        }
    }
}
