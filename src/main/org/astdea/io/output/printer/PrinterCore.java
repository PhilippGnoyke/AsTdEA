package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

interface PrinterCore
{
    void print(String[] headers, CSVPrinter printer) throws IOException;
}
