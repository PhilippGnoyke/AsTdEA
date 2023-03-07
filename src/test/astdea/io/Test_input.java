package astdea.io;

import org.astdea.io.input.*;
import org.astdea.io.IOUtils;
import org.astdea.io.output.printer.MainPrinter;
import org.astdea.io.output.printer.subprinters.PrinterCore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Test_input
{
    public final static String TEST_DIR = IOUtils.makeFilePath("src","resources","tests");


    @Test
    public void test_DateReader_retrieveDates() throws IOException
    {
        final String DATE1 = "2014-08-01";
        final String DATE2 = "2014-09-23";
        final String DATE3 = "2014-11-18";
        final String DATE4 = "2015-02-20";
        final String[] DATES = {DATE1, DATE2, DATE3, DATE4};
        final LocalDate[] EXPECTED_DATES = new LocalDate[DATES.length];
        for (int i = 0; i < DATES.length; i++)
        {
            EXPECTED_DATES[i] = LocalDate.parse(DATES[i]);
        }

        PrinterCore printerCore = (headers, printer) ->
        {
            for (String date : DATES)
            {
                printer.print(date);
                printer.println();
            }
        };
        final String TEST_DIR = IOUtils.makeFilePath("src","resources","tests");
        File file = IOUtils.makeFile(IOUtils.makeFilePath(TEST_DIR, IFN.FILE_DATES_CSV));
        MainPrinter.printCore(file, new String[]{IPN.DATE}, printerCore);
        LocalDate[] actualDates = DatesReader.retrieveDates(TEST_DIR);
        for (int i = 0; i < DATES.length; i++)
        {
            assertEquals(EXPECTED_DATES[i], actualDates[i]);
        }
        file.delete();
    }

    @Test
    public void test_LocReader_retrieveLocs() throws IOException
    {
        final int LOC1 = 2435;
        final int LOC2 = 1673;
        final int LOC3 = 895;
        final int LOC4 = 57810;
        final int[] EXPECTED_LOCS = {LOC1,LOC2,LOC3,LOC4};

        PrinterCore printerCore = (headers, printer) ->
        {
            for (int loc : EXPECTED_LOCS)
            {
                printer.print(loc);
                printer.println();
            }
        };
        File file = IOUtils.makeFile(IOUtils.makeFilePath(TEST_DIR, IFN.FILE_LOC_CSV));
        MainPrinter.printCore(file, new String[]{IPN.LOC}, printerCore);
        Integer[] actualLocs = LocReader.retrieveLocs(TEST_DIR);
        for (int i = 0; i < EXPECTED_LOCS.length; i++)
        {
            assertEquals(EXPECTED_LOCS[i], actualLocs[i].intValue());
        }
        file.delete();
    }

    @Test
    public void test_FilenameComparator_compare()
    {
        final String VERSION0 = "program-b.jar";
        final String VERSION1 = "program-1a.jar";
        final String VERSION2 = "program-1.jar";
        final String VERSION3 = "program-1.2a.jar";
        final String VERSION4 = "program-1.2.jar";
        final String VERSION5 = "program-1.2a.6.jar";
        final String VERSION6 = "program-1.2.a.jar";
        final String VERSION7 = "program-1.2.6.jar";
        final String VERSION8 = "program-2b.jar";
        final String VERSION9 = "program-2.jar";
        final String VERSION10 = "program-2.2a.6.jar";
        final String VERSION11 = "program-3.2.jar";
        final String VERSION12 = "program-3.2.a.jar";
        final String VERSION13 = "program-4.2.6.jar";
        final String VERSION14 = "program-4.3a.jar";

        final String[] EXPECTED_VERSIONS = new String[]{
            VERSION0, VERSION1, VERSION2, VERSION3, VERSION4, VERSION5, VERSION6, VERSION7,
            VERSION8, VERSION9, VERSION10, VERSION11, VERSION12, VERSION13, VERSION14,
        };
        List<String> actualVersions = new ArrayList<>(Arrays.asList(EXPECTED_VERSIONS));
        while (actualVersions.get(0).equals(EXPECTED_VERSIONS[0]))
        {
            Collections.shuffle(actualVersions);
        }
        actualVersions.sort(new FilenameComparator());
        for (int i = 0; i < EXPECTED_VERSIONS.length; i++)
        {
            assertEquals(EXPECTED_VERSIONS[i], actualVersions.get(i));
        }
    }
}