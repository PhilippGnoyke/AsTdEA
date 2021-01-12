package org.astdea.data.versions;

import org.astdea.io.output.OPN;
import org.astdea.logic.deltacalc.Deltas;

public class DeltaSmellsInVersion
{
    private int loc;
    private int classCount;
    private int packCount;

    private int numOfSmellIntros;
    private int numOfSmellRemovs;
    private int numOfClassCdIntros;
    private int numOfClassCdRemovs;
    private int numOfPackCdIntros;
    private int numOfPackCdRemovs;
    private int numOfHdIntros;
    private int numOfHdRemovs;
    private int numOfUdIntros;
    private int numOfUdRemovs;

    public DeltaSmellsInVersion(int versionId, Deltas deltasClassCd, Deltas deltasPackCd,
                                Deltas deltasHd, Deltas deltasUd)
    {
        this.numOfClassCdIntros = deltasClassCd.getIntro(versionId);
        this.numOfClassCdRemovs = deltasClassCd.getRemov(versionId);
        this.numOfPackCdIntros = deltasPackCd.getIntro(versionId);
        this.numOfPackCdRemovs = deltasPackCd.getRemov(versionId);
        this.numOfHdIntros = deltasHd.getIntro(versionId);
        this.numOfHdRemovs = deltasHd.getRemov(versionId);
        this.numOfUdIntros = deltasUd.getIntro(versionId);
        this.numOfUdRemovs = deltasUd.getRemov(versionId);
        numOfSmellIntros = numOfClassCdIntros + numOfPackCdIntros + numOfHdIntros + numOfUdIntros;
        numOfSmellRemovs = numOfClassCdRemovs + numOfPackCdRemovs + numOfHdRemovs + numOfUdRemovs;
    }

    void setLoc(int loc) {this.loc = loc;}

    void setClassCount(int classCount) {this.classCount = classCount;}

    void setPackCount(int packCount) {this.packCount = packCount;}

    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.NUM_OF_SMELL_INTROS -> numOfSmellIntros;
                case OPN.SMELL_INTROS_PER_LOC -> (double) numOfSmellIntros / loc;
                case OPN.SMELL_INTROS_PER_CLASS -> (double) numOfSmellIntros / classCount;
                case OPN.SMELL_INTROS_PER_PACK -> (double) numOfSmellIntros / packCount;
                case OPN.NUM_OF_SMELL_REMOVS -> numOfSmellRemovs;
                case OPN.SMELL_REMOVS_PER_LOC -> (double) numOfSmellRemovs / loc;
                case OPN.SMELL_REMOVS_PER_CLASS -> (double) numOfSmellRemovs / classCount;
                case OPN.SMELL_REMOVS_PER_PACK -> (double) numOfSmellRemovs / packCount;
                case OPN.NUM_OF_CLASS_CD_INTROS -> numOfClassCdIntros;
                case OPN.CLASS_CD_INTROS_PER_LOC -> (double) numOfClassCdIntros / loc;
                case OPN.CLASS_CD_INTROS_PER_CLASS -> (double) numOfClassCdIntros / classCount;
                case OPN.NUM_OF_CLASS_CD_REMOVS -> numOfClassCdRemovs;
                case OPN.CLASS_CD_REMOVS_PER_LOC -> (double) numOfClassCdRemovs / loc;
                case OPN.CLASS_CD_REMOVS_PER_CLASS -> (double) numOfClassCdRemovs / classCount;
                case OPN.NUM_OF_PACK_CD_INTROS -> numOfPackCdIntros;
                case OPN.PACK_CD_INTROS_PER_LOC -> (double) numOfPackCdIntros / loc;
                case OPN.PACK_CD_INTROS_PER_PACK -> (double) numOfPackCdIntros / packCount;
                case OPN.NUM_OF_PACK_CD_REMOVS -> numOfPackCdRemovs;
                case OPN.PACK_CD_REMOVS_PER_LOC -> (double) numOfPackCdRemovs / loc;
                case OPN.PACK_CD_REMOVS_PER_PACK -> (double) numOfPackCdRemovs / packCount;
                case OPN.NUM_OF_HD_INTROS -> numOfHdIntros;
                case OPN.HD_INTROS_PER_LOC -> (double) numOfHdIntros / loc;
                case OPN.HD_INTROS_PER_CLASS -> (double) numOfHdIntros / classCount;
                case OPN.NUM_OF_HD_REMOVS -> numOfHdRemovs;
                case OPN.HD_REMOVS_PER_LOC -> (double) numOfHdRemovs / loc;
                case OPN.HD_REMOVS_PER_CLASS -> (double) numOfHdRemovs / classCount;
                case OPN.NUM_OF_UD_INTROS -> numOfUdIntros;
                case OPN.UD_INTROS_PER_LOC -> (double) numOfUdIntros / loc;
                case OPN.UD_INTROS_PER_PACK -> (double) numOfUdIntros / packCount;
                case OPN.NUM_OF_UD_REMOVS -> numOfUdRemovs;
                case OPN.UD_REMOVS_PER_LOC -> (double) numOfUdRemovs / loc;
                case OPN.UD_REMOVS_PER_PACK -> (double) numOfUdRemovs / packCount;
                default -> null;
            };
    }
}
