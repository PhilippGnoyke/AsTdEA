package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.CompChangeAffectedCounter;

import java.io.IOException;

public class CompChangeAffectedCounterPrinter implements PrinterCore {

    private final CompChangeAffectedCounter counter;
    private final boolean projectLevel;
    private int versionCount;

    public CompChangeAffectedCounterPrinter(CompChangeAffectedCounter counter, int versionCount) {
        this.counter = counter;
        this.versionCount = versionCount;
        this.projectLevel = true;
    }

    public CompChangeAffectedCounterPrinter(CompChangeAffectedCounter counter) {
        this.counter = counter;
        this.projectLevel = false;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException {
        for (String field : counter.getRowNames()) {
            printer.print(field);
            printer.print(counter.getAbsVal(field));
            if (projectLevel) {
                printer.print((double) counter.getAbsVal(field) / versionCount);
                printer.print(counter.getRelVal(field) / versionCount);
            }
            else {
                printer.print(counter.getRelVal(field));
            }
            printer.println();
        }
    }
}
