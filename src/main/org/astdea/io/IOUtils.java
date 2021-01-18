package org.astdea.io;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class IOUtils
{
    public static final String DELIMITER = AsTdEvolutionPrinter.DELIMITER;

    private IOUtils() {}

    public static void makeDir(String fullName)
    {
        new File(fullName).mkdirs();
    }

    public static Set<String> parseStringToSet(String input, String delimiter)
    {
        return new HashSet<>(Arrays.asList(input.split(delimiter)));
    }

    public static String makeFilePath(String... elements)
    {
        return String.join(File.separator, elements);
    }

    public static File makeFile(String folderName, String fileName)
    {
        return Paths.get(folderName, fileName).toAbsolutePath().toFile();
    }

    public static File makeFile(String path)
    {
        return Paths.get(path).toAbsolutePath().toFile();
    }
}
