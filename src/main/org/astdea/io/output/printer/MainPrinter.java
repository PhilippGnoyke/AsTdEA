package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;
import org.astdea.data.smells.Level;
import org.astdea.data.versions.Version;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.inputoutput.IOUtils;
import org.astdea.io.output.OFN;
import org.astdea.io.output.ResultHeaders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainPrinter
{
    private String intraOutDir;
    private String interOutDir;
    private Project project;
    private PropsPrinter propsPrinter;
    private CompsPrinter compsPrinter;

    public MainPrinter(String generalOutDir, Project project)
    {
        this.intraOutDir = IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION);
        this.interOutDir = IOUtils.makeFilePath(generalOutDir, OFN.INTER_VERSION);
        this.project = project;
        this.propsPrinter = new PropsPrinter(project);
        this.compsPrinter = new CompsPrinter(project);
    }

    public void printAll() throws IOException, NullPointerException
    {
        IOUtils.makeDir(interOutDir);
        printProjectMetrics();
        printCds();
        printHds();
        printUds();
        updateVersionMetrics();
    }

    private void printCore(File file, String[] headers, PrinterCore printerCore)
        throws IOException, NullPointerException
    {
        CSVFormat formatter = CSVFormat.EXCEL.withHeader(headers);
        FileWriter writer = new FileWriter(file);
        CSVPrinter printer = new CSVPrinter(writer, formatter);
        printerCore.print(headers, printer);
        printer.close();
        writer.close();
    }

    private void printCore(String fileName, String[] headers, PrinterCore printerCore)
        throws IOException, NullPointerException
    {
        File fileCsv = IOUtils.makeFile(interOutDir, fileName);
        printCore(fileCsv, headers, printerCore);
    }

    public void printProjectMetrics() throws IOException, NullPointerException
    {
        printCore(IOFN.FILE_PROJECT, ResultHeaders.getProjectMetricsHeaders(), new ProjectMetricsPrinter(project));
    }

    public void printCds() throws IOException, NullPointerException
    {
        printCdsCore(Level.CLASS, IOFN.FILE_CLASS_CDS_PROPS, IOFN.FILE_CLASS_CDS_COMPS, OFN.FILE_CLASS_CDS_EDGES);
        printCdsCore(Level.PACK, IOFN.FILE_PACK_CDS_PROPS, IOFN.FILE_PACK_CDS_COMPS, OFN.FILE_PACK_CDS_EDGES);
    }

    public void printCdsCore(Level level, String fileProps, String fileComps, String fileEdges)
        throws IOException, NullPointerException
    {
        printCore(fileProps, ResultHeaders.getCdPropHeaders(), propsPrinter.initCdPrinter(level));
        printCore(fileComps, ResultHeaders.getGeneralSmellCompHeaders(), compsPrinter.initCdPrinter(level));
        printCore(fileEdges, ResultHeaders.getCdEdgesHeaders(), new CdEdgesPrinter(project, level));
    }

    public void printHds() throws IOException, NullPointerException
    {
        printCore(IOFN.FILE_HDS_PROPS, ResultHeaders.getGeneralSmellPropHeaders(), propsPrinter.initHdPrinter());
        printCore(IOFN.FILE_HDS_COMPS, ResultHeaders.getGeneralSmellCompHeaders(), compsPrinter.initHdPrinter());
    }

    public void printUds() throws IOException, NullPointerException
    {
        printCore(IOFN.FILE_UDS_PROPS, ResultHeaders.getGeneralSmellPropHeaders(), propsPrinter.initUdPrinter());
        printCore(IOFN.FILE_UDS_COMPS, ResultHeaders.getGeneralSmellCompHeaders(), compsPrinter.initUdPrinter());
    }

    public void updateVersionMetrics() throws IOException
    {
        for (Version version : project.getVersions())
        {
            String path = IOUtils.makeFilePath(intraOutDir, String.valueOf(version.getVersionId()), IOFN.FILE_PROJECT);
            VersionMetricsPrinter printer = new VersionMetricsPrinter(version, path);
            printCore(IOUtils.makeFile(path), ResultHeaders.getVersionHeaders(), printer);
        }
    }
}
