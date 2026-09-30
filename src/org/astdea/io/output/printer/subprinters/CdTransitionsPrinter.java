package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.graphelements.Transition;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;

import java.io.IOException;

public class CdTransitionsPrinter implements PrinterCore
{
    InterVersionCd inter;

    public CdTransitionsPrinter(InterVersionCd inter)
    {
        this.inter = inter;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (Transition transition : inter.getTransitions())
        {
            IntraVersionCd source = transition.getSourceIntra();
            IntraVersionCd target = transition.getTargetIntra();

            printer.print(transition.getSourceVersion());
            printer.print(transition.getTargetVersion());
            printer.print(source.getIntraId());
            printer.print(target.getIntraId());
            printer.print(transition.getTransitionType().get());
            printer.print(source.getShape().get());
            printer.print(target.getShape().get());
            printer.println();
        }
    }
}
