package org.astdea.io.inputoutput;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.StringConverter;
import org.apache.commons.text.WordUtils;
import org.astdea.data.Project;
import org.astdea.io.output.LogUtil;
import org.astdea.io.output.printer.MainPrinter;

import java.io.File;
import java.io.IOException;

public class TerminalExecutor
{
    private static String suppressNonAsTdEvolutionArg;

    @Parameter(names = {"-help", "-h"}, help = true, description = "Print this help")
    private static boolean _help = false;

    @Parameter(names = {"-inputDir", "-in"}, description = "Top level input dir. For each project to analyse," +
        "put a folder with the project's jars in it.", converter = StringConverter.class)
    private static String _inDir = "in";

    @Parameter(names = {"-outputDir", "-out"}, description = "Top level output dir of results",
        converter = StringConverter.class)
    private static String _outDir = "out";

    @Parameter(names = {"-suppressNonAsTdEvolution", "-sup"}, description =
        "Only output results of the modded features (td, supercycle CDs, etc.)")
    private static boolean _suppressNonAsTdEvolution = true;

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
                    String projectName = file.getName();
                    LogUtil.log("Began analysis of project " + projectName + ".");
                    String projectIn = IOUtils.makeFilePath(_inDir, projectName);
                    String projectOut = IOUtils.makeFilePath(_outDir, projectName);
                    ArcanRunner arcanRunner = new ArcanRunner
                        (projectName, projectOut, projectIn, suppressNonAsTdEvolutionArg);
                    String[] versionNames = arcanRunner.analyseAllVersions();
                    Project project = new Project(projectIn, projectOut, versionNames.length).build();
                    new MainPrinter(projectOut, project, versionNames).printAll();
                    LogUtil.log("Finished analysis of project " + projectName + ".");
                }
            }
        }
        else {System.out.println("No projects folders found in " + _inDir + ".");}
    }

    private void processArgs()
    {
        suppressNonAsTdEvolutionArg = _suppressNonAsTdEvolution ? "-suppressNonAsTdEvolution" : "";
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
            "The TdAsEA sorts the versions automatically according to their version number. " +
            "For example: If a project named \"MyProject\" shall be analysed " +
            "and the input folder is the default \"in\", " +
            "insert the jars in the folder \"in/MyProject\". " +
            "The jars have to look like \"[anyText]-[version number].jar\", " +
            "with the version number for example being \"1\", \"3.5\", \"10.7.11\", \"1.2a\", \"1.a\"." +
            "In addition, for each project, you have to provide two files with additional metadata for the analysis. " +
            "These are \"date.csv\" and \"loc.csv\" and must be put in the same folder as the jars. " +
            "Examples for their structure are given below. " +
            "The output of all analyses is put into the output folder, subdivided by projects and " +
            "further subdivided by simple version numbers (0, 1, 2, etc.) " +
            "(their mapping to actual version numbers is given in the file \"VersionNumbers.csv\". " +
            "This program runs a modified version of Arcan (arcan-1.2.1-modded.jar), " +
            "which must be provided on the top level.";
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
            "\t30642\n\n";
        System.out.println(manualPt2);
    }
}
