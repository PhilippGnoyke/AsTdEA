package org.astdea.io.input;

import org.astdea.io.IOUtils;

import java.io.IOException;
import java.nio.file.Paths;

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

    public static boolean isOldStructureWithMetadata(String inDir)
    {
        return IOUtils.makeFile(inDir, IFN.FILE_VERSIONS_CSV).exists();
    }

    public static boolean isNewStructure(String inDir)
    {
        return IOUtils.makeFile(CsvReadingUtils.getStatsFolder(inDir), IFN.FILE_VERSIONS_CSV).exists();
    }


}
