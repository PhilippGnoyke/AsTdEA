package org.astdea.io.input;

import java.io.IOException;
import java.time.LocalDate;

public final class DateReader
{
    private DateReader() {}

    public static LocalDate[] retrieveDates(String inDir, int numVersions) throws IOException
    {
        HelperCsvRetriever<LocalDate> helper = new HelperCsvRetriever<>()
        {
            @Override
            public LocalDate[] instantiateArray(int rows) { return new LocalDate[rows + 1]; }

            @Override
            public LocalDate parseValue(String input) { return LocalDate.parse(input);}
        };
        return CsvReadingUtils.retrieveHelperCsv(inDir, IFN.FILE_DATE_CSV, IPN.DATE, numVersions, helper);
    }
}
