package org.astdea.data.versions.initialising;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.IOUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class VersionSmellsInitialiser
{
    private String outDir;
    private int versionId;

    public VersionSmellsInitialiser(String outDir, int versionId)
    {
        this.outDir = outDir;
        this.versionId = versionId;
    }

    public Set<IntraVersionCd> initClassCds() throws IOException {return initCore(new CdInitHelper(Level.CLASS));}

    public Set<IntraVersionCd> initPackCds() throws IOException {return initCore(new CdInitHelper(Level.PACK));}

    public Set<IntraVersionHd> initHds() throws IOException {return initCore(new HdInitHelper());}

    public Set<IntraVersionUd> initUds() throws IOException {return initCore(new UdInitHelper());}

    private <IntraType extends IntraVersionSmell> Set<IntraType> initCore
        (SmellTypeInitHelper<IntraType> helper) throws IOException
    {
        String fileComps = IOUtils.makeFilePath(outDir, helper.getCompsFile());
        String fileProps = IOUtils.makeFilePath(outDir, helper.getPropsFile());
        CSVParser recordsComps = CsvReadingUtils.initCsvParser(fileComps, helper.getCompsHeaders());
        CSVParser recordsProps = CsvReadingUtils.initCsvParser(fileProps, helper.getPropsHeaders());
        Iterator<CSVRecord> compsIter = recordsComps.iterator();
        Iterator<CSVRecord> propsIter = recordsProps.iterator();
        Set<IntraType> intras = new HashSet<>();
        while (compsIter.hasNext())
        {
            CSVRecord compRecord = compsIter.next();
            CSVRecord propRecord = propsIter.next();
            int smellId = Integer.parseInt(compRecord.get(IPN.ID));
            double pageRank = Double.parseDouble(propRecord.get(IPN.CENTRALITY));
            intras.add(helper.initIntra(compRecord, propRecord, versionId, smellId, pageRank));
        }
        recordsComps.close();
        recordsProps.close();
        return intras;
    }
}
