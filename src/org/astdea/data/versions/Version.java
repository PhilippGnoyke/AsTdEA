package org.astdea.data.versions;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.CompChangeAffectedCounter;
import org.astdea.data.CompChanges;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.intraversionsmells.*;
import org.astdea.data.AffType;
import org.astdea.data.versions.initialising.SharedClassesParser;
import org.astdea.data.versions.initialising.VersionCompManager;
import org.astdea.data.versions.initialising.VersionSmellsInitialiser;
import org.astdea.io.IOUtils;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.ArcanRunner;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.output.OPN;
import org.astdea.logic.tracker.versionpairtracker.TransitionCounter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Version {

    private String outDir;
    private int versionId;
    private String versionName;
    private Map<IntraId, IntraVersionCd> classCds;
    private Map<IntraId, IntraVersionCd> packCds;
    private Map<IntraId, IntraVersionHd> hds;
    private Map<IntraId, IntraVersionUd> uds;
    private int loc;
    private int classCount;
    private int packCount;
    private LocalDate versionTime;
    private int versionTimeSpan;
    private DeltaSmellsInVersion deltaSmellsInVersion;
    private final Map<AffType, CompChangeAffectedCounter> outgoingCompChangeAffectedCounters;
    private final Map<AffType, CompChangeAffectedCounter> incomingCompChangeAffectedCounters;

    private final VersionCompManager versionCompManager;
    private final Set<String> sharedClasses;
    private final Set<String> sharedPacks;
    private final Map<AffType, TransitionCounter> transitionCounters;

    public Version(int versionId, String versionName, String generalOutDir, LocalDate versionTime, int versionTimeSpanInDays) {
        this.versionId = versionId;
        this.versionName = versionName;
        this.outDir = ArcanRunner.getArcanOutFolder(generalOutDir, versionId);
        this.versionTime = versionTime;
        this.versionTimeSpan = versionTimeSpanInDays;
        this.versionCompManager = new VersionCompManager();
        this.sharedClasses = new HashSet<>();
        this.sharedPacks = new HashSet<>();
        this.outgoingCompChangeAffectedCounters = new HashMap<>();
        this.incomingCompChangeAffectedCounters = new HashMap<>();
        this.transitionCounters = new HashMap<>();
        for (AffType affType : AffType.values()) {
            outgoingCompChangeAffectedCounters.put(affType, new CompChangeAffectedCounter(affType, true));
            incomingCompChangeAffectedCounters.put(affType, new CompChangeAffectedCounter(affType, false));
        }
    }

    public Version init(CompChanges compChanges) throws IOException {
        initSmells();
        initVersionProps();
        if(compChanges!=null)
        {
            versionCompManager.addCompChanges(compChanges, versionName);
        }
        String[] fqcns = SharedClassesParser.parseSharedClasses(outDir);
        for (String fqcn : fqcns) {
            if (fqcn != null) {
                sharedClasses.add(fqcn);
                sharedPacks.add(fqcn.substring(0, fqcn.lastIndexOf(".")));
            }
        }
        return this;
    }

    public int getVersionId() {return versionId;}

    public Map<IntraId, IntraVersionCd> getClassCds() {return classCds;}

    public Map<IntraId, IntraVersionCd> getPackCds() {return packCds;}

    public Map<IntraId, IntraVersionHd> getHds() {return hds;}

    public Map<IntraId, IntraVersionUd> getUds() {return uds;}

    public Map<IntraId, IntraVersionCd> getCds(Level level) {return level == Level.CLASS ? getClassCds() : getPackCds();}

    public void setDeltaSmellsInVersion(DeltaSmellsInVersion deltaSmellManager) {
        this.deltaSmellsInVersion = deltaSmellManager;
        deltaSmellsInVersion.setCountsOfCurrVersion
            (loc, classCount, packCount, classCds.size(), packCds.size(), hds.size(), uds.size());
    }

    public void setDeltaSmellsAsPrevVersion(DeltaSmellsInVersion deltaSmellManager) {
        deltaSmellManager.setCountsOfPrevVersion(classCds.size(), packCds.size(), hds.size(), uds.size());
    }

    private void initSmells() throws IOException {
        VersionSmellsInitialiser initialiser = new VersionSmellsInitialiser(outDir, versionId, versionCompManager);
        classCds = initialiser.initClassCds();
        packCds = initialiser.initPackCds();
        hds = initialiser.initHds();
        uds = initialiser.initUds();
    }

    private void initVersionProps() throws IOException {
        String[] headers = AsTdEvolutionPrinter.projectMetricsHeaders;
        String projectFile = IOUtils.makeFilePath(outDir, IOFN.FILE_PROJECT);
        CSVParser records = CsvReadingUtils.initCsvParser(projectFile, headers);
        CSVRecord record = records.getRecords().get(0);
        loc = Integer.parseInt(record.get(IPN.LOC));
        classCount = Integer.parseInt(record.get(IPN.CLASS_COUNT));
        packCount = Integer.parseInt(record.get(IPN.PACK_COUNT));
        records.close();
    }

    public Object get(String fieldName) {
        return switch (fieldName) {
            case OPN.VERSION_TIME -> versionTime;
            case OPN.VERSION_TIME_SPAN -> versionTimeSpan;
            default -> deltaSmellsInVersion.get(fieldName);
        };
    }

    public Map<AffType, CompChangeAffectedCounter> getOutgoingCompChangeAffectedCounters() {
        return outgoingCompChangeAffectedCounters;
    }

    public Map<AffType, CompChangeAffectedCounter> getIncomingCompChangeAffectedCounters() {
        return incomingCompChangeAffectedCounters;
    }

    public Map<AffType, TransitionCounter> getTransitionCounters() {
        return transitionCounters;
    }

    public void addTransitionCounter(AffType affType, TransitionCounter counter) {
        transitionCounters.put(affType, counter);
    }

    public VersionCompManager getVersionCompManager() {
        return versionCompManager;
    }

    public Set<String> getSharedComps(Level level) {
        return level == Level.CLASS ? sharedClasses : sharedPacks;
    }
}
