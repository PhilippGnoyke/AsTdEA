package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.inputoutput.IOUtils;

import java.util.Set;

class HdInitHelper implements SmellTypeInitHelper<IntraVersionHd>
{
    @Override
    public String getCompsFile() {return IOFN.FILE_HDS_COMPS;}

    @Override
    public String getPropsFile() {return IOFN.FILE_HDS_PROPS;}

    @Override
    public String[] getCompsHeaders() {return AsTdEvolutionPrinter.hdCompHeaders;}

    @Override
    public String[] getPropsHeaders() {return AsTdEvolutionPrinter.hdPropHeaders;}

    @Override
    public IntraVersionHd initIntra
        (CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank)
    {
        String mainComp = compRecord.get(IPN.MAIN_COMP);
        Set<String> affComps = IOUtils.parseStringToSet(compRecord.get(IPN.AFF_COMPS), IOUtils.DELIMITER);
        Set<String> effComps = IOUtils.parseStringToSet(compRecord.get(IPN.EFF_COMPS), IOUtils.DELIMITER);
        return new IntraVersionHd(smellId, versionId, pageRank, mainComp, affComps, effComps);
    }
}
