package org.astdea.io.inputoutput;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.astdea.io.IOUtils;
import org.astdea.io.input.IFN;
import org.astdea.io.output.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArcanVersionRunner extends Thread
{
    private String versionsInDir;
    private String outDir;
    private String suppressNonAsTdEvolutionArg;
    private List<String> sharedClassesPaths;
    private String projectName;
    private List<Integer> versionIds;
    private int versionCount;
    private List<Integer> locs;
    private List<String> analyseArgs;
    private final boolean newStructure;

    private List<String> versionInDirs;
    private List<String> localOutDirs;

    private static int totalVersionsAnalysed;

    public static void resetTotalVersionsAnalysed()
    {
        totalVersionsAnalysed = 0;
    }

    public ArcanVersionRunner
        (String versionsInDir, String outDir, String suppressNonAsTdEvolutionArg,
         String projectName, int versionCount, boolean newStructure) throws IOException
    {
        this.versionsInDir = versionsInDir;
        this.outDir = outDir;
        this.suppressNonAsTdEvolutionArg = suppressNonAsTdEvolutionArg;
        this.projectName = projectName;
        this.versionCount = versionCount;
        this.newStructure = newStructure;
        sharedClassesPaths = new ArrayList<>();
        versionIds = new ArrayList<>();
        analyseArgs = new ArrayList<>();
        locs = new ArrayList<>();
        versionInDirs = new ArrayList<>();
        localOutDirs = new ArrayList<>();
    }

    public void addVersion(int versionId, String versionName, int loc) throws IOException
    {
        boolean isFolder;
        if (newStructure)
        {
            isFolder = true;
            versionInDirs.add(IOUtils.makeFilePath(versionsInDir,versionName, IFN.BIN));
            sharedClassesPaths.add(IOUtils.makeFilePath(versionsInDir,versionName, IFN.STATS, IFN.FILE_SHARED_CLASSES_CSV));
        }
        else
        {
            isFolder = IOUtils.isFolder(versionsInDir, versionName);
            versionInDirs.add( IOUtils.makeFilePath(versionsInDir,isFolder ? versionName : versionName + ".jar"));
        }
        versionIds.add(versionId);
        analyseArgs.add(isFolder ? "-fj" : "-jr");
        locs.add(loc);
        buildFolder(versionId,versionName, newStructure);
    }

    private void buildFolder(int versionId, String versionName, boolean newStructure) throws IOException
    {
        String localOutDir = IOUtils.makeFilePath(outDir, String.valueOf(versionId));
        IOUtils.makeDir(localOutDir);
        localOutDirs.add(localOutDir);
        if (newStructure)
        {
            String locPerClassFilePath = IOUtils.makeFilePath(versionsInDir,versionName, IFN.STATS, IOFN.FILE_LOC_PER_CLASS_CSV);
            copyLocPerClass(locPerClassFilePath, localOutDir);
        }
    }


    @Override
    public void run()
    {
        for (int i = 0; i < versionIds.size(); i++)
        {
            String[] arcanArgs =
                {
                    "-p", versionInDirs.get(i), analyseArgs.get(i),
                    "-out", localOutDirs.get(i), "-all",
                    "-loc", String.valueOf(locs.get(i)), "-asTdEvolution",
                    suppressNonAsTdEvolutionArg, "-mute"
                };
            if (sharedClassesFileExists(i))
            {
                String[] additionalArguments =
                    {
                        "-cf", sharedClassesPaths.get(i)
                    };
                arcanArgs = (String[]) ArrayUtils.addAll(arcanArgs, additionalArguments);
            }
            it.unimib.disco.essere.main.TerminalExecutor.main(arcanArgs);
            LogUtil.logInfo("Finished Arcan analysis of project " +
                projectName + ": " + ++totalVersionsAnalysed + " of " + versionCount + " versions analysed.");
        }
    }

    private boolean sharedClassesFileExists(int versionId)
    {
        if(sharedClassesPaths.size()==0)
        {
            return false;
        }
        else
        {
            return IOUtils.doesFileExist(sharedClassesPaths.get(versionId));
        }
    }

    private void copyLocPerClass(String inFilePath, String localOutDir) throws IOException
    {
        String outFilePath = IOUtils.makeFilePath(localOutDir, IOFN.FILE_LOC_PER_CLASS_CSV);
        File original = new File(inFilePath);
        File copied = new File(outFilePath);
        FileUtils.copyFile(original, copied);
    }
}
