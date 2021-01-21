package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;
import org.astdea.io.IOUtils;

import java.util.Set;

class CdInitHelper implements SmellTypeInitHelper<IntraVersionCd>
{
    private Level level;

    public CdInitHelper(Level level) {this.level = level;}

    @Override
    public String getCompsFile()
    {
        return level == Level.CLASS ? IOFN.FILE_CLASS_CDS_COMPS : IOFN.FILE_PACK_CDS_COMPS;
    }

    @Override
    public String getPropsFile()
    {
        return level == Level.CLASS ? IOFN.FILE_CLASS_CDS_PROPS : IOFN.FILE_PACK_CDS_PROPS;
    }

    @Override
    public String[] getCompsHeaders()
    {
        return AsTdEvolutionPrinter.cdCompHeaders;
    }

    @Override
    public String[] getPropsHeaders()
    {
        return level == Level.CLASS ?
            AsTdEvolutionPrinter.classCdPropHeaders : AsTdEvolutionPrinter.packCdPropHeaders;
    }

    @Override
    public IntraVersionCd initIntra
        (CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank)
    {
        String compsString = compRecord.get(IPN.AFFECTED_COMPS);
        Set<String> comps = IOUtils.parseStringToSet(compsString, IOUtils.DELIMITER);
        return new IntraVersionCd(smellId, versionId, pageRank, comps);
    }
}
