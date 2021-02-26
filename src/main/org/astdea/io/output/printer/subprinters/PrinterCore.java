package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public interface PrinterCore
{
    void print(String[] headers, CSVPrinter printer) throws IOException;
}
