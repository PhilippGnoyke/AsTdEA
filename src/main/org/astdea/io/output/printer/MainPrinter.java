package org.astdea.io.output.printer;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import it.unimib.disco.essere.main.ETLE;
import it.unimib.disco.essere.main.ExTimeLogger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.astdea.data.Project;
import org.astdea.data.graphelements.MergeSplitType;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.versions.Version;
import org.astdea.io.IOUtils;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.output.OFN;
import org.astdea.io.output.OPN;
import org.astdea.io.output.ResultHeaders;
import org.astdea.io.output.printer.compappender.CdCompAppender;
import org.astdea.io.output.printer.compappender.LinEvoTypeCompAppender;
import org.astdea.io.output.printer.subprinters.*;
import org.astdea.logic.mapping.CdMappings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class MainPrinter
{
    private String intraOutDir;
    private String interOutDir;
    private String classCdMergeOutDir;
    private String packCdMergeOutDir;
    private String classCdSplitOutDir;
    private String packCdSplitOutDir;
    private String classCdTransitionOutDir;
    private String packCdTransitionOutDir;

    private String[] versionNames;
    private Project project;
    private ExTimeLogger exTimeLogger;

    public MainPrinter(String generalOutDir, Project project, String[] versionNames,ExTimeLogger exTimeLogger)
    {
        this.versionNames = versionNames;
        this.intraOutDir = IOUtils.makeFilePath(generalOutDir, OFN.INTRA_VERSION);
        this.interOutDir = IOUtils.makeFilePath(generalOutDir, OFN.INTER_VERSION);
        this.classCdMergeOutDir = IOUtils.makeFilePath(interOutDir, OFN.FOLDER_CLASS_CDS_MERGES);
        this.packCdMergeOutDir = IOUtils.makeFilePath(interOutDir, OFN.FOLDER_PACK_CDS_MERGES);
        this.classCdSplitOutDir = IOUtils.makeFilePath(interOutDir, OFN.FOLDER_CLASS_CDS_SPLITS);
        this.packCdSplitOutDir = IOUtils.makeFilePath(interOutDir, OFN.FOLDER_PACK_CDS_SPLITS);
        this.classCdTransitionOutDir = IOUtils.makeFilePath(interOutDir, OFN.FOLDER_CLASS_CDS_TRANSITIONS);
        this.packCdTransitionOutDir = IOUtils.makeFilePath(interOutDir, OFN.FOLDER_PACK_CDS_TRANSITIONS);
        this.exTimeLogger = exTimeLogger;

        IOUtils.makeFilePath(interOutDir, OFN.INTRA_VERSION);
        IOUtils.makeDir(interOutDir);
        IOUtils.makeDir(classCdMergeOutDir);
        IOUtils.makeDir(packCdMergeOutDir);
        IOUtils.makeDir(classCdSplitOutDir);
        IOUtils.makeDir(packCdSplitOutDir);
        IOUtils.makeDir(classCdTransitionOutDir);
        IOUtils.makeDir(packCdTransitionOutDir);

        this.project = project;
    }

    public void printAll() throws IOException, NullPointerException
    {
        IOUtils.makeDir(interOutDir);
        printProjectMetrics();
        printCds();
        printHds();
        printUds();
        updateIntraVersionProps();
        printVersionNames();
        exTimeLogger.logEventEnd(ETLE.Event.ASTDEA_PRINTING);
        printExTimeLogs();
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

    private void printCore(String dir, String fileName, String[] headers, PrinterCore printerCore)
        throws IOException, NullPointerException
    {
        File fileCsv = IOUtils.makeFile(dir, fileName);
        printCore(fileCsv, headers, printerCore);
    }

    private void printCore(String fileName, String[] headers, PrinterCore printerCore)
        throws IOException, NullPointerException
    {
        printCore(interOutDir, fileName, headers, printerCore);
    }

    public void printProjectMetrics() throws IOException, NullPointerException
    {
        printCore(IOFN.FILE_PROJECT, ResultHeaders.projectMetricsHeaders, new ProjectMetricsPrinter(project));
    }

    public void printExTimeLogs() throws IOException, NullPointerException
    {
        printCore(IOFN.FILE_EX_TIME_LOGS, ResultHeaders.exTimeLogsHeaders, new ExTimeLogsPrinter(exTimeLogger));
    }

    public void printCds() throws IOException, NullPointerException
    {
        printCdsCore(Level.CLASS);
        printCdsCore(Level.PACK);
    }

    private void printCdsCore(Level level)
        throws IOException, NullPointerException
    {
        String fileProps, fileComps, fileEdges, folderMerges, folderSplits, folderTransitions;
        switch (level)
        {
            case CLASS -> {
                fileProps = IOFN.FILE_CLASS_CDS_PROPS;
                fileComps = IOFN.FILE_CLASS_CDS_COMPS;
                fileEdges = OFN.FILE_CLASS_CDS_EDGES;
                folderMerges = classCdMergeOutDir;
                folderSplits = classCdSplitOutDir;
                folderTransitions = classCdTransitionOutDir;
            }
            case PACK -> {
                fileProps = IOFN.FILE_PACK_CDS_PROPS;
                fileComps = IOFN.FILE_PACK_CDS_COMPS;
                fileEdges = OFN.FILE_PACK_CDS_EDGES;
                folderMerges = packCdMergeOutDir;
                folderSplits = packCdSplitOutDir;
                folderTransitions = packCdTransitionOutDir;
            }
            default -> throw new IllegalStateException();
        }
        Set<InterVersionCd> inters = project.getCds(level);
        CdMappings mappings = project.getCdMappingsMap(level);
        printCore(fileProps, ResultHeaders.cdPropHeaders, new PropsPrinter<>(inters));
        printCore(fileComps, ResultHeaders.cdCompHeaders, new CompsPrinter<>(inters, new CdCompAppender()));
        printCore(fileEdges, ResultHeaders.cdEdgesHeaders, new CdEdgesPrinter(inters, mappings));
        printMergesSplitsTransitions(folderMerges, folderSplits, folderTransitions, inters);
    }

    private void printMergesSplitsTransitions(String folderMerges, String folderSplits, String folderTransitions, Set<InterVersionCd> inters) throws IOException
    {
        for (InterVersionCd inter : inters)
        {
            if (inter.getMerges().size() > 0)
            {
                printCore(folderMerges, inter.get(OPN.ID).toString() + OFN.CSV,
                    ResultHeaders.cdMergeHeaders, new CdMergesSplitsPrinter(inter, MergeSplitType.MERGE));
            }
            if (inter.getSplits().size() > 0)
            {
                printCore(folderSplits, inter.get(OPN.ID).toString() + OFN.CSV,
                    ResultHeaders.cdSplitHeaders, new CdMergesSplitsPrinter(inter, MergeSplitType.SPLIT));
            }
            if (inter.getTransitions().size() > 0)
            {
                printCore(folderTransitions, inter.get(OPN.ID).toString() + OFN.CSV,
                    ResultHeaders.cdTransitionHeaders, new CdTransitionsPrinter(inter));
            }
        }
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

    public void updateIntraVersionProps() throws IOException
    {
        for (Version version : project.getVersions())
        {
            updateVersionMetrics(version);
            updateIntraVersionClassCdProps(version);
            updateIntraVersionPackCdProps(version);
            updateIntraVersionHdProps(version);
            updateIntraVersionUdProps(version);
        }
    }

    private void updateVersionMetrics(Version version) throws IOException
    {
        String path = IOUtils.makeFilePath(intraOutDir, String.valueOf(version.getVersionId()), IOFN.FILE_PROJECT);
        VersionMetricsPrinter printer = new VersionMetricsPrinter(version, path);
        printCore(IOUtils.makeFile(path), ResultHeaders.versionHeaders, printer);
    }

    private <IntraType extends IntraVersionSmell> void updateIntraVersionSmellProps
        (Version version, String file, String[] headersPt1, String[] headersAll,
         Map<IntraId, IntraType> intras, boolean isCd) throws IOException
    {
        String path = IOUtils.makeFilePath(intraOutDir, String.valueOf(version.getVersionId()), file);
        IntraVersionPropsPrinter<IntraType> printer = new IntraVersionPropsPrinter<>(version, path, headersPt1,
            intras, headersAll.length - headersPt1.length,isCd);
        printCore(IOUtils.makeFile(path), headersAll, printer);
    }


    private void updateIntraVersionClassCdProps(Version version) throws IOException
    {
        updateIntraVersionSmellProps(version,
            IOFN.FILE_CLASS_CDS_PROPS,
            AsTdEvolutionPrinter.classCdPropHeaders,
            ResultHeaders.intraVersionClassCdPropHeaders,
            version.getClassCds(),
            true);
    }

    private void updateIntraVersionPackCdProps(Version version) throws IOException
    {
        updateIntraVersionSmellProps(version,
            IOFN.FILE_PACK_CDS_PROPS,
            AsTdEvolutionPrinter.packCdPropHeaders,
            ResultHeaders.intraVersionPackCdPropHeaders,
            version.getPackCds(),
            true);
    }

    private void updateIntraVersionHdProps(Version version) throws IOException
    {
        updateIntraVersionSmellProps(version,
            IOFN.FILE_HDS_PROPS,
            AsTdEvolutionPrinter.hdPropHeaders,
            ResultHeaders.intraVersionHdPropHeaders,
            version.getHds(),
            false);
    }

    private void updateIntraVersionUdProps(Version version) throws IOException
    {
        updateIntraVersionSmellProps(version,
            IOFN.FILE_UDS_PROPS,
            AsTdEvolutionPrinter.udPropHeaders,
            ResultHeaders.intraVersionUdPropHeaders,
            version.getUds(),
            false);
    }

    public void printVersionNames() throws IOException
    {
        File outfile = IOUtils.makeFile(IOUtils.makeFilePath(intraOutDir, IOFN.FILE_VERSION_NAMES_CSV));
        printCore(outfile, ResultHeaders.versionNamesHeaders, new VersionNamesPrinter(versionNames));
    }

}
