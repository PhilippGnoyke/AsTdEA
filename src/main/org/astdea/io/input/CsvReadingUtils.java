package org.astdea.io.input;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.astdea.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class CsvReadingUtils
{
    private CsvReadingUtils() {}

    public static CSVParser initCsvParser(String file, String[] headers) throws IOException
    {
        return CSVFormat.DEFAULT
            .withHeader(headers)
            .withFirstRecordAsHeader()
            .parse(new FileReader(file));
    }

    public static List<Map<String, String>> readCsvRows(String file, String[] headers) throws IOException
    {
        CSVParser records = initCsvParser(file, headers);
        List<Map<String, String>> vals = new ArrayList<>();
        for (CSVRecord record : records)
        {
            Map<String, String> row = record.toMap();
            vals.add(row);
        }
        records.close();
        return vals;
    }

    public static Map<String, List<String>> readCsvColumns(String file, String[] headers) throws IOException
    {
        CSVParser records = initCsvParser(file, headers);
        Map<String, List<String>> vals = new HashMap<>();
        for (String header : headers)
        {
            vals.put(header, new ArrayList<>());
        }
        for (CSVRecord record : records)
        {
            for (String header : headers)
            {
                vals.get(header).add(record.get(header));
            }
        }
        records.close();
        return vals;
    }

    public static Map<String, String> readCsvColumnsOf1RowFile(String file, String[] headers) throws IOException
    {
        CSVParser records = initCsvParser(file, headers);
        Map<String, String> vals = records.iterator().next().toMap();
        records.close();
        return vals;
    }

    public static <Type> Type[] retrieveHelperCsv(String inDir, String fileName, String header, int rows,
                                                  HelperCsvRetriever<Type> helper) throws IOException
    {
        Type[] result = helper.instantiateArray(rows);
        String file = IOUtils.makeFilePath(inDir, fileName);
        CSVParser records = CsvReadingUtils.initCsvParser(file, new String[]{header});
        int i = 0;
        for (CSVRecord record : records)
        {
            String val = record.get(header);
            if (val.equals("")) {throwError(file);}
            result[i] = helper.parseValue(val);
            i++;
        }
        if (i != rows) {throwError(file);}
        records.close();
        return result;
    }

    private static void throwError(String file)
    {
        throw new InputMismatchException("The file " + file +
            " contains fewer values than provided versions.");
    }
}

