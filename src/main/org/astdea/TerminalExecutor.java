package org.astdea;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.StringConverter;
import it.unimib.disco.essere.main.ETLE;
import it.unimib.disco.essere.main.ExTimeLogger;
import org.apache.commons.text.WordUtils;
import org.astdea.data.Project;
import org.astdea.io.IOUtils;
import org.astdea.io.inputoutput.ArcanRunner;
import org.astdea.io.output.LogUtil;
import org.astdea.io.output.printer.MainPrinter;

import java.io.File;
import java.io.IOException;

public class TerminalExecutor
{
    private static String suppressNonAsTdEvolutionArg;

    @Parameter(names = {"-help", "-h"}, help = true, description = "Print this help")
    private static boolean _help = false;

    @Parameter(names = {"-inputDir", "-in"}, description = "Top level input dir. For each project to analyse, " +
        "put a folder with the project's jars in it.", converter = StringConverter.class)
    private static String _inDir = "in";

    @Parameter(names = {"-outputDir", "-out"}, description = "Top level output dir of results",
        converter = StringConverter.class)
    private static String _outDir = "out";

    @Parameter(names = {"-dontSuppressNonAsTdEvolution", "-noSup"}, description =
        "Only output results of the modded features (TD, supercycle CDs, etc.)")
    private static boolean _dontSuppressNonAsTdEvolution = false;

    @Parameter(names = {"-dontRunArcan", "-noA"}, description =
        "Don't run Arcan but parse previously generated .csv files in the out folder.")
    private static boolean _dontRunArcan = false;


    public static void main(String[] args) throws IOException, InterruptedException
    {
        TerminalExecutor tt = new TerminalExecutor();
        JCommander commander = new JCommander(tt);
        commander.setCaseSensitiveOptions(false);
        commander.setProgramName("java -jar AsTdEA.jar");
        commander.parse(args);
        tt.processArgs();
        if (_help) {printHelp(commander);}
        else {tt.run();}
    }

    private void run() throws IOException, InterruptedException
    {
        System.out.println("Get help with -help");
        File directory = new File(_inDir);
        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    ExTimeLogger exTimeLogger = new ExTimeLogger();
                    String projectName = file.getName();
                    LogUtil.logInfo("Began analysis of project " + projectName + ".");
                    String projectInDir = IOUtils.makeFilePath(_inDir, projectName);
                    String projectOutDir = IOUtils.makeFilePath(_outDir, projectName);
                    exTimeLogger.logEventStart(ETLE.Event.ARCAN_RUNNING);
                    ArcanRunner arcanRunner = new ArcanRunner
                        (projectName, projectOutDir, projectInDir, suppressNonAsTdEvolutionArg);
                    String[] versionNames = arcanRunner.analyseAllVersions(_dontRunArcan);
                    exTimeLogger.logEventEnd(ETLE.Event.ARCAN_RUNNING);
                    Project project = new Project(projectInDir, projectOutDir, versionNames.length,exTimeLogger).build();
                    exTimeLogger.logEventStart(ETLE.Event.ASTDEA_PRINTING);
                    new MainPrinter(projectOutDir, project, versionNames,exTimeLogger).printAll();
                    LogUtil.logInfo("Finished analysis of project " + projectName + ".");
                }
            }
        }
        else {System.out.println("No projects folders found in " + _inDir + ".");}
    }

    private void processArgs()
    {
        suppressNonAsTdEvolutionArg = _dontSuppressNonAsTdEvolution ? "" : "-suppressNonAsTdEvolution";
    }

    private static void printHelp(JCommander commander)
    {
        final int PRINT_WIDTH = 120;
        commander.usage();
        String manualPt1 = "Manual:\n" +
            "This is the Technical-debt-Architecture-smell-Evolution-Analyser (TdAsEA). " +
            "It can analyse the evolution of multiple projects with multiple versions each. " +
            "Each version has to be provided as a .jar. " +
            "All jars of a project have to be inserted in a folder with the project's name, " +
            "located in the general input folder. " +
            "Each jar has to contain its version number at the end of its name. " +
            "The TdAsEA can sort the versions automatically according to their version number. " +
            "For example: If a project named \"MyProject\" shall be analysed " +
            "and the input folder is the default \"in\", " +
            "insert the jars in the folder \"in/MyProject\". " +
            "The jars have to look like \"[anyText]-[version number].jar\", " +
            "with the version number for example being \"1\", \"3.5\", \"10.7.11\", \"1.2a\", \"1.a\". " +
            "Alternatively, provide an additional file that contains the version numbers from oldest to newest. " +
            "This file is \"versions.csv\" and has to be put into the same folder as the jars. " +
            "An example for its structure is given below. " +
            "In addition, for each project, you always have to provide " +
            "two files with additional metadata for the analysis. " +
            "These are \"date.csv\" and \"loc.csv\" and must also be put in the same folder as the jars. " +
            "Examples for their structure are given below. " +
            "The output of all analyses is put into the output folder, subdivided by projects and " +
            "further subdivided by simple version numbers (0, 1, 2, etc.) " +
            "(their mapping to actual version numbers is given in the file \"VersionNumbers.csv\". " +
            "This program runs a modified version of Arcan (arcan-1.2.1-modded.jar), " +
            "which must be provided on the top level. " +
            "If you already generated modded Arcan output, you don't have to run it again for AsTdEA. " +
            "Simply add the \"-noA\" argument and just provide the three meta data .csv files. " +
            "Input .jar files are not necessary in this case, " +
            "but make sure that the meta data .csv files are still in the input folder " +
            "and that the out folder is already filled with intraVersion output generated by Arcan.";


        System.out.println(WordUtils.wrap(manualPt1, PRINT_WIDTH));
        String manualPt2 = "\nExample for \"date.csv\" " +
            "(contains the release dates of each version (sorted in ascending order) in YYYY-MM-DD):\n" +
            "\tdate\n" +
            "\t2014-08-01\n" +
            "\t2014-09-23\n" +
            "\t2014-11-18\n" +
            "\t2015-02-20\n\n" +
            "Example for \"loc.csv\" (contains the lines of code (LOC) of each version " +
            "(sorted in ascending order of their release date)):\n" +
            "\tloc\n" +
            "\t25247\n" +
            "\t27410\n" +
            "\t31582\n" +
            "\t30642\n\n" +
            "Example for \"versions.csv\" (contains the version number of each version " +
            "(sorted in ascending order of their release date)):\n" +
            "\tversion\n" +
            "\tmyProject-1.1\n" +
            "\tmyProject-1.2g\n" +
            "\tmyProject-1.2final\n" +
            "\tmyProject-1.3\n\n";
        System.out.println(manualPt2);
    }
}
