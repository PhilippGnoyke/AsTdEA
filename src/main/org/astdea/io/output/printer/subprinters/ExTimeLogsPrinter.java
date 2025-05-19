package org.astdea.io.output.printer.subprinters;

import it.unimib.disco.essere.main.ExTimeLogger;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public class ExTimeLogsPrinter implements PrinterCore
{
    private ExTimeLogger exTimeLogger;

    public ExTimeLogsPrinter(ExTimeLogger exTimeLogger)
    {
        this.exTimeLogger = exTimeLogger;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (ExTimeLogger.ExTimeLoggerEvent event : exTimeLogger.getEvents())
        {
            printer.print(event.getEventId());
            printer.print(event.getParentId());
            printer.print(event.getDurationInMilliSecs());
            printer.print(event.getEventCount());
            printer.print(event.getEventDescription());
            printer.println();
        }
    }
}
