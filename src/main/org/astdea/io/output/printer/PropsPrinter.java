package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionSmell;

import java.io.IOException;
import java.util.Set;

class PropsPrinter
{
    private Project project;

    PropsPrinter(Project project) {this.project = project;}

    public CdPropsPrinter initCdPrinter(Level level) {return new CdPropsPrinter(level);}

    public HdPropsPrinter initHdPrinter() {return new HdPropsPrinter();}

    public UdPropsPrinter initUdPrinter() {return new UdPropsPrinter();}

    private static <InterType extends InterVersionSmell> void printSmellPropsCore
        (String[] headers, CSVPrinter printer, Set<InterType> smells) throws IOException
    {
        for (InterType smell : smells)
        {
            for (String header : headers)
            {
                printer.print(smell.get(header));
            }
            printer.println();
        }
    }

    class CdPropsPrinter implements PrinterCore
    {
        private Level level;

        public CdPropsPrinter(Level level)
        {
            this.level = level;
        }

        @Override
        public void print(String[] headers, CSVPrinter printer) throws IOException
        {
            printSmellPropsCore(headers, printer, project.getCds(level));
        }
    }

    class HdPropsPrinter implements PrinterCore
    {
        @Override
        public void print(String[] headers, CSVPrinter printer) throws IOException
        {
            printSmellPropsCore(headers, printer, project.getHds());
        }
    }

    class UdPropsPrinter implements PrinterCore
    {
        @Override
        public void print(String[] headers, CSVPrinter printer) throws IOException
        {
            printSmellPropsCore(headers, printer, project.getUds());
        }
    }
}
