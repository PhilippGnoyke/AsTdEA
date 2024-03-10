package org.astdea.data.versions.initialising;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.intraversionsmells.*;
import org.astdea.io.IOUtils;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IPN;

import java.io.IOException;
import java.util.*;

public class VersionSmellsInitialiser
{
    private String outDir;
    private int versionId;

    public VersionSmellsInitialiser(String outDir, int versionId)
    {
        this.outDir = outDir;
        this.versionId = versionId;
    }

    public Map<IntraId,IntraVersionCd> initClassCds() throws IOException {return initCore(new CdInitHelper(Level.CLASS));}

    public Map<IntraId,IntraVersionCd> initPackCds() throws IOException {return initCore(new CdInitHelper(Level.PACK));}

    public Map<IntraId,IntraVersionHd> initHds() throws IOException {return initCore(new HdInitHelper());}

    public Map<IntraId,IntraVersionUd> initUds() throws IOException {return initCore(new UdInitHelper());}

    private <IntraType extends IntraVersionSmell> Map<IntraId,IntraType> initCore
        (SmellTypeInitHelper<IntraType> helper) throws IOException
    {
        String fileComps = IOUtils.makeFilePath(outDir, helper.getCompsFile());
        String fileProps = IOUtils.makeFilePath(outDir, helper.getPropsFile());
        CSVParser recordsComps = CsvReadingUtils.initCsvParser(fileComps, helper.getCompsHeaders());
        CSVParser recordsProps = CsvReadingUtils.initCsvParser(fileProps, helper.getPropsHeaders());
        Iterator<CSVRecord> compsIter = recordsComps.iterator();
        Iterator<CSVRecord> propsIter = recordsProps.iterator();
        Map<IntraId,IntraType> intras = new HashMap<>();
        while (compsIter.hasNext())
        {
            CSVRecord compRecord = compsIter.next();
            CSVRecord propRecord = propsIter.next();
            int smellId = Integer.parseInt(compRecord.get(IPN.ID));
            IntraId intraId = new IntraId(versionId,smellId);
            int order =  Integer.parseInt(propRecord.get(IPN.ORDER));
            int size =  Integer.parseInt(propRecord.get(IPN.SIZE));
            double pageRank = Double.parseDouble(propRecord.get(IPN.CENTRALITY));
            intras.put(intraId,helper.initIntra(compRecord, propRecord, versionId, smellId, pageRank, order, size));
        }
        recordsComps.close();
        recordsProps.close();
        return intras;
    }
}
