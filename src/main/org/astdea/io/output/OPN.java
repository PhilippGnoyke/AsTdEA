package org.astdea.io.output;

public final class OPN
{
    private OPN() {}

    //IntraVersionSmell
    public final static String AGE = "age";
    public final static String REMAINING_AGE = "remainingAge";
    public final static String NUM_PREDS = "numDirectPredecessors";
    public final static String NUM_ALL_PREDS = "numAllPredecessors";
    public final static String NUM_SUCCS = "numDirectSuccessors";
    public final static String NUM_ALL_SUCCS = "numAllSuccessors";

    // InterVersionSmell
    public final static String ID = "id";
    public final static String VERSION_OF_INTRODUCTION = "versionOfIntroduction";
    public final static String TIME_OF_INTRODUCTION = "timeOfIntroduction";
    public final static String VERSION_OF_REMOVAL = "versionOfRemoval";
    public final static String TIME_OF_REMOVAL = "timeOfRemoval";
    public final static String LIFE_SPAN_IN_VERSIONS = "lifeSpanInVersions";
    public final static String LIFE_SPAN_IN_DAYS = "lifeSpanInDays";
    public final static String VERSION_ORIENTED_RELATIVE_LIFESPAN = "versionOrientedRelativeLifespan";
    public final static String TIME_ORIENTED_RELATIVE_LIFESPAN = "timeOrientedRelativeLifespan";
    public final static String PRESENT_IN_FIRST_VERSION = "presentInFirstVersion";
    public final static String PRESENT_IN_LAST_VERSION = "presentInLastVersion";
    public static final String CD_INTRA_IDS = "intraVersionSmells(versionId.smellId-removalWeight)";
    public static final String LIN_EVO_TYPE_INTRA_IDS = "intraVersionSmells(versionId.smellId)";

    // InterVersionCd
    public final static String FAMILY_ORDER = "familyOrder";
    public final static String MEDIAN_FAMILY_WIDTH = "medianFamilyWidth";
    public final static String MIN_FAMILY_WIDTH = "minFamilyWidth";
    public final static String MAX_FAMILY_WIDTH = "maxFamilyWidth";
    public final static String FAMILY_SIZE = "familySize"; //=Total transitions
    public final static String PURE_TRANSITIONS = "pureTransitions";
    public final static String MERGE_ONLY_TRANSITIONS = "mergeOnlyTransitions";
    public final static String SPLIT_ONLY_TRANSITIONS = "splitOnlyTransitions";
    public final static String MERGE_AND_SPLIT_TRANSITIONS = "mergeAndSplitTransitions";
    public final static String MERGES_COUNT = "mergesCount";
    public final static String SPLITS_COUNT = "splitsCount";
    public final static String SIMPLE_MERGES_SHARE_UNWEIGHTED = "simpleMergesShareUnweighted"; //Share of 2->1 merges among all merges in family
    public final static String SIMPLE_SPLITS_SHARE_UNWEIGHTED = "simpleSplitsShareUnweighted"; //Share of 1->2 splits among all splits in family
    public final static String SIMPLE_MERGES_SHARE_WEIGHTED = "simpleMergesShareWeighted"; //Share of merge transitions in family within 2->1 merges
    public final static String SIMPLE_SPLITS_SHARE_WEIGHTED = "simpleSplitsShareWeighted"; //Share of split transitions in family within 2->1 splits
    public final static String INCOMING_EDGE_COUNT_LARGEST_MERGE = "incomingEdgeCountLargestMerge";
    public final static String OUTGOING_EDGE_COUNT_LARGEST_SPLIT = "outgoingEdgeCountLargestSplit";

    //Other inter-version CD output
    public static final String CD_EDGES = "edges";
    public static final String CD_TARGET_ID = "targetIntraVersionCd";
    public static final String CD_MERGE_SIZE = "incomingEdgesCount";
    public static final String CD_MERGE_SOURCES = "sourceIntraVersionCds";
    public static final String CD_SOURCE_ID = "sourceIntraVersionCd";
    public static final String CD_SPLIT_SIZE = "outgoingEdgesCount";
    public static final String CD_SPLIT_SOURCES = "targetIntraVersionCds";
    public static final String CD_OVERALL_ORDER_BEFORE = "overallOrderBefore";
    public static final String CD_OVERALL_ORDER_AFTER = "overallOrderAfter";
    public static final String CD_OVERALL_ORDER_CHANGE = "overallOrderChange";
    public static final String CD_OVERALL_SIZE_BEFORE = "overallSizeBefore";
    public static final String CD_OVERALL_SIZE_AFTER = "overallSizeAfter";
    public static final String CD_OVERALL_SIZE_CHANGE = "overallSizeChange";
    public static final String CD_OVERALL_SUBCYCLES_BEFORE = "overallSubcyclesBefore";
    public static final String CD_OVERALL_SUBCYCLES_AFTER = "overallSubcyclesAfter";
    public static final String CD_OVERALL_SUBCYCLES_CHANGE = "overallSubcyclesChange";
    public static final String CD_SOURCE_VERSION = "sourceVersion";
    public static final String CD_TARGET_VERSION = "targetVersion";
    public static final String CD_TRANSITION_TYPE = "transitionType";
    public static final String CD_SHAPE_SOURCE = "shapeSource";
    public static final String CD_SHAPE_TARGET = "shapeTarget";


