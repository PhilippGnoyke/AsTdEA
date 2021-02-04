package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.io.IOUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;

import java.util.Set;

class UdInitHelper implements SmellTypeInitHelper<IntraVersionUd>
{
    @Override
    public String getCompsFile() {return IOFN.FILE_UDS_COMPS;}

    @Override
    public String getPropsFile() {return IOFN.FILE_UDS_PROPS;}

    @Override
    public String[] getCompsHeaders() {return AsTdEvolutionPrinter.udCompHeaders;}

    @Override
    public String[] getPropsHeaders() {return AsTdEvolutionPrinter.udPropHeaders;}

    @Override
    public IntraVersionUd initIntra
        (CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank)
    {
        String mainComp = compRecord.get(IPN.MAIN_COMP);
        Set<String> lessStablePacks =
            IOUtils.parseStringToSet(compRecord.get(IPN.LESS_STABLE_PACKS), IOUtils.DELIMITER);
        return new IntraVersionUd(smellId, versionId, pageRank, mainComp, lessStablePacks);
    }
}
