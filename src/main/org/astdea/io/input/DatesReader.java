package org.astdea.io.input;

import java.io.IOException;
import java.time.LocalDate;

public final class DatesReader
{
    private DatesReader() {}

    public static LocalDate[] retrieveDates(String inDir) throws IOException
    {
        HelperCsvRetriever<LocalDate> helper = new HelperCsvRetriever<>()
        {
            @Override
            public LocalDate[] instantiateArray(int rows) {return new LocalDate[rows + 1];}

            @Override
            public LocalDate parseValue(String input) {return parseDateRow(input);}
        };
        return CsvReadingUtils.retrieveHelperCsv(inDir, IFN.FILE_DATES_CSV, IPN.DATE, helper);
    }

    private static LocalDate parseDateRow(String input)
    {
        return LocalDate.parse(input);
    }
}