    // Version
    public final static String VERSION_TIME = "versionTime";
    public final static String VERSION_TIME_SPAN = "versionTimeSpan";
    public final static String NUM_OF_SMELL_INTROS = "numOfSmellIntroductions";
    public final static String SMELL_INTROS_PER_LOC = "smellIntroductionsPerLoc";
    public final static String SMELL_INTROS_PER_CLASS = "smellIntroductionsPerClass";
    public final static String SMELL_INTROS_PER_PACK = "smellIntroductionsPerPackage";
    public final static String SMELL_INTRO_DEGREE = "degreeOfSmellIntroduction";
    public final static String NUM_OF_SMELL_REMOVS = "numOfSmellRemovals";
    public final static String SMELL_REMOVS_PER_LOC = "smellRemovalsPerLoc";
    public final static String SMELL_REMOVS_PER_CLASS = "smellRemovalsPerClass";
    public final static String SMELL_REMOVS_PER_PACK = "smellRemovalsPerPackage";
    public final static String SMELL_REMOV_DEGREE = "degreeOfSmellRemoval";
    public final static String NUM_OF_CLASS_CD_INTROS = "numOfClassCdIntroductions";
    public final static String CLASS_CD_INTROS_PER_LOC = "classCdIntroductionsPerLoc";
    public final static String CLASS_CD_INTROS_PER_CLASS = "classCdIntroductionsPerClass";
    public final static String CLASS_CD_INTRO_DEGREE = "degreeOfClassCdIntroduction";
    public final static String NUM_OF_CLASS_CD_REMOVS = "numOfClassCdRemovals";
    public final static String CLASS_CD_REMOVS_PER_LOC = "classCdRemovalsPerLoc";
    public final static String CLASS_CD_REMOVS_PER_CLASS = "classCdRemovalsPerClass";
    public final static String CLASS_CD_REMOV_DEGREE = "degreeOfClassCdRemoval";
    public final static String NUM_OF_PACK_CD_INTROS = "numOfPackCdIntroductions";
    public final static String PACK_CD_INTROS_PER_LOC = "packCdIntroductionsPerLoc";
    public final static String PACK_CD_INTROS_PER_PACK = "packCdIntroductionsPerPackage";
    public final static String PACK_CD_INTRO_DEGREE = "degreeOfPackCdIntroduction";
    public final static String NUM_OF_PACK_CD_REMOVS = "numOfPackCdRemovals";
    public final static String PACK_CD_REMOVS_PER_LOC = "packCdRemovalsPerLoc";
    public final static String PACK_CD_REMOVS_PER_PACK = "packCdRemovalsPerPackage";
    public final static String PACK_CD_REMOV_DEGREE = "degreeOfPackCdRemoval";
    public final static String NUM_OF_HD_INTROS = "numOfHdIntroductions";
    public final static String HD_INTROS_PER_LOC = "hdIntroductionsPerLoc";
    public final static String HD_INTROS_PER_CLASS = "hdIntroductionsPerClass";
    public final static String HD_INTRO_DEGREE = "degreeOfHdIntroduction";
    public final static String NUM_OF_HD_REMOVS = "numOfHdRemovals";
    public final static String HD_REMOVS_PER_LOC = "hdRemovalsPerLoc";
    public final static String HD_REMOVS_PER_CLASS = "hdRemovalsPerClass";
    public final static String HD_REMOV_DEGREE = "degreeOfHdRemoval";
    public final static String NUM_OF_UD_INTROS = "numOfUdIntroductions";
    public final static String UD_INTROS_PER_LOC = "udIntroductionsPerLoc";
    public final static String UD_INTROS_PER_PACK = "udIntroductionsPerPackage";
    public final static String UD_INTRO_DEGREE = "degreeOfUdIntroduction";
    public final static String NUM_OF_UD_REMOVS = "numOfUdRemovals";
    public final static String UD_REMOVS_PER_LOC = "udRemovalsPerLoc";
    public final static String UD_REMOVS_PER_PACK = "udRemovalsPerPackage";
    public final static String UD_REMOV_DEGREE = "degreeOfUdRemoval";

    // Project
    public static final String ANALYSED_VERSIONS = "analysedVersions";
    public static final String ANALYSED_TIME_SPAN = "analysedTimeSpanInDays";
    public static final String NUM_OF_INTER_VERSION_SMELLS = "numOfInterVersionSmells";
    public static final String NUM_OF_INTRA_VERSION_CLASS_CDS = "numOfIntraVersionClassCDs";
    public static final String NUM_OF_INTRA_VERSION_PACK_CDS = "numOfIntraVersionPackCDs";
    public static final String NUM_OF_INTRA_VERSION_HDS = "numOfIntraVersionHDs";
    public static final String NUM_OF_INTRA_VERSION_UDS = "numOfIntraVersionUDs";
    public static final String NUM_OF_INTRA_VERSION_SMELLS = "numOfIntraVersionSmells";
    public static final String NUM_OF_INTER_VERSION_CLASS_CDS = "numOfInterVersionClassCDs";
    public static final String NUM_OF_INTER_VERSION_PACK_CDS = "numOfInterVersionPackCDs";
    public static final String NUM_OF_INTER_VERSION_HDS = "numOfInterVersionHDs";
    public static final String NUM_OF_INTER_VERSION_UDS = "numOfInterVersionUDs";

    // VersionNames
    public static final String VERSION_ID = "versionId";
    public static final String VERSION_NAME = "versionName";
}
