package org.astdea.io.input;

import org.astdea.io.IOUtils;

import java.io.IOException;

public final class VersionsReader
{
    private VersionsReader() {}

    public static String[] retrieveVersions(String inDir) throws IOException
    {
        HelperCsvRetriever<String> helper = new HelperCsvRetriever<>()
        {
            @Override
            public String[] instantiateArray(int rows) {return new String[rows];}

            @Override
            public String parseValue(String input) {return input;}
        };
        return CsvReadingUtils.retrieveHelperCsv(inDir, IFN.FILE_VERSIONS_CSV, IPN.VERSION, helper);
    }

    public static boolean fileExists(String inDir)
    {
        return IOUtils.makeFile(inDir, IFN.FILE_VERSIONS_CSV).exists();
    }
}
