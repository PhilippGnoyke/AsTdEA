package org.astdea.data.versions.initialising;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.apache.commons.csv.CSVRecord;
import org.astdea.data.AffType;
import org.astdea.data.smells.Level;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.Shape;
import org.astdea.data.versions.Component;
import org.astdea.io.IOUtils;
import org.astdea.io.input.IPN;
import org.astdea.io.inputoutput.IOFN;

import java.util.Set;

class CdInitHelper extends SmellTypeInitHelper<IntraVersionCd> {

    private Level level;

    public CdInitHelper(VersionCompManager versionCompManager, Level level) {
        super(versionCompManager);
        this.level = level;
    }

    @Override
    public String getCompsFile() {
        return level == Level.CLASS ? IOFN.FILE_CLASS_CDS_COMPS : IOFN.FILE_PACK_CDS_COMPS;
    }

    @Override
    public String getPropsFile() {
        return level == Level.CLASS ? IOFN.FILE_CLASS_CDS_PROPS : IOFN.FILE_PACK_CDS_PROPS;
    }

    @Override
    public String[] getCompsHeaders() {
        return AsTdEvolutionPrinter.cdCompHeaders;
    }

    @Override
    public String[] getPropsHeaders() {
        return level == Level.CLASS ?
            AsTdEvolutionPrinter.classCdPropHeaders : AsTdEvolutionPrinter.packCdPropHeaders;
    }

    @Override
    public IntraVersionCd initIntra
        (CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank, int order, int size) {
        int numSubcycles = Integer.parseInt(propRecord.get(IPN.NUM_SUBCYCLES));
        int mfesSize = Integer.parseInt(propRecord.get(IPN.MFES_SIZE));
        int mfesWoTinysSize = Integer.parseInt(propRecord.get(IPN.MFES_WO_TINYS_SIZE));
        Shape shape = Shape.parseString(propRecord.get(IPN.SHAPE));
        String compsString = compRecord.get(IPN.AFFECTED_COMPS);
        Set<Component> comps = versionCompManager.addComponents(
            IOUtils.parseStringToSet(compsString, IOUtils.DELIMITER), AffType.getCdAffTypeFromLevel(level));
        return new IntraVersionCd(
            smellId, versionId, pageRank, order, size, numSubcycles, mfesSize, mfesWoTinysSize, shape, comps);
    }
}
