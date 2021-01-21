package org.astdea.data.versions;

import org.astdea.io.output.OPN;
import org.astdea.logic.deltacalc.Deltas;
import org.astdea.utils.MathUtils;

public class DeltaSmellsInVersion
{
    private int loc;
    private int classCount;
    private int packCount;

    private int smellCount;
    private int classCdCount;
    private int packCdCount;
    private int hdCount;
    private int udCount;

    private int smellCountPrev;
    private int classCdCountPrev;
    private int packCdCountPrev;
    private int hdCountPrev;
    private int udCountPrev;

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
        this.numOfSmellIntros = numOfClassCdIntros + numOfPackCdIntros + numOfHdIntros + numOfUdIntros;
        this.numOfSmellRemovs = numOfClassCdRemovs + numOfPackCdRemovs + numOfHdRemovs + numOfUdRemovs;
        this.smellCountPrev = MathUtils.INFINITY;
        this.classCdCountPrev = MathUtils.INFINITY;
        this.packCdCountPrev = MathUtils.INFINITY;
        this.hdCountPrev = MathUtils.INFINITY;
        this.udCountPrev = MathUtils.INFINITY;
    }

    public void setCountsOfCurrVersion
        (int loc, int classCount, int packCount, int classCdCount, int packCdCount, int hdCount, int udCount)
    {
        this.loc = loc;
        this.classCount = classCount;
        this.packCount = packCount;
        this.smellCount = classCdCount + packCdCount + hdCount + udCount;
        this.classCdCount = classCdCount;
        this.packCdCount = packCdCount;
        this.hdCount = hdCount;
        this.udCount = udCount;
    }

    public void setCountsOfPrevVersion(int classCdCountPrev, int packCdCountPrev, int hdCountPrev, int udCountPrev)
    {
        this.smellCountPrev = classCdCountPrev + packCdCountPrev + hdCountPrev + udCountPrev;
        this.classCdCountPrev = classCdCountPrev;
        this.packCdCountPrev = packCdCountPrev;
        this.hdCountPrev = hdCountPrev;
        this.udCountPrev = udCountPrev;
    }

    private double normPerLoc(int dividend)
    {
        return norm(dividend, loc);
    }

    private double normPerClass(int dividend)
    {
        return norm(dividend, classCount);
    }

    private double normPerPack(int dividend)
    {
        return norm(dividend, packCount);
    }

    private double norm(int dividend, int divisor)
    {
        return (double) dividend / divisor;
    }

    public Object get(String fieldName)
    {
        return switch (fieldName)
            {
                case OPN.NUM_OF_SMELL_INTROS -> numOfSmellIntros;
                case OPN.SMELL_INTROS_PER_LOC -> normPerLoc(numOfSmellIntros);
                case OPN.SMELL_INTROS_PER_CLASS -> normPerClass(numOfSmellIntros);
                case OPN.SMELL_INTROS_PER_PACK -> normPerPack(numOfSmellIntros);
                case OPN.SMELL_INTRO_DEGREE -> norm(numOfSmellIntros, smellCount);
                case OPN.NUM_OF_SMELL_REMOVS -> numOfSmellRemovs;
                case OPN.SMELL_REMOVS_PER_LOC -> normPerLoc(numOfSmellRemovs);
                case OPN.SMELL_REMOVS_PER_CLASS -> normPerClass(numOfSmellRemovs);
                case OPN.SMELL_REMOVS_PER_PACK -> normPerPack(numOfSmellRemovs);
                case OPN.SMELL_REMOV_DEGREE -> norm(numOfSmellRemovs, smellCountPrev);

                case OPN.NUM_OF_CLASS_CD_INTROS -> numOfClassCdIntros;
                case OPN.CLASS_CD_INTROS_PER_LOC -> normPerLoc(numOfClassCdIntros);
                case OPN.CLASS_CD_INTROS_PER_CLASS -> normPerClass(numOfClassCdIntros);
                case OPN.CLASS_CD_INTRO_DEGREE -> norm(numOfClassCdIntros, classCdCount);
                case OPN.NUM_OF_CLASS_CD_REMOVS -> numOfClassCdRemovs;
                case OPN.CLASS_CD_REMOVS_PER_LOC -> normPerLoc(numOfClassCdRemovs);
                case OPN.CLASS_CD_REMOVS_PER_CLASS -> normPerClass(numOfClassCdRemovs);
                case OPN.CLASS_CD_REMOV_DEGREE -> norm(numOfClassCdRemovs, classCdCountPrev);

                case OPN.NUM_OF_PACK_CD_INTROS -> numOfPackCdIntros;
                case OPN.PACK_CD_INTROS_PER_LOC -> normPerLoc(numOfPackCdIntros);
                case OPN.PACK_CD_INTROS_PER_PACK -> normPerPack(numOfPackCdIntros);
                case OPN.PACK_CD_INTRO_DEGREE -> norm(numOfPackCdIntros, packCdCount);
                case OPN.NUM_OF_PACK_CD_REMOVS -> numOfPackCdRemovs;
                case OPN.PACK_CD_REMOVS_PER_LOC -> normPerLoc(numOfPackCdRemovs);
                case OPN.PACK_CD_REMOVS_PER_PACK -> normPerPack(numOfPackCdRemovs);
                case OPN.PACK_CD_REMOV_DEGREE -> norm(numOfPackCdRemovs, packCdCountPrev);

                case OPN.NUM_OF_HD_INTROS -> numOfHdIntros;
                case OPN.HD_INTROS_PER_LOC -> normPerLoc(numOfHdIntros);
                case OPN.HD_INTROS_PER_CLASS -> normPerClass(numOfHdIntros);
                case OPN.HD_INTRO_DEGREE -> norm(numOfHdIntros, hdCount);
                case OPN.NUM_OF_HD_REMOVS -> numOfHdRemovs;
                case OPN.HD_REMOVS_PER_LOC -> normPerLoc(numOfHdRemovs);
                case OPN.HD_REMOVS_PER_CLASS -> normPerClass(numOfHdRemovs);
                case OPN.HD_REMOV_DEGREE -> norm(numOfHdRemovs, hdCountPrev);

                case OPN.NUM_OF_UD_INTROS -> numOfUdIntros;
                case OPN.UD_INTROS_PER_LOC -> normPerLoc(numOfUdIntros);
                case OPN.UD_INTROS_PER_PACK -> normPerPack(numOfUdIntros);
                case OPN.UD_INTRO_DEGREE -> norm(numOfUdIntros, udCount);
                case OPN.NUM_OF_UD_REMOVS -> numOfUdRemovs;
                case OPN.UD_REMOVS_PER_LOC -> normPerLoc(numOfUdRemovs);
                case OPN.UD_REMOVS_PER_PACK -> normPerPack(numOfUdRemovs);
                case OPN.UD_REMOV_DEGREE -> norm(numOfUdRemovs, udCountPrev);
                default -> null;
            };
    }
}
