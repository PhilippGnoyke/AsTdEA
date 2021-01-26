package org.astdea.io.inputoutput;

import org.apache.commons.io.FilenameUtils;
import org.astdea.io.IOUtils;
import org.astdea.io.input.FilenameComparator;
import org.astdea.io.input.IFN;
import org.astdea.io.input.LocReader;
import org.astdea.io.input.VersionsReader;
import org.astdea.io.output.LogUtil;
import org.astdea.io.output.OFN;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ArcanRunner
{
    private final static PrintStream NULL_OUTPUT_STREAM = new PrintStream(OutputStream.nullOutputStream());

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
            ArcanVersionRunner[] threads = initThreads(versionCount);
            Integer[] locs = LocReader.retrieveLocs(inDir, versionCount);
            // Disable any console output from Arcan and its usage of other libs (still shows errors)
            System.setOut(NULL_OUTPUT_STREAM);
            for (int versionId = 0; versionId < versionCount; versionId++)
            {
                threads[versionId % threads.length].addVersion(versionId, versionNames[versionId], locs[versionId]);
            }
            for (Thread thread : threads) {thread.start();}
            for (Thread thread : threads) {thread.join();}
            System.setOut(System.out);
        }
        return versionNames;
    }

    private ArcanVersionRunner[] initThreads(int versionCount)
    {
        int procCount = Runtime.getRuntime().availableProcessors();
        ArcanVersionRunner[] threads = new ArcanVersionRunner[procCount];
        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new ArcanVersionRunner(inDir, outDir, suppressNonAsTdEvolutionArg,
                projectName, versionCount);
        }
        return threads;
    }

    private String[] retrieveVersions() throws IOException
    {
        File inFolder = new File(inDir);
        if (VersionsReader.fileExists(inDir))
        {
            final int META_FILE_COUNT = 3;
            int versionCount = IOUtils.numOfFilesInFolder(inFolder) - META_FILE_COUNT;
            return VersionsReader.retrieveVersions(inDir, versionCount);
        }
        File[] files = inFolder.listFiles();
        if (files == null)
        {
            LogUtil.logSevere("No project files found in project folder " + inDir);
            return null;
        }
        List<String> versions = new ArrayList<>();
        for (final File file : files)
        {
            String fileName = file.getName();
            if (FilenameUtils.getExtension(fileName).equals(IFN.JAR))
            {
                versions.add(FilenameUtils.getBaseName(fileName));
            }
            else if(file.isDirectory())
            {
                versions.add(fileName);
            }
        }
        FilenameComparator comparator = new FilenameComparator();
        versions.sort(comparator);
        return versions.toArray(new String[0]);
    }

    public static String getArcanOutFolder(String generalOutDir, int version)
    {
        return IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION, String.valueOf(version));
    }
}
