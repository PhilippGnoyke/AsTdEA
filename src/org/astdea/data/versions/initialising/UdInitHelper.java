package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.AffType;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.data.versions.Component;
import org.astdea.io.IOUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;

import java.util.Set;

class UdInitHelper extends SmellTypeInitHelper<IntraVersionUd> {

    public UdInitHelper(VersionCompManager versionCompManager) {
        super(versionCompManager);
    }

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
        (CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank, int order, int size) {
        Component mainComp = versionCompManager.addComponent(
            compRecord.get(IPN.MAIN_COMP), AffType.UDC);
        Set<Component> lessStablePacks = versionCompManager.addComponents(
            IOUtils.parseStringToSet(compRecord.get(IPN.LESS_STABLE_PACKS), IOUtils.DELIMITER), AffType.UDAE);
        return new IntraVersionUd(smellId, versionId, pageRank, order, size, mainComp, lessStablePacks);
    }
}
