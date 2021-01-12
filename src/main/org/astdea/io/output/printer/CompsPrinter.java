package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;
import org.astdea.io.inputoutput.IOUtils;
import org.astdea.io.output.OPN;

import java.io.IOException;
import java.util.Set;

class CompsPrinter extends SubPrinter
{
    public CompsPrinter(Project project) {super(project);}

    public CdCompsPrinter initCdPrinter(Level level) {return new CdCompsPrinter(level);}

    public HdCompsPrinter initHdPrinter() {return new HdCompsPrinter();}

    public UdCompsPrinter initUdPrinter() {return new UdCompsPrinter();}

    private static <InterType extends InterVersionSmell> void printCompsCore
        (CompAppender<InterType> compAppender, CSVPrinter printer, Set<InterType> smells) throws IOException
    {
        for (InterType smell : smells)
        {
            printer.print(smell.get(OPN.PROPERTY_ID));
            StringBuilder compNames = new StringBuilder();
            compAppender.appendComps(smell, compNames);
            compNames.deleteCharAt(compNames.length() - 1);
            printer.print(compNames);
            printer.println();
        }
    }

    private class CdCompsPrinter implements PrinterCore
    {
        private Level level;

        public CdCompsPrinter(Level level)
        {
            this.level = level;
        }

        @Override
        public void print(String[] headers, CSVPrinter printer) throws IOException
        {
            printCompsCore(new InterVersionCdCompAppender(), printer, project.getCds(level));
        }
    }

    private class HdCompsPrinter implements PrinterCore
    {
        @Override
        public void print(String[] headers, CSVPrinter printer) throws IOException
        {
            printCompsCore(new InterVersionLinEvoTypeCompAppender<>(), printer, project.getHds());
        }
    }

    private class UdCompsPrinter implements PrinterCore
    {
        @Override
        public void print(String[] headers, CSVPrinter printer) throws IOException
        {
            printCompsCore(new InterVersionLinEvoTypeCompAppender<>(), printer, project.getUds());
        }
    }

    private interface CompAppender<InterType extends InterVersionSmell>
    {
        void appendComps(InterType smell, StringBuilder compNames);
    }

    private static class InterVersionLinEvoTypeCompAppender<IntraType extends IntraVersionLinEvoType,
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

    private static class InterVersionCdCompAppender implements CompAppender<InterVersionCd>
    {
        @Override
        public void appendComps(InterVersionCd smell, StringBuilder compNames)
        {
            for (Set<IntraVersionCd> compsInVersion : smell.getIntraVersionSmells())
            {
                for (IntraVersionCd comp : compsInVersion)
                {
                    compNames.append(comp.getIntraId()).append("-");
                    compNames.append(comp.getRemovalWeight()).append(IOUtils.DELIMITER);
                }
            }
        }
    }
}
