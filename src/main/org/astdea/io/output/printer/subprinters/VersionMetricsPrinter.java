package org.astdea.io.output.printer.subprinters;

import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.versions.Version;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.output.ResultHeaders;

import java.io.IOException;
import java.util.Map;

public class VersionMetricsPrinter implements PrinterCore
{
    private static final String[] headers1 = ResultHeaders.versionHeadersPt1;
    private static final String[] headers2 = ResultHeaders.versionHeadersPt2;
    private static final String[] headers3 = ResultHeaders.versionHeadersPt3;

    private Version version;
    private String file;
    private String[] vals;

    public VersionMetricsPrinter(Version version, String file) throws IOException
    {
        this.version = version;
        this.file = file;
        vals = new String[headers1.length + headers2.length + headers3.length];
        initVals();
    }

    @Override
    public void print(String[] headers, CSVPrinter printer) throws IOException
    {
        for (String val : vals)
        {
            printer.print(val);
        }
        printer.println();
    }

    private void initVals() throws IOException
    {
        int valsInd = 0;
        valsInd = initNewVals(headers1, valsInd);
        valsInd = initOldVals(headers2, valsInd);
        valsInd = initNewVals(headers3, valsInd);
    }

    private int initNewVals(String[] headers, int valsInd)
    {
        for (String header : headers)
        {
            vals[valsInd] = version.get(header).toString();
            valsInd++;
        }
        return valsInd;
    }

    private int initOldVals(String[] headers, int valsInd) throws IOException
    {
        Map<String, String> presentVals = CsvReadingUtils.readCsvColumnsOf1RowFile
            (file, ResultHeaders.versionHeadersPt2);
        for (String header : headers)
        {
            vals[valsInd] = presentVals.get(header);
            valsInd++;
        }
        return valsInd;
    }
}
