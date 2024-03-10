package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.graphelements.MergeSplit;
import org.astdea.data.graphelements.MergeSplitType;
import org.astdea.data.graphelements.Transition;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.IOUtils;
import org.astdea.io.output.OPN;

import java.io.IOException;
import java.util.Set;

public class CdMergesSplitsPrinter implements PrinterCore
{
    InterVersionCd inter;
    MergeSplitType type;

    public CdMergesSplitsPrinter(InterVersionCd inter, MergeSplitType type)
    {
        this.inter = inter;
        this.type = type;
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        Set<MergeSplit> mergesSplits = type.equals(MergeSplitType.MERGE)?inter.getMerges() : inter.getSplits();

        for (MergeSplit mergeSplit : mergesSplits)
        {
            IntraVersionCd targetOrSource = mergeSplit.getTargetOrSource();
            Set<IntraVersionCd> sourcesOrTargets = mergeSplit.getSourcesOrTargets();

            printer.print(mergeSplit.getSourceVersion());
            printer.print(mergeSplit.getTargetVersion());
            printer.print(targetOrSource.getIntraId());
            printer.print(sourcesOrTargets.size());
            printer.print(targetOrSource.getOrder());
            printer.print(mergeSplit.getOverallOrderSet());
            printer.print(mergeSplit.getOverallOrderChange());
            printer.print(targetOrSource.getSize());
            printer.print(mergeSplit.getOverallSizeSet());
            printer.print(mergeSplit.getOverallSizeChange());
            printer.print(targetOrSource.getNumSubcycles());
            printer.print(mergeSplit.getOverallSubcyclesSet());
            printer.print(mergeSplit.getOverallSubcyclesChange());
            printer.print(buildIntras(sourcesOrTargets).toString());
            printer.println();
        }
    }

    private StringBuilder buildIntras(Set<IntraVersionCd> intras)
    {
        StringBuilder compNames = new StringBuilder();
        for (IntraVersionCd intra : intras)
        {
            compNames.append(intra.getIntraId());
            compNames.append(IOUtils.DELIMITER);
        }
        compNames.deleteCharAt(compNames.length()-1);
        return compNames;
    }
}
