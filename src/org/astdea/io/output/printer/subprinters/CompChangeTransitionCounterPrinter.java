package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.logic.tracker.versionpairtracker.TransitionCounter;

import java.io.IOException;

public class CompChangeTransitionCounterPrinter   implements PrinterCore{

    private final TransitionCounter counter;
    private final boolean projectLevel;
    private int versionCount;

    public CompChangeTransitionCounterPrinter(TransitionCounter counter, int versionCount) {
        this.counter = counter;
        this.versionCount = versionCount;
        this.projectLevel = true;
    }

    public CompChangeTransitionCounterPrinter(TransitionCounter counter) {
        this.counter = counter;
        this.projectLevel = false;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException {
        for (String field : counter.getRowNames()) {
            printer.print(field);
            int abs = counter.getAbsVal(field);
            printer.print(abs);
            if (projectLevel)
            {
                printer.print((double)abs/versionCount);
            }
            printer.println();
        }
    }

}
