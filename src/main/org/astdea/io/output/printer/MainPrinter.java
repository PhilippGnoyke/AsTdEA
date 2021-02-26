package org.astdea.io.output.printer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;
import org.astdea.data.versions.Version;
import org.astdea.io.IOUtils;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.output.OFN;
import org.astdea.io.output.ResultHeaders;
import org.astdea.io.output.printer.compappender.CdCompAppender;
import org.astdea.io.output.printer.compappender.LinEvoTypeCompAppender;
import org.astdea.io.output.printer.subprinters.*;
import org.astdea.logic.mapping.CdMappings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class MainPrinter
{
    private String intraOutDir;
    private String interOutDir;
    private String[] versionNames;
    private Project project;

    public MainPrinter(String generalOutDir, Project project, String[] versionNames)
    {
        this.versionNames = versionNames;
        this.intraOutDir = IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION);
        this.interOutDir = IOUtils.makeFilePath(generalOutDir, OFN.INTER_VERSION);
        this.project = project;
    }

    public void printAll() throws IOException, NullPointerException
    {
        IOUtils.makeDir(interOutDir);
        printProjectMetrics();
        printCds();
        printHds();
        printUds();
        updateVersionMetrics();
        printVersionNames();
    }

    public static void printCore(File file, String[] headers, PrinterCore printerCore)
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
        printCore(IOFN.FILE_PROJECT, ResultHeaders.projectMetricsHeaders, new ProjectMetricsPrinter(project));
    }

    public void printCds() throws IOException, NullPointerException
    {
        printCdsCore(Level.CLASS, IOFN.FILE_CLASS_CDS_PROPS, IOFN.FILE_CLASS_CDS_COMPS, OFN.FILE_CLASS_CDS_EDGES);
        printCdsCore(Level.PACK, IOFN.FILE_PACK_CDS_PROPS, IOFN.FILE_PACK_CDS_COMPS, OFN.FILE_PACK_CDS_EDGES);
    }

    private void printCdsCore(Level level, String fileProps, String fileComps, String fileEdges)
        throws IOException, NullPointerException
    {
        Set<InterVersionCd> inters = project.getCds(level);
        CdMappings mappings = project.getCdMappingsMap(level);
        printCore(fileProps, ResultHeaders.cdPropHeaders, new PropsPrinter<>(inters));
        printCore(fileComps, ResultHeaders.cdCompHeaders, new CompsPrinter<>(inters, new CdCompAppender()));
        printCore(fileEdges, ResultHeaders.cdEdgesHeaders, new CdEdgesPrinter(inters, mappings));
    }

    public void printHds() throws IOException, NullPointerException
    {
        printLinEvoTypesCore(project.getHds(), IOFN.FILE_HDS_PROPS, IOFN.FILE_HDS_COMPS);
    }

    public void printUds() throws IOException, NullPointerException
    {
        printLinEvoTypesCore(project.getUds(), IOFN.FILE_UDS_PROPS, IOFN.FILE_UDS_COMPS);
    }

    private <IntraType extends IntraVersionLinEvoType, InterType extends InterVersionLinEvoType<IntraType>> void
    printLinEvoTypesCore(Set<InterType> inters, String fileProps, String fileComps) throws IOException
    {
        printCore(fileProps, ResultHeaders.generalSmellPropHeaders, new PropsPrinter<>(inters));
        printCore(fileComps, ResultHeaders.linEvoTypeCompHeaders, new CompsPrinter<>
            (inters, new LinEvoTypeCompAppender<>()));
    }

    public void updateVersionMetrics() throws IOException
    {
        for (Version version : project.getVersions())
        {
            String path = IOUtils.makeFilePath(intraOutDir, String.valueOf(version.getVersionId()), IOFN.FILE_PROJECT);
            VersionMetricsPrinter printer = new VersionMetricsPrinter(version, path);
            printCore(IOUtils.makeFile(path), ResultHeaders.versionHeaders, printer);
        }
    }

    public void printVersionNames() throws IOException
    {
        File outfile = IOUtils.makeFile(IOUtils.makeFilePath(intraOutDir, IOFN.FILE_VERSION_NAMES));
        printCore(outfile, ResultHeaders.versionNamesHeaders, new VersionNamesPrinter(versionNames));
    }
}
