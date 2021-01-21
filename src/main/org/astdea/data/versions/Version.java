package org.astdea.data.versions;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.data.versions.initialising.VersionSmellsInitialiser;
import org.astdea.io.IOUtils;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.ArcanRunner;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.output.OPN;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

public class Version
{
    private String outDir;
    private int versionId;
    private Set<IntraVersionCd> classCds;
    private Set<IntraVersionCd> packCds;
    private Set<IntraVersionHd> hds;
    private Set<IntraVersionUd> uds;
    private int loc;
    private int classCount;
    private int packCount;
    private LocalDate versionTime;
    private int versionTimeSpan;
    private DeltaSmellsInVersion deltaSmellsInVersion;

    public Version(int versionId, String generalOutDir, LocalDate versionTime, int versionTimeSpanInDays)
    {
        this.versionId = versionId;
        this.outDir = ArcanRunner.getArcanOutFolder(generalOutDir, versionId);
        this.versionTime = versionTime;
        this.versionTimeSpan = versionTimeSpanInDays;
    }

    public Version(int versionId, String generalOutDir, LocalDate versionTime, int versionTimeSpanInDays,
                   Set<IntraVersionCd> classCds, Set<IntraVersionCd> packCds,
                   Set<IntraVersionHd> hds, Set<IntraVersionUd> uds)
    {
        this(versionId, generalOutDir, versionTime, versionTimeSpanInDays);
        this.classCds = classCds;
        this.packCds = packCds;
        this.hds = hds;
        this.uds = uds;
    }

    public Version init() throws IOException
    {
        initSmells();
        initVersionProps();
        return this;
    }

    public int getVersionId() {return versionId;}

    public Set<IntraVersionCd> getClassCds() {return classCds;}

    public Set<IntraVersionCd> getPackCds() {return packCds;}

    public Set<IntraVersionHd> getHds() {return hds;}

    public Set<IntraVersionUd> getUds() {return uds;}

    public Set<IntraVersionCd> getCds(Level level) {return level == Level.CLASS ? getClassCds() : getPackCds();}

    public void setDeltaSmellsInVersion(DeltaSmellsInVersion deltaSmellManager)
    {
        this.deltaSmellsInVersion = deltaSmellManager;
        deltaSmellsInVersion.setCountsOfCurrVersion
            (loc, classCount, packCount, classCds.size(), packCds.size(), hds.size(), uds.size());
    }

    public void setDeltaSmellsAsPrevVersion(DeltaSmellsInVersion deltaSmellManager)
    {
        deltaSmellManager.setCountsOfPrevVersion(classCds.size(), packCds.size(), hds.size(), uds.size());
    }

    private void initSmells() throws IOException
    {
        VersionSmellsInitialiser initialiser = new VersionSmellsInitialiser(outDir, versionId);
        classCds = initialiser.initClassCds();
        packCds = initialiser.initPackCds();
        hds = initialiser.initHds();
        uds = initialiser.initUds();
    }

    private void initVersionProps() throws IOException
    {
        String[] headers = AsTdEvolutionPrinter.projectMetricsHeaders;
        String projectFile = IOUtils.makeFilePath(outDir, IOFN.FILE_PROJECT);
        CSVParser records = CsvReadingUtils.initCsvParser(projectFile, headers);
        CSVRecord record = records.getRecords().get(0);
        loc = Integer.parseInt(record.get(IPN.LOC));
        classCount = Integer.parseInt(record.get(IPN.CLASS_COUNT));
        packCount = Integer.parseInt(record.get(IPN.PACK_COUNT));
        records.close();
    }

    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.VERSION_TIME -> versionTime;
                case OPN.VERSION_TIME_SPAN -> versionTimeSpan;
                default -> deltaSmellsInVersion.get(fieldName);
            };
    }
}
