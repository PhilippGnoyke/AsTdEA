package org.astdea.io.output;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import org.astdea.utils.CollectionUtils;

public final class ResultHeaders
{
    private ResultHeaders() {}

    public static final String[] projectMetricsHeaders = new String[]{
        OPN.ANALYSED_VERSIONS,
        OPN.ANALYSED_TIME_SPAN,
        OPN.NUM_OF_INTRA_VERSION_SMELLS,
        OPN.NUM_OF_INTRA_VERSION_CLASS_CDS,
        OPN.NUM_OF_INTRA_VERSION_PACK_CDS,
        OPN.NUM_OF_INTRA_VERSION_HDS,
        OPN.NUM_OF_INTRA_VERSION_UDS,
        OPN.NUM_OF_INTER_VERSION_SMELLS,
        OPN.NUM_OF_INTER_VERSION_CLASS_CDS,
        OPN.NUM_OF_INTER_VERSION_PACK_CDS,
        OPN.NUM_OF_INTER_VERSION_HDS,
        OPN.NUM_OF_INTER_VERSION_UDS,
    };

    public static final String[] generalSmellPropHeaders = new String[]{
        OPN.ID,
        OPN.VERSION_OF_INTRODUCTION,
        OPN.TIME_OF_INTRODUCTION,
        OPN.VERSION_OF_REMOVAL,
        OPN.TIME_OF_REMOVAL,
        OPN.LIFE_SPAN_IN_VERSIONS,
        OPN.LIFE_SPAN_IN_DAYS,
        OPN.VERSION_ORIENTED_RELATIVE_LIFESPAN,
        OPN.TIME_ORIENTED_RELATIVE_LIFESPAN,
        OPN.PRESENT_IN_FIRST_VERSION,
        OPN.PRESENT_IN_LAST_VERSION,
    };

    public static final String[] cdPropHeaders =
        CollectionUtils.mergeStringArrays(generalSmellPropHeaders, new String[]{
            OPN.FAMILY_ORDER,
            OPN.MEDIAN_FAMILY_WIDTH,
            OPN.MIN_FAMILY_WIDTH,
            OPN.MAX_FAMILY_WIDTH,
            OPN.FAMILY_SIZE,
            OPN.PURE_TRANSITIONS,
            OPN.MERGE_ONLY_TRANSITIONS,
            OPN.SPLIT_ONLY_TRANSITIONS,
            OPN.MERGE_AND_SPLIT_TRANSITIONS,
            OPN.MERGES_COUNT,
            OPN.SPLITS_COUNT,
            OPN.SIMPLE_MERGES_SHARE_UNWEIGHTED,
            OPN.SIMPLE_SPLITS_SHARE_UNWEIGHTED,
            OPN.SIMPLE_MERGES_SHARE_WEIGHTED,
            OPN.SIMPLE_SPLITS_SHARE_WEIGHTED,
            OPN.INCOMING_EDGE_COUNT_LARGEST_MERGE,
            OPN.OUTGOING_EDGE_COUNT_LARGEST_SPLIT
        });

    public static final String[] cdCompHeaders = new String[]{
        OPN.ID,
        OPN.CD_INTRA_IDS
    };

    public static final String[] linEvoTypeCompHeaders = new String[]{
        OPN.ID,
        OPN.LIN_EVO_TYPE_INTRA_IDS
    };

    public static final String[] cdEdgesHeaders = new String[]{
        OPN.ID,
        OPN.CD_EDGES
    };

    public static final String[] cdMergeHeaders = new String[]{
        OPN.CD_SOURCE_VERSION,
        OPN.CD_TARGET_VERSION,
        OPN.CD_TARGET_ID,
        OPN.CD_MERGE_SIZE,
        OPN.CD_OVERALL_ORDER_BEFORE,
        OPN.CD_OVERALL_ORDER_AFTER,
        OPN.CD_OVERALL_ORDER_CHANGE,
        OPN.CD_OVERALL_SIZE_BEFORE,
        OPN.CD_OVERALL_SIZE_AFTER,
        OPN.CD_OVERALL_SIZE_CHANGE,
        OPN.CD_OVERALL_SUBCYCLES_BEFORE,
        OPN.CD_OVERALL_SUBCYCLES_AFTER,
        OPN.CD_OVERALL_SUBCYCLES_CHANGE,
        OPN.CD_MERGE_SOURCES
    };

    public static final String[] cdSplitHeaders = new String[]{
        OPN.CD_SOURCE_VERSION,
        OPN.CD_TARGET_VERSION,
        OPN.CD_SOURCE_ID,
        OPN.CD_SPLIT_SIZE,
        OPN.CD_OVERALL_ORDER_BEFORE,
        OPN.CD_OVERALL_ORDER_AFTER,
        OPN.CD_OVERALL_ORDER_CHANGE,
        OPN.CD_OVERALL_SIZE_BEFORE,
        OPN.CD_OVERALL_SIZE_AFTER,
        OPN.CD_OVERALL_SIZE_CHANGE,
        OPN.CD_OVERALL_SUBCYCLES_BEFORE,
        OPN.CD_OVERALL_SUBCYCLES_AFTER,
        OPN.CD_OVERALL_SUBCYCLES_CHANGE,
        OPN.CD_SPLIT_SOURCES
    };

