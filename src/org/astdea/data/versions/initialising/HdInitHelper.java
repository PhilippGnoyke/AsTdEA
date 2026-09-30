package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.AffType;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.versions.Component;
import org.astdea.io.IOUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;

import java.util.Set;

class HdInitHelper extends SmellTypeInitHelper<IntraVersionHd> {

    public HdInitHelper(VersionCompManager versionCompManager) {
        super(versionCompManager);
    }

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
        (CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank, int order, int size) {
        Component mainComp = versionCompManager.addComponent(
            compRecord.get(IPN.MAIN_COMP), AffType.HDC);
        Set<Component> affComps = versionCompManager.addComponents(
            IOUtils.parseStringToSet(compRecord.get(IPN.AFF_COMPS), IOUtils.DELIMITER), AffType.HDAE);
        Set<Component> effComps = versionCompManager.addComponents(
            IOUtils.parseStringToSet(compRecord.get(IPN.EFF_COMPS), IOUtils.DELIMITER), AffType.HDAE);
        return new IntraVersionHd(smellId, versionId, pageRank, order, size, mainComp, affComps, effComps);
    }
}
