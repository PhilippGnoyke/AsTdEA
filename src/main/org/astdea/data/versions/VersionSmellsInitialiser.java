package org.astdea.data.versions;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.io.input.CsvReadingUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.inputoutput.IOUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class VersionSmellsInitialiser
{
    private String outDir;
    private int versionId;

    VersionSmellsInitialiser(String outDir, int versionId)
    {
        this.outDir = outDir;
        this.versionId = versionId;
    }

    Set<IntraVersionCd> initClassCds() throws IOException {return initCore(new ClassCdInitHelper());}

    Set<IntraVersionCd> initPackCds() throws IOException {return initCore(new PackCdInitHelper());}

    Set<IntraVersionHd> initHds() throws IOException {return initCore(new HdInitHelper());}

    Set<IntraVersionUd> initUds() throws IOException {return initCore(new UdInitHelper());}

    private <IntraType extends IntraVersionSmell> Set<IntraType> initCore(SmellTypeInitHelper<IntraType> helper) throws IOException
    {
        String fileComps = IOUtils.makeFilePath(outDir, helper.getCompsFile());
        String fileProps = IOUtils.makeFilePath(outDir, helper.getPropsFile());
        CSVParser recordsComps = CsvReadingUtils.initCsvParser(fileComps, helper.getCompsHeaders());
        CSVParser recordsProps = CsvReadingUtils.initCsvParser(fileProps, helper.getPropsHeaders());
        Iterator<CSVRecord> compsIter = recordsComps.iterator();
        Iterator<CSVRecord> propsIter = recordsProps.iterator();
        Set<IntraType> intras = new HashSet<>(); /* Assert that recordComps and recordProps have the same length*/
        while (compsIter.hasNext())
        {
            CSVRecord compRecord = compsIter.next();
            CSVRecord propRecord = propsIter.next();
            int smellId = Integer.parseInt(compRecord.get(IPN.ID));
            double pageRank = Double.parseDouble(propRecord.get(IPN.CENTRALITY));
            intras.add(helper.initIntra(compRecord, propRecord, smellId, pageRank));
        }
        recordsComps.close();
        recordsProps.close();
        return intras;
    }

    private interface SmellTypeInitHelper<IntraType extends IntraVersionSmell>
    {
        String getCompsFile();

        String getPropsFile();

        String[] getCompsHeaders();

        String[] getPropsHeaders();

        IntraType initIntra(CSVRecord compRecord, CSVRecord propRecord, int smellId, double pageRank);
    }

    private class ClassCdInitHelper implements SmellTypeInitHelper<IntraVersionCd>
    {
        @Override
        public String getCompsFile() {return IOFN.FILE_CLASS_CDS_COMPS;}

        @Override
        public String getPropsFile() {return IOFN.FILE_CLASS_CDS_PROPS;}

        @Override
        public String[] getCompsHeaders() {return AsTdEvolutionPrinter.getCdCompHeaders();}

        @Override
        public String[] getPropsHeaders() {return AsTdEvolutionPrinter.getClassCdPropHeaders();}

        @Override
        public IntraVersionCd initIntra(CSVRecord compRecord, CSVRecord propRecord, int smellId, double pageRank) {return intCdCore(compRecord, propRecord, smellId, pageRank);}
    }

    private class PackCdInitHelper implements SmellTypeInitHelper<IntraVersionCd>
    {
        @Override
        public String getCompsFile() {return IOFN.FILE_PACK_CDS_COMPS;}

        @Override
        public String getPropsFile() {return IOFN.FILE_PACK_CDS_PROPS;}

        @Override
        public String[] getCompsHeaders() {return AsTdEvolutionPrinter.getCdCompHeaders();}

        @Override
        public String[] getPropsHeaders() {return AsTdEvolutionPrinter.getPackCdPropHeaders();}

        @Override
        public IntraVersionCd initIntra(CSVRecord compRecord, CSVRecord propRecord, int smellId, double pageRank) {return intCdCore(compRecord, propRecord, smellId, pageRank);}
    }

    private IntraVersionCd intCdCore(CSVRecord compRecord, CSVRecord propRecord, int smellId, double pageRank)
    {
        int order = Integer.parseInt(propRecord.get(IPN.ORDER));
        String compsString = compRecord.get(IPN.AFFECTED_COMPS);
        Set<String> comps = IOUtils.parseStringToSet(compsString, IOUtils.DELIMITER);
        return new IntraVersionCd(smellId, versionId, pageRank, comps, order);
    }

    private class HdInitHelper implements SmellTypeInitHelper<IntraVersionHd>
    {
        @Override
        public String getCompsFile() {return IOFN.FILE_HDS_COMPS;}

        @Override
        public String getPropsFile() {return IOFN.FILE_HDS_PROPS;}

        @Override
        public String[] getCompsHeaders() {return AsTdEvolutionPrinter.getHdCompHeaders();}

        @Override
        public String[] getPropsHeaders() {return AsTdEvolutionPrinter.getHdPropHeaders();}

        @Override
        public IntraVersionHd initIntra(CSVRecord compRecord, CSVRecord propRecord, int smellId, double pageRank)
        {
            String mainComp = compRecord.get(IPN.MAIN_COMP);
            Set<String> affComps = IOUtils.parseStringToSet(compRecord.get(IPN.AFF_COMPS), IOUtils.DELIMITER);
            Set<String> effComps = IOUtils.parseStringToSet(compRecord.get(IPN.EFF_COMPS), IOUtils.DELIMITER);
            return new IntraVersionHd(smellId, versionId, pageRank, mainComp, affComps, effComps);
        }
    }

    private class UdInitHelper implements SmellTypeInitHelper<IntraVersionUd>
    {
        @Override
        public String getCompsFile() {return IOFN.FILE_UDS_COMPS;}

        @Override
        public String getPropsFile() {return IOFN.FILE_UDS_PROPS;}

        @Override
        public String[] getCompsHeaders() {return AsTdEvolutionPrinter.getUdCompHeaders();}

        @Override
        public String[] getPropsHeaders() {return AsTdEvolutionPrinter.getUdPropHeaders();}

        @Override
        public IntraVersionUd initIntra(CSVRecord compRecord, CSVRecord propRecord, int smellId, double pageRank)
        {
            String mainComp = compRecord.get(IPN.MAIN_COMP);
            Set<String> lessStablePacks = IOUtils.parseStringToSet(compRecord.get(IPN.LESS_STABLE_PACKS), IOUtils.DELIMITER);
            return new IntraVersionUd(smellId, versionId, pageRank, mainComp, lessStablePacks);
        }
    }
}
