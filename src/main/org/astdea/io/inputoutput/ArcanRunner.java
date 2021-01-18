package org.astdea.io.inputoutput;

import org.apache.commons.io.FilenameUtils;
import org.astdea.io.IOUtils;
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

    private String projectName;
    private String outDir;
    private String inDir;
    private String suppressNonAsTdEvolutionArg;

    public ArcanRunner(String projectName, String generalOutDir, String inDir, String suppressNonAsTdEvolutionArg)
    {
        this.projectName = projectName;
        this.outDir = IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION);
        this.inDir = inDir;
        this.suppressNonAsTdEvolutionArg = suppressNonAsTdEvolutionArg;
    }

    public String[] analyseAllVersions() throws IOException, InterruptedException
    {
        IOUtils.makeDir(outDir);
        String[] versionNames = retrieveVersions();
        ArcanVersionRunner.resetTotalVersionsAnalysed();
        if (versionNames != null)
        {
            int versionCount = versionNames.length;
            Thread[] threads = new Thread[versionCount];
            Integer[] locs = LocReader.retrieveLocs(inDir, versionCount);
            // Disable any console output from Arcan and its usage of other libs (still shows errors)
            System.setOut(nullOutputStream);
            for (int versionId = 0; versionId < versionCount; versionId++)
            {
                threads[versionId] = analyseVersion(
                    versionNames[versionId], projectName, versionId, versionCount, locs[versionId]);
            }
            for (Thread thread : threads)
            {
                thread.join();
            }
            System.setOut(System.out);
        }
        return versionNames;
    }

    private String[] retrieveVersions()
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
                jars.add(FilenameUtils.getName(fileName));
            }
        }
        FilenameComparator comparator = new FilenameComparator();
        jars.sort(comparator);
        return jars.toArray(new String[0]);
    }

    public static String getArcanOutFolder(String generalOutDir, int version)
    {
        return IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION, String.valueOf(version));
    }

    private Thread analyseVersion(String filename, String projectName, int versionId, int versionCount, int loc)
    {
        ArcanVersionRunner versionRunner = new ArcanVersionRunner
            (inDir, outDir, suppressNonAsTdEvolutionArg, filename, projectName, versionId, versionCount, loc);
        versionRunner.start();
        return versionRunner;
    }
}
