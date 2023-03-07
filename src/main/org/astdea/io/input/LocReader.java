package org.astdea.io.input;

import java.io.IOException;

public final class LocReader
{
    private LocReader() {}

    public static Integer[] retrieveLocs(String inDir) throws IOException
    {
        HelperCsvRetriever<Integer> helper = new HelperCsvRetriever<>()
        {
            @Override
            public Integer[] instantiateArray(int rows) {return new Integer[rows];}

            @Override
            public Integer parseValue(String input) {return Integer.parseInt(input);}
        };
        return CsvReadingUtils.retrieveHelperCsv(inDir, IFN.FILE_LOC_CSV, IPN.LOC, helper);
    }
}
