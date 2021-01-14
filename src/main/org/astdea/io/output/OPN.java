package org.astdea.io.output;

public final class OPN
{
    private OPN() {}

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
    public static final String INTRA_IDS = "intraVersionSmells(versionId.smellId-removalWeight)";

    // InterVersionCd
    public final static String FAMILY_ORDER = "familyOrder";
    public final static String MEDIAN_FAMILY_WIDTH = "medianFamilyWidth";
    public final static String MAX_FAMILY_WIDTH = "maxFamilyWidth";
    public static final String CD_EDGES = "edges";

    // Version
    public final static String VERSION_TIME = "versionTime";
    public final static String VERSION_TIME_SPAN = "versionTimeSpan";
    public final static String NUM_OF_SMELL_INTROS = "numOfSmellIntroductions";
    public final static String SMELL_INTROS_PER_LOC = "smellIntroductionsPerLoc";
    public final static String SMELL_INTROS_PER_CLASS = "smellIntroductionsPerClass";
    public final static String SMELL_INTROS_PER_PACK = "smellIntroductionsPerPackage";
    public final static String NUM_OF_SMELL_REMOVS = "numOfSmellRemovals";
    public final static String SMELL_REMOVS_PER_LOC = "smellRemovalsPerLoc";
    public final static String SMELL_REMOVS_PER_CLASS = "smellRemovalsPerClass";
    public final static String SMELL_REMOVS_PER_PACK = "smellRemovalsPerPackage";
    public final static String NUM_OF_CLASS_CD_INTROS = "numOfClassCdIntroductions";
    public final static String CLASS_CD_INTROS_PER_LOC = "classCdIntroductionsPerLoc";
    public final static String CLASS_CD_INTROS_PER_CLASS = "classCdIntroductionsPerClass";
    public final static String NUM_OF_CLASS_CD_REMOVS = "numOfClassCdRemovals";
    public final static String CLASS_CD_REMOVS_PER_LOC = "classCdRemovalsPerLoc";
    public final static String CLASS_CD_REMOVS_PER_CLASS = "classCdRemovalsPerClass";
    public final static String NUM_OF_PACK_CD_INTROS = "numOfPackCdIntroductions";
    public final static String PACK_CD_INTROS_PER_LOC = "packCdIntroductionsPerLoc";
    public final static String PACK_CD_INTROS_PER_PACK = "packCdIntroductionsPerPackage";
    public final static String NUM_OF_PACK_CD_REMOVS = "numOfPackCdRemovals";
    public final static String PACK_CD_REMOVS_PER_LOC = "packCdRemovalsPerLoc";
    public final static String PACK_CD_REMOVS_PER_PACK = "packCdRemovalsPerPackage";
    public final static String NUM_OF_HD_INTROS = "numOfHdIntroductions";
    public final static String HD_INTROS_PER_LOC = "hdIntroductionsPerLoc";
    public final static String HD_INTROS_PER_CLASS = "hdIntroductionsPerClass";
    public final static String NUM_OF_HD_REMOVS = "numOfHdRemovals";
    public final static String HD_REMOVS_PER_LOC = "hdRemovalsPerLoc";
    public final static String HD_REMOVS_PER_CLASS = "hdRemovalsPerClass";
    public final static String NUM_OF_UD_INTROS = "numOfUdIntroductions";
    public final static String UD_INTROS_PER_LOC = "udIntroductionsPerLoc";
    public final static String UD_INTROS_PER_PACK = "udIntroductionsPerPackage";
    public final static String NUM_OF_UD_REMOVS = "numOfUdRemovals";
    public final static String UD_REMOVS_PER_LOC = "udRemovalsPerLoc";
    public final static String UD_REMOVS_PER_PACK = "udRemovalsPerPackage";

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
