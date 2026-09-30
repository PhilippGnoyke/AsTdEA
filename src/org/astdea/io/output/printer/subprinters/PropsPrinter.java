package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.smells.interversionsmells.InterVersionSmell;

import java.io.IOException;
import java.util.Set;

public class PropsPrinter<InterType extends InterVersionSmell> implements PrinterCore
{
    private Set<InterType> inters;

    public PropsPrinter(Set<InterType> inters) {this.inters = inters;}

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (InterType smell : inters)
        {
            for (String header : headers)
            {
                printer.print(smell.get(header));
            }
            printer.println();
        }
    }
}
