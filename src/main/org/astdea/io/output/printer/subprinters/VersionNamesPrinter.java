package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public class VersionNamesPrinter implements PrinterCore
{
    private String[] versionNames;

    public VersionNamesPrinter(String[] versionNames) {this.versionNames = versionNames;}

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (int i = 0; i < versionNames.length; i++)
        {
            printer.print(i);
            printer.print(FilenameUtils.getName(versionNames[i]));
            printer.println();
        }
    }
}