    public static final String[] cdTransitionHeaders = new String[]{
        OPN.CD_SOURCE_VERSION,
        OPN.CD_TARGET_VERSION,
        OPN.CD_SOURCE_ID,
        OPN.CD_TARGET_ID,
        OPN.CD_TRANSITION_TYPE,
        OPN.CD_SHAPE_SOURCE,
        OPN.CD_SHAPE_TARGET
    };

    public static final String[] intraVersionClassCdPropHeadersPt1 = AsTdEvolutionPrinter.classCdPropHeaders;
    public static final String[] intraVersionPackCdPropHeadersPt1 = AsTdEvolutionPrinter.packCdPropHeaders;
    public static final String[] intraVersionHdPropHeadersPt1 = AsTdEvolutionPrinter.hdPropHeaders;
    public static final String[] intraVersionUdPropHeadersPt1 = AsTdEvolutionPrinter.udPropHeaders;

    public static final String[] intraVersionPropHeadersPt2  = new String[]{
      OPN.AGE,
      OPN.REMAINING_AGE
    };

    public static final String[] intraVersionCdPropHeadersPt3  = new String[]{
        OPN.NUM_PREDS,
        OPN.NUM_ALL_PREDS,
        OPN.NUM_SUCCS,
        OPN.NUM_ALL_SUCCS,
    };


    public static final String[] intraVersionClassCdPropHeaders = CollectionUtils.mergeStringArrays(
        intraVersionClassCdPropHeadersPt1, intraVersionPropHeadersPt2,intraVersionCdPropHeadersPt3);

    public static final String[] intraVersionPackCdPropHeaders = CollectionUtils.mergeStringArrays(
        intraVersionPackCdPropHeadersPt1, intraVersionPropHeadersPt2,intraVersionCdPropHeadersPt3);

    public static final String[] intraVersionHdPropHeaders = CollectionUtils.mergeStringArrays(
        intraVersionHdPropHeadersPt1, intraVersionPropHeadersPt2);

    public static final String[] intraVersionUdPropHeaders = CollectionUtils.mergeStringArrays(
        intraVersionUdPropHeadersPt1, intraVersionPropHeadersPt2);

    public static final String[] versionHeadersPt1 = new String[]{
        OPN.VERSION_TIME,
        OPN.VERSION_TIME_SPAN,
    };

    public static final String[] versionHeadersPt2 = AsTdEvolutionPrinter.projectMetricsHeaders;

    public static final String[] versionHeadersPt3 = new String[]{
        OPN.NUM_OF_SMELL_INTROS,
        OPN.SMELL_INTROS_PER_LOC,
        OPN.SMELL_INTROS_PER_CLASS,
        OPN.SMELL_INTROS_PER_PACK,
        OPN.SMELL_INTRO_DEGREE,
        OPN.NUM_OF_SMELL_REMOVS,
        OPN.SMELL_REMOVS_PER_LOC,
        OPN.SMELL_REMOVS_PER_CLASS,
        OPN.SMELL_REMOVS_PER_PACK,
        OPN.SMELL_REMOV_DEGREE,

        OPN.NUM_OF_CLASS_CD_INTROS,
        OPN.CLASS_CD_INTROS_PER_LOC,
        OPN.CLASS_CD_INTROS_PER_CLASS,
        OPN.CLASS_CD_INTRO_DEGREE,
        OPN.NUM_OF_CLASS_CD_REMOVS,
        OPN.CLASS_CD_REMOVS_PER_LOC,
        OPN.CLASS_CD_REMOVS_PER_CLASS,
        OPN.CLASS_CD_REMOV_DEGREE,

        OPN.NUM_OF_PACK_CD_INTROS,
        OPN.PACK_CD_INTROS_PER_LOC,
        OPN.PACK_CD_INTROS_PER_PACK,
        OPN.PACK_CD_INTRO_DEGREE,
        OPN.NUM_OF_PACK_CD_REMOVS,
        OPN.PACK_CD_REMOVS_PER_LOC,
        OPN.PACK_CD_REMOVS_PER_PACK,
        OPN.PACK_CD_REMOV_DEGREE,

        OPN.NUM_OF_HD_INTROS,
        OPN.HD_INTROS_PER_LOC,
        OPN.HD_INTROS_PER_CLASS,
        OPN.HD_INTRO_DEGREE,
        OPN.NUM_OF_HD_REMOVS,
        OPN.HD_REMOVS_PER_LOC,
        OPN.HD_REMOVS_PER_CLASS,
        OPN.HD_REMOV_DEGREE,

        OPN.NUM_OF_UD_INTROS,
        OPN.UD_INTROS_PER_LOC,
        OPN.UD_INTROS_PER_PACK,
        OPN.UD_INTRO_DEGREE,
        OPN.NUM_OF_UD_REMOVS,
        OPN.UD_REMOVS_PER_LOC,
        OPN.UD_REMOVS_PER_PACK,
        OPN.UD_REMOV_DEGREE
    };

    public static final String[] versionHeaders = CollectionUtils.mergeStringArrays(
        versionHeadersPt1, versionHeadersPt2, versionHeadersPt3);

    public static final String[] versionNamesHeaders = new String[]{
        OPN.VERSION_ID,
        OPN.VERSION_NAME
    };
}
