package org.astdea.io.input;

import java.io.IOException;
import java.nio.file.Paths;

public final class LocReader
{
    private LocReader() {}

    public static Integer[] retrieveLocs(String inDir, boolean newStructure) throws IOException
    {
        String folder = newStructure ? Paths.get(inDir, IFN.STATS).toString() : inDir;
        String locFile = newStructure ? IFN.FILE_LOC_SHARED_CSV : IFN.FILE_LOC_CSV;
        HelperCsvRetriever<Integer> helper = new HelperCsvRetriever<>()
        {
            @Override
            public Integer[] instantiateArray(int rows) {return new Integer[rows];}

            @Override
            public Integer parseValue(String input) {return Integer.parseInt(input);}
        };
        return CsvReadingUtils.retrieveHelperCsv(folder, locFile, IPN.LOC, helper);
    }
}
