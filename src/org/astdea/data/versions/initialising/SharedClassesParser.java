package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.HelperCsvRetriever;

import java.io.IOException;

public final class SharedClassesParser {

    private static HelperCsvRetriever<String> helper = new HelperCsvRetriever<>() {
        @Override
        public String[] instantiateArray(int rows) {return new String[rows + 1];}

        @Override
        public String parseValue(String input) {return input;}
    };

    public static String[] parseSharedClasses(String outDir) throws IOException {

        String file = AsTdEvolutionPrinter.FILE_SHARED_CLASSES;
        String header = AsTdEvolutionPrinter.FQCN;
        return CsvReadingUtils.retrieveHelperCsv(outDir, file, header, helper);
    }
}
