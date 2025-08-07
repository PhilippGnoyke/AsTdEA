package org.astdea.data;

import it.unimib.disco.essere.main.ETLE.*;
import it.unimib.disco.essere.main.ExTimeLogger;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.*;
import org.astdea.data.versions.DeltaSmellsInVersion;
import org.astdea.data.versions.Version;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.VersionsReader;
import org.astdea.io.output.OPN;
import org.astdea.io.output.printer.subprinters.ExTimeLogsPrinter;
import org.astdea.logic.deltacalc.CdDeltaCalculator;
import org.astdea.logic.deltacalc.Deltas;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.logic.tracker.projecttracker.CdProjectTracker;
import org.astdea.logic.tracker.projecttracker.HdProjectTracker;
import org.astdea.logic.tracker.projecttracker.UdProjectTracker;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Project
{
    private int analysedVersions;
    private int analysedTimeSpanInDays;

    private int numOfIntraVersionSmells;
    private int numOfIntraVersionClassCDs;
    private int numOfIntraVersionPackCDs;
    private int numOfIntraVersionHDs;
    private int numOfIntraVersionUDs;

    private int numOfInterVersionSmells;
    private int numOfInterVersionClassCDs;
    private int numOfInterVersionPackCDs;
    private int numOfInterVersionHDs;
    private int numOfInterVersionUDs;

    private String projectInDir;
    private String outDir;
    private List<Version> versions;

    private Set<InterVersionCd> classCds;
    private Set<InterVersionCd> packCds;
    private Set<InterVersionHd> hds;
    private Set<InterVersionUd> uds;

    private CdMappings cdClassMappings;
    private CdMappings cdPackMappings;

    private final boolean newStructure;
    private final TimeManager timeManager;
    private ExTimeLogger exTimeLogger;

    public Project(String projectInDir, String outDir, int analysedVersions, ExTimeLogger exTimeLogger) throws IOException
    {
        this.projectInDir = projectInDir;
        this.outDir = outDir;
        this.analysedVersions = analysedVersions;
        newStructure = VersionsReader.isNewStructure(projectInDir);
        timeManager = initTimeManager();
        this.exTimeLogger = exTimeLogger;
    }

    public Project build() throws IOException
    {
        if (analysedVersions > 0)
        {
            initVersions();
            calcNumsOfIntraSmells();
            initInterSmells();
            retrieveInterSmells();
            calcNumsOfInterSmells();
            calcSmellDeltas();
        }
        return this;
    }

    public List<Version> getVersions() {return versions;}

    public Set<InterVersionHd> getHds() {return hds;}

    public Set<InterVersionUd> getUds() {return uds;}

    public Set<InterVersionCd> getCds(Level level) {return level == Level.CLASS ? classCds : packCds;}

    public CdMappings getCdMappingsMap(Level level) {return level == Level.CLASS ? cdClassMappings : cdPackMappings;}

    private void initVersions() throws IOException
    {
        exTimeLogger.logEventStart(Event.INIT_VERSIONS);
        versions = new ArrayList<>();
        for (int i = 0; i < analysedVersions; i++)
        {
            LocalDate versionTime = timeManager.getVersionTime(i);
            int versionTimeSpan = timeManager.getTimeSpan(i);
            versions.add(new Version(i, outDir, versionTime, versionTimeSpan).init());
        }
        analysedTimeSpanInDays = timeManager.getAnalysedTimeSpanInDays();
        exTimeLogger.logEventEnd(Event.INIT_VERSIONS);
    }

    private TimeManager initTimeManager() throws IOException
    {
        String timeDirectory = newStructure ? CsvReadingUtils.getStatsFolder(projectInDir) : projectInDir;
        return new TimeManager(timeDirectory, analysedVersions, newStructure);
    }

    private void calcNumsOfIntraSmells()
    {
        exTimeLogger.logEventStart(Event.PROCESS_INTRA_SMELLS);
        for (Version version : versions)
        {
            numOfIntraVersionClassCDs += version.getClassCds().size();
            numOfIntraVersionPackCDs += version.getPackCds().size();
            numOfIntraVersionHDs += version.getHds().size();
            numOfIntraVersionUDs += version.getUds().size();
        }
        numOfIntraVersionSmells = numOfIntraVersionClassCDs + numOfIntraVersionPackCDs +
            numOfIntraVersionHDs + numOfIntraVersionUDs;
        exTimeLogger.logEventEnd(Event.PROCESS_INTRA_SMELLS);
    }

    private void initInterSmells()
    {
        exTimeLogger.logEventStart(Event.PROCESS_INTER_SMELLS);
        classCds = new HashSet<>();
        packCds = new HashSet<>();
        hds = new HashSet<>();
        uds = new HashSet<>();
    }

    private void retrieveInterSmells()
    {
        uds = new UdProjectTracker(timeManager).track(versions);
        hds = new HdProjectTracker(timeManager).track(versions);
        CdProjectTracker cdClassTracker = new CdProjectTracker(timeManager, Level.CLASS, numOfIntraVersionClassCDs);
        CdProjectTracker cdPackTracker = new CdProjectTracker(timeManager, Level.PACK, numOfIntraVersionPackCDs);
        classCds = cdClassTracker.track(versions);
        packCds = cdPackTracker.track(versions);
        cdClassMappings = cdClassTracker.getMappings();
        cdPackMappings = cdPackTracker.getMappings();
    }

    private void calcNumsOfInterSmells()
    {
        numOfInterVersionClassCDs = classCds.size();
        numOfInterVersionPackCDs = packCds.size();
        numOfInterVersionHDs = hds.size();
        numOfInterVersionUDs = uds.size();

        numOfInterVersionSmells = numOfInterVersionClassCDs + numOfInterVersionPackCDs +
            numOfInterVersionHDs + numOfInterVersionUDs;
        exTimeLogger.logEventEnd(Event.PROCESS_INTER_SMELLS);
    }

    private void calcSmellDeltas()
    {
        exTimeLogger.logEventStart(Event.CALC_SMELL_DELTAS);
        Deltas deltasClassCd = new CdDeltaCalculator(cdClassMappings, classCds, analysedVersions).calc();
        Deltas deltasPackCd = new CdDeltaCalculator(cdPackMappings, packCds, analysedVersions).calc();
        Deltas deltasHd = calcLinEvoTypeDeltas(hds);
        Deltas deltasUd = calcLinEvoTypeDeltas(uds);
        for (int versionId = 0; versionId < analysedVersions; versionId++)
        {
            DeltaSmellsInVersion deltaSmells = new DeltaSmellsInVersion
                (versionId, deltasClassCd, deltasPackCd, deltasHd, deltasUd);
            versions.get(versionId).setDeltaSmellsInVersion(deltaSmells);
            if (versionId > 0) {versions.get(versionId - 1).setDeltaSmellsAsPrevVersion(deltaSmells);}
        }
        exTimeLogger.logEventEnd(Event.CALC_SMELL_DELTAS);
    }

    private <InterType extends InterVersionLinEvoType> Deltas calcLinEvoTypeDeltas(Set<InterType> inters)
    {
        Deltas deltas = new Deltas(analysedVersions);
        for (InterType inter : inters)
        {
            deltas.incrIntros(inter.getVersionOfIntroduction());
            if (inter.getVersionOfRemoval() < analysedVersions)
            {
                deltas.incrRemovs(inter.getVersionOfRemoval());
            }
        }
        return deltas;
    }

    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.ANALYSED_VERSIONS -> analysedVersions;
                case OPN.ANALYSED_TIME_SPAN -> analysedTimeSpanInDays;
                case OPN.NUM_OF_INTRA_VERSION_SMELLS -> numOfIntraVersionSmells;
                case OPN.NUM_OF_INTRA_VERSION_CLASS_CDS -> numOfIntraVersionClassCDs;
                case OPN.NUM_OF_INTRA_VERSION_PACK_CDS -> numOfIntraVersionPackCDs;
                case OPN.NUM_OF_INTRA_VERSION_HDS -> numOfIntraVersionHDs;
                case OPN.NUM_OF_INTRA_VERSION_UDS -> numOfIntraVersionUDs;
                case OPN.NUM_OF_INTER_VERSION_SMELLS -> numOfInterVersionSmells;
                case OPN.NUM_OF_INTER_VERSION_CLASS_CDS -> numOfInterVersionClassCDs;
                case OPN.NUM_OF_INTER_VERSION_PACK_CDS -> numOfInterVersionPackCDs;
                case OPN.NUM_OF_INTER_VERSION_HDS -> numOfInterVersionHDs;
                case OPN.NUM_OF_INTER_VERSION_UDS -> numOfInterVersionUDs;
                default -> null;
            };
    }
}
