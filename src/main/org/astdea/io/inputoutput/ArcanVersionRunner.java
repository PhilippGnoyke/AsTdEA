package org.astdea.io.inputoutput;

import org.astdea.io.IOUtils;
import org.astdea.io.output.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ArcanVersionRunner extends Thread
{
    private String inDir;
    private String outDir;
    private String suppressNonAsTdEvolutionArg;
    private List<String> fullFilenames;
    private String projectName;
    private List<Integer> versionIds;
    private int versionCount;
    private List<Integer> locs;
    private List<String> analyseArgs;

    private static int totalVersionsAnalysed;

    public static void resetTotalVersionsAnalysed()
    {
        totalVersionsAnalysed = 0;
    }

    public ArcanVersionRunner
        (String inDir, String outDir, String suppressNonAsTdEvolutionArg, String projectName, int versionCount)
    {
        this.inDir = inDir;
        this.outDir = outDir;
        this.suppressNonAsTdEvolutionArg = suppressNonAsTdEvolutionArg;
        this.projectName = projectName;
        this.versionCount = versionCount;
        fullFilenames = new ArrayList<>();
        versionIds = new ArrayList<>();
        analyseArgs = new ArrayList<>();
        locs = new ArrayList<>();
    }

    public void addVersion(int versionId, String filename, int loc)
    {
        boolean isFolder = IOUtils.isFolder(inDir, filename);
        fullFilenames.add(isFolder ? filename : filename + ".jar");
        versionIds.add(versionId);
        analyseArgs.add(isFolder ? "-fj" : "-jr");
        locs.add(loc);
    }

    @Override
    public void run()
    {
        for (int i = 0; i < versionIds.size(); i++)
        {
            String localOutDir = IOUtils.makeFilePath(outDir, String.valueOf(versionIds.get(i)));
            IOUtils.makeDir(localOutDir);
            String[] arcanArgs =
                {
                    "-p", IOUtils.makeFilePath(inDir, fullFilenames.get(i)), analyseArgs.get(i),
                    "-out", localOutDir, "-all",
                    "-loc", String.valueOf(locs.get(i)), "-asTdEvolution",
                    suppressNonAsTdEvolutionArg, "-mute"
                };
            it.unimib.disco.essere.main.TerminalExecutor.main(arcanArgs);
            LogUtil.logInfo("Finished Arcan analysis of project " +
                projectName + ": " + ++totalVersionsAnalysed + " of " + versionCount + " versions analysed.");
        }
    }
}
