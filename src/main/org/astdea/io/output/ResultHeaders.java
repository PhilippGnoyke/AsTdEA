package org.astdea.io.output;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.astdea.utils.CollectionUtils;

public final class ResultHeaders
{
    private ResultHeaders() {}

    public static String[] getProjectMetricsHeaders()
    {
        return new String[]{
            OPN.PROPERTY_ANALYSED_VERSIONS,
            OPN.PROPERTY_ANALYSED_TIME_SPAN,
            OPN.PROPERTY_NUM_OF_INTRA_VERSION_SMELLS,
            OPN.PROPERTY_NUM_OF_INTRA_VERSION_CLASS_CDS,
            OPN.PROPERTY_NUM_OF_INTRA_VERSION_PACK_CDS,
            OPN.PROPERTY_NUM_OF_INTRA_VERSION_HDS,
            OPN.PROPERTY_NUM_OF_INTRA_VERSION_UDS,
            OPN.PROPERTY_NUM_OF_INTER_VERSION_SMELLS,
            OPN.PROPERTY_NUM_OF_INTER_VERSION_CLASS_CDS,
            OPN.PROPERTY_NUM_OF_INTER_VERSION_PACK_CDS,
            OPN.PROPERTY_NUM_OF_INTER_VERSION_HDS,
            OPN.PROPERTY_NUM_OF_INTER_VERSION_UDS,
        };
    }

    public static String[] getGeneralSmellPropHeaders()
    {
        return new String[]{
            OPN.PROPERTY_ID,
            OPN.PROPERTY_VERSION_OF_INTRODUCTION,
            OPN.PROPERTY_TIME_OF_INTRODUCTION,
            OPN.PROPERTY_VERSION_OF_REMOVAL,
            OPN.PROPERTY_TIME_OF_REMOVAL,
            OPN.PROPERTY_LIFE_SPAN_IN_VERSIONS,
            OPN.PROPERTY_LIFE_SPAN_IN_DAYS,
            OPN.PROPERTY_VERSION_ORIENTED_RELATIVE_LIFESPAN,
            OPN.PROPERTY_TIME_ORIENTED_RELATIVE_LIFESPAN,
            OPN.PROPERTY_PRESENT_IN_FIRST_VERSION,
            OPN.PROPERTY_PRESENT_IN_LAST_VERSION,
        };
    }

    public static String[] getCdPropHeaders()
    {
        String[] classCdHeaders = {
            OPN.PROPERTY_FAMILY_ORDER,
            OPN.PROPERTY_MEDIAN_FAMILY_WIDTH,
            OPN.PROPERTY_MAX_FAMILY_WIDTH
        };
        return CollectionUtils.mergeStringArrays(getGeneralSmellPropHeaders(), classCdHeaders);
    }

    public static String[] getGeneralSmellCompHeaders()
    {
        return new String[]{
            OPN.PROPERTY_ID,
            OPN.INTRA_IDS
        };
    }

    public static String[] getCdEdgesHeaders()
    {
        return new String[]{
            OPN.PROPERTY_ID,
            OPN.CD_EDGES
        };
    }

    public static String[] getVersionHeaders()
    {
        return CollectionUtils.mergeStringArrays(getVersionHeadersPt1(),
            getVersionHeadersPt2(), getVersionHeadersPt3());
    }

    public static String[] getVersionHeadersPt1()
    {
        return new String[]{
            OPN.VERSION_TIME,
            OPN.VERSION_TIME_SPAN,
        };
    }

    public static String[] getVersionHeadersPt2()
    {
        return AsTdEvolutionPrinter.getProjectMetricsHeaders();
    }

    public static String[] getVersionHeadersPt3()
    {
        return new String[]{
            OPN.NUM_OF_SMELL_INTROS,
            OPN.SMELL_INTROS_PER_LOC,
            OPN.SMELL_INTROS_PER_CLASS,
            OPN.SMELL_INTROS_PER_PACK,
            OPN.NUM_OF_SMELL_REMOVS,
            OPN.SMELL_REMOVS_PER_LOC,
            OPN.SMELL_REMOVS_PER_CLASS,
            OPN.SMELL_REMOVS_PER_PACK,
            OPN.NUM_OF_CLASS_CD_INTROS,
            OPN.CLASS_CD_INTROS_PER_LOC,
            OPN.CLASS_CD_INTROS_PER_CLASS,
            OPN.NUM_OF_CLASS_CD_REMOVS,
            OPN.CLASS_CD_REMOVS_PER_LOC,
            OPN.CLASS_CD_REMOVS_PER_CLASS,
            OPN.PACK_CD_INTROS_PER_LOC,
            OPN.PACK_CD_INTROS_PER_PACK,
            OPN.NUM_OF_PACK_CD_REMOVS,
            OPN.PACK_CD_REMOVS_PER_LOC,
            OPN.PACK_CD_REMOVS_PER_PACK,
            OPN.NUM_OF_HD_INTROS,
            OPN.HD_INTROS_PER_LOC,
            OPN.HD_INTROS_PER_CLASS,
            OPN.NUM_OF_HD_REMOVS,
            OPN.HD_REMOVS_PER_LOC,
            OPN.HD_REMOVS_PER_CLASS,
            OPN.NUM_OF_UD_INTROS,
            OPN.UD_INTROS_PER_LOC,
            OPN.UD_INTROS_PER_PACK,
            OPN.NUM_OF_UD_REMOVS,
            OPN.UD_REMOVS_PER_LOC,
            OPN.UD_REMOVS_PER_PACK
        };
    }
}
