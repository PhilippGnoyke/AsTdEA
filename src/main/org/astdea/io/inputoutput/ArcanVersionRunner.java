package org.astdea.io.inputoutput;

import org.astdea.io.output.LogUtil;

public class ArcanVersionRunner extends Thread
{
    private String inDir;
    private String outDir;
    private String suppressNonAsTdEvolutionArg;
    private String filename;
    private String projectName;
    private int versionId;
    private int versionCount;
    private int loc;

    private static int totalVersionsAnalysed;

    public ArcanVersionRunner
        (String inDir, String outDir, String suppressNonAsTdEvolutionArg, String filename, String projectName,
         int versionId, int versionCount, int loc)
    {
        this.inDir = inDir;
        this.outDir = outDir;
        this.suppressNonAsTdEvolutionArg = suppressNonAsTdEvolutionArg;
        this.filename = filename;
        this.projectName = projectName;
        this.versionId = versionId;
        this.versionCount = versionCount;
        this.loc = loc;
    }

    public static void resetTotalVersionsAnalysed()
    {
        totalVersionsAnalysed = 0;
    }

    @Override
    public void run()
    {
        String localOutDir = IOUtils.makeFilePath(outDir, String.valueOf(versionId));
        IOUtils.makeDir(localOutDir);
        String[] arcanArgs =
            {
                "-p", IOUtils.makeFilePath(inDir, filename), "-jr",
                "-out", localOutDir, "-all",
                "-loc", String.valueOf(loc), "-asTdEvolution",
                suppressNonAsTdEvolutionArg, "-mute"
            };
        it.unimib.disco.essere.main.TerminalExecutor.main(arcanArgs);
        LogUtil.log("Finished Arcan analysis of project " +
            projectName + ": " + ++totalVersionsAnalysed + " of " + versionCount + " versions analysed.");
    }
}
