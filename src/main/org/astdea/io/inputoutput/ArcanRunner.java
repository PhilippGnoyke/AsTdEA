package org.astdea.io.inputoutput;

import org.apache.commons.io.FilenameUtils;
import org.astdea.io.IOUtils;
import org.astdea.io.input.*;
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

    private final String projectName;
    private final String outDir;
    private final String projectInDir;
    private final String suppressNonAsTdEvolutionArg;
    private final boolean newStructure;
    private final String versionsInDir;

    public ArcanRunner(String projectName, String generalOutDir, String projectInDir, String suppressNonAsTdEvolutionArg)
    {
        this.projectName = projectName;
        this.outDir = IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION);
        this.projectInDir = projectInDir;
        this.suppressNonAsTdEvolutionArg = suppressNonAsTdEvolutionArg;
        this.newStructure = VersionsReader.isNewStructure(projectInDir);
        this.versionsInDir = newStructure ? CsvReadingUtils.getVersionsFolder(projectInDir) : projectInDir;
    }

    public String[] analyseAllVersions(boolean dontRunArcan) throws IOException, InterruptedException
    {
        IOUtils.makeDir(outDir);
        String[] versionNames = retrieveVersions();
        ArcanVersionRunner.resetTotalVersionsAnalysed();
        if (versionNames != null && !dontRunArcan)
        {
            int versionCount = versionNames.length;
            ArcanVersionRunner[] threads = initThreads(versionCount);
            Integer[] locs = LocReader.retrieveLocs(projectInDir, newStructure);
            // Disable any console output from Arcan and its usage of other libs (still shows errors)
            System.setOut(NULL_OUTPUT_STREAM);
            for (int versionId = 0; versionId < versionCount; versionId++)
            {
                int index = versionId % threads.length;
                threads[index].addVersion(versionId, versionNames[versionId], locs[versionId]);
            }
            for (Thread thread : threads) {thread.start();}
            for (Thread thread : threads) {thread.join();}
            System.setOut(System.out);
        }
        return versionNames;
    }

    private ArcanVersionRunner[] initThreads(int versionCount) throws IOException
    {
        int procCount = Runtime.getRuntime().availableProcessors();
        ArcanVersionRunner[] threads = new ArcanVersionRunner[procCount];
        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new ArcanVersionRunner(versionsInDir, outDir, suppressNonAsTdEvolutionArg,
                projectName, versionCount,newStructure);
        }
        return threads;
    }

    private String[] retrieveVersionsWithoutMetadataFile(String inDir)
    {
        File inFolder = new File(inDir);
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
            else if (file.isDirectory())
            {
                versions.add(fileName);
            }
        }
        FilenameComparator comparator = new FilenameComparator();
        versions.sort(comparator);
        return versions.toArray(new String[0]);
    }

    private String[] retrieveVersions() throws IOException
    {
        if (newStructure)
        {
            return VersionsReader.retrieveVersions(CsvReadingUtils.getStatsFolder(projectInDir));
        }
        else if(VersionsReader.isOldStructureWithMetadata(projectInDir))
        {
            return VersionsReader.retrieveVersions(projectInDir);
        }
        else
        {
            return retrieveVersionsWithoutMetadataFile(projectInDir);
        }
    }

    public static String getArcanOutFolder(String generalOutDir, int version)
    {
        return IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION, String.valueOf(version));
    }

}
