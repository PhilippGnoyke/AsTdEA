package org.astdea.io.inputoutput;

import org.apache.commons.io.FilenameUtils;
import org.astdea.io.input.FilenameComparator;
import org.astdea.io.input.LocReader;
import org.astdea.io.output.OFN;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ArcanRunner
{
    private final static PrintStream nullOutputStream = new PrintStream(OutputStream.nullOutputStream());

    private String outDir;
    private String inDir;
    private String suppressNonAsTdEvolutionArg;

    public ArcanRunner(String generalOutDir, String inDir, String suppressNonAsTdEvolutionArg)
    {
        this.outDir = IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION);
        this.inDir = inDir;
        this.suppressNonAsTdEvolutionArg = suppressNonAsTdEvolutionArg;
    }

    public int analyseAllVersions() throws IOException
    {
        IOUtils.makeDir(outDir);
        List<String> jars = retrieveVersions();
        if (jars != null)
        {
            Integer[] locs = LocReader.retrieveLocs(inDir, jars.size());
            for (int i = 0; i < jars.size(); i++)
            {
                analyseVersion(jars.get(i), i, locs[i]);
            }
            return jars.size();
        }
        return 0;
    }

    private List<String> retrieveVersions()
    {
        File inFolder = new File(inDir);
        File[] files = inFolder.listFiles();
        if (files == null)
        {
            System.out.println("No jars found in project folder " + inDir);
            return null;
        }
        List<String> jars = new ArrayList<>();
        for (final File file : files)
        {
            String fileName = file.getName();
            if (FilenameUtils.getExtension(fileName).equals("jar"))
            {
                jars.add(fileName);
            }
        }
        FilenameComparator comparator = new FilenameComparator();
        jars.sort(comparator);
        return jars;
    }

    public static String getArcanOutFolder(String generalOutDir, int version)
    {
        return IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION, String.valueOf(version));
    }

    private void analyseVersion(String filename, int version, int loc)
    {
        String localOutDir = IOUtils.makeFilePath(outDir, String.valueOf(version));
        IOUtils.makeDir(localOutDir);
        String[] arcanArgs =
            {
                "-p", IOUtils.makeFilePath(inDir, filename), "-jr",
                "-out", localOutDir, "-all",
                "-loc", String.valueOf(loc), "-asTdEvolution",
                suppressNonAsTdEvolutionArg, "-mute"
            };
        // Disable any console output from Arcan and its usage of other libs (still shows errors)
        System.setOut(nullOutputStream);
        it.unimib.disco.essere.main.TerminalExecutor.main(arcanArgs);
        System.setOut(System.out);
    }
}
