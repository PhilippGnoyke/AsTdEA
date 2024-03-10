package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.versions.Version;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.output.ResultHeaders;

import java.io.IOException;
import java.util.*;

public class IntraVersionPropsPrinter<IntraType extends IntraVersionSmell> implements PrinterCore
{
    private String[] headersOld;
    private Version version;
    private String file;
    private Map<IntraId,IntraType> intras;
    private List<String[]> vals = new ArrayList<>();
    private int headersNewCount;
    private boolean isCd;

    public IntraVersionPropsPrinter(Version version, String file, String[] headersOld, Map<IntraId,IntraType> intras,
                                    int headersNewCount, boolean isCd) throws IOException
    {
        this.headersOld = headersOld;
        this.version = version;
        this.file = file;
        this.intras = intras;
        this.headersNewCount = headersNewCount;
        this.isCd = isCd;
        initVals();
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (String[] row : vals)
        {
            for (String val : row)
            {
                printer.print(val);
            }
            printer.println();
        }
    }

    private void initVals() throws IOException
    {
        List<Map<String, String>> oldVals = CsvReadingUtils.readCsvRows(file,headersOld);

        for(Map<String, String> row : oldVals)
        {
            String[] newVals = new String[headersOld.length+headersNewCount];
            IntraId intraId = new IntraId(version.getVersionId(), Integer.parseInt(row.get(IPN.ID)));
            IntraType intra = intras.get(intraId);

            int valsInd = 0;
            for (String header : headersOld)
            {
                newVals[valsInd] = row.get(header);
                valsInd++;
            }
            newVals[valsInd++]= String.valueOf(intra.getAge());
            newVals[valsInd++]= String.valueOf(intra.getRemainingAge());
            if(isCd)
            {
                addCdSpecificVals(newVals,valsInd,(IntraVersionCd) intra);
            }
            vals.add(newVals);
        }
    }

    //Override in child classes
    private void addCdSpecificVals(String[] newVals, int valsInd, IntraVersionCd intra)
    {
        newVals[valsInd++]= String.valueOf(intra.getNumDirectPreds());
        newVals[valsInd++]= String.valueOf(intra.getNumAllPreds());
        newVals[valsInd++]= String.valueOf(intra.getNumDirectSuccs());
        newVals[valsInd]= String.valueOf(intra.getNumAllSuccs());
    }
}
