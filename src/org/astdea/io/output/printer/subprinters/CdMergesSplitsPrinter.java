package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.graphelements.MergeSplit;
import org.astdea.data.graphelements.MergeSplitType;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.IOUtils;

import java.io.IOException;
import java.util.Set;

import static org.astdea.data.graphelements.MergeSplitType.MERGE;

public class CdMergesSplitsPrinter implements PrinterCore {

    InterVersionCd inter;
    MergeSplitType type;

    public CdMergesSplitsPrinter(InterVersionCd inter, MergeSplitType type) {
        this.inter = inter;
        this.type = type;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException {
        Set<MergeSplit> mergesSplits = type.equals(MERGE) ? inter.getMerges() : inter.getSplits();

        for (MergeSplit mergeSplit : mergesSplits) {
            IntraVersionCd targetOrSource = mergeSplit.getTargetOrSource();
            Set<IntraVersionCd> sourcesOrTargets = mergeSplit.getSourcesOrTargets();

            printer.print(mergeSplit.getSourceVersion());
            printer.print(mergeSplit.getTargetVersion());
            printer.print(targetOrSource.getIntraId());
            printer.print(sourcesOrTargets.size());
            printPair(printer, targetOrSource.getOrder(), mergeSplit.getOverallOrderSet());
            printer.print(mergeSplit.getOverallOrderChange());
            printPair(printer, targetOrSource.getSize(), mergeSplit.getOverallSizeSet());
            printer.print(mergeSplit.getOverallSizeChange());
            printPair(printer, targetOrSource.getNumSubcycles(), mergeSplit.getOverallSubcyclesSet());
            printer.print(mergeSplit.getOverallSubcyclesChange());
            printPair(printer, targetOrSource.getMfesSize(), mergeSplit.getOverallMfesSet());
            printer.print(mergeSplit.getOverallMfesChange());
            printPair(printer, targetOrSource.getMfesWoTinysSize(), mergeSplit.getOverallMfesWoTinysSet());
            printer.print(mergeSplit.getOverallMfesWoTinysChange());
            printer.print(buildIntras(sourcesOrTargets).toString());
            printer.println();
        }
    }

    private void printPair(CSVPrinter printer, int individal, int set) throws IOException {
        if (type == MERGE) {
            printer.print(set);
            printer.print(individal);
        }
        else {
            printer.print(individal);
            printer.print(set);
        }
    }

    private StringBuilder buildIntras(Set<IntraVersionCd> intras) {
        StringBuilder compNames = new StringBuilder();
        for (IntraVersionCd intra : intras) {
            compNames.append(intra.getIntraId());
            compNames.append(IOUtils.DELIMITER);
        }
        compNames.deleteCharAt(compNames.length() - 1);
        return compNames;
    }
}
