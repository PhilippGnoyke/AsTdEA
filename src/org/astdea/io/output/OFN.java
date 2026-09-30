package org.astdea.io.output;

import org.astdea.data.AffType;

// OFN = OutFileNames
public final class OFN {

    public static final String CSV = ".csv";

    public static final String INTRA_VERSION = "intraVersion";
    public static final String INTER_VERSION = "interVersion";

    public static final String FILE_CLASS_CDS_EDGES = "ClassCDsEdges" + CSV;
    public static final String FILE_PACK_CDS_EDGES = "PackageCDsEdges" + CSV;
    public final static String FILE_CLASS_CD_AFFECTED_COMP_EVO = "classLevelCdAffectedCompEvolution" + CSV;
    public final static String FILE_PACK_CD_AFFECTED_COMP_EVO = "packLevelCdAffectedCompEvolution" + CSV;
    public final static String FILE_HD_CENTRAL_AFFECTED_COMP_EVO = "hdCentralCompAffectedCompEvolution" + CSV;
    public final static String FILE_HD_AFF_EFF_AFFECTED_COMP_EVO = "hdAfferentEfferentCompsAffectedCompEvolution" + CSV;
    public final static String FILE_UD_CENTRAL_AFFECTED_COMP_EVO = "udCentralCompAffectedCompEvolution" + CSV;
    public final static String FILE_UD_AFF_EFF_AFFECTED_COMP_EVO = "udAfferentEfferentCompsAffectedCompEvolution" + CSV;
    public final static String FILE_CLASS_CD_COMP_CHANGE_TRANSITIONS = "classLevelCdCompChangeSmellTransitions" + CSV;
    public final static String FILE_PACK_CD_COMP_CHANGE_TRANSITIONS = "packLevelCdCompChangeSmellTransitions" + CSV;
    public final static String FILE_HD_CENTRAL_COMP_CHANGE_TRANSITIONS = "hdCentralCompCompChangeSmellTransitions" + CSV;
    public final static String FILE_HD_AFF_EFF_COMP_CHANGE_TRANSITIONS = "hdAfferentEfferentCompsCompChangeSmellTransitions" + CSV;
    public final static String FILE_UD_CENTRAL_COMP_CHANGE_TRANSITIONS = "udCentralCompCompChangeSmellTransitions" + CSV;
    public final static String FILE_UD_AFF_EFF_COMP_CHANGE_TRANSITIONS = "udAfferentEfferentCompsCompChangeSmellTransitions" + CSV;




    public static final String FOLDER_CLASS_CDS_MERGES = "ClassCdsMerges";
    public static final String FOLDER_CLASS_CDS_SPLITS = "ClassCdsSplits";
    public static final String FOLDER_CLASS_CDS_TRANSITIONS = "ClassCdTransitions";
    public static final String FOLDER_PACK_CDS_MERGES = "PackCdMerges";
    public static final String FOLDER_PACK_CDS_SPLITS = "PackCdSplits";
    public static final String FOLDER_PACK_CDS_TRANSITIONS = "PackCdTransitions";
    public static final String FOLDER_AFFECTED_COMP_EVO = "AffectedCompEvolution";
    public static final String FOLDER_INCOMING = "Incoming";
    public static final String FOLDER_OUTGOING = "Outgoing";
    public static final String FOLDER_COMP_EVO_TRANSITIONS = "CompEvolutionSmellTransitions";
    public static final String FILE_VERSION_NAMES_CSV = "VersionNames.csv";

    private OFN() {}

    public static String getAffectedOutFile(AffType affType) {
        return switch (affType) {
            case CCD -> OFN.FILE_CLASS_CD_AFFECTED_COMP_EVO;
            case PCD -> OFN.FILE_PACK_CD_AFFECTED_COMP_EVO;
            case HDC -> OFN.FILE_HD_CENTRAL_AFFECTED_COMP_EVO;
            case HDAE -> OFN.FILE_HD_AFF_EFF_AFFECTED_COMP_EVO;
            case UDC -> OFN.FILE_UD_CENTRAL_AFFECTED_COMP_EVO;
            case UDAE -> OFN.FILE_UD_AFF_EFF_AFFECTED_COMP_EVO;
        };
    }



    public static String getTransitionOutFile(AffType affType) {
        return switch (affType) {
            case CCD -> OFN.FILE_CLASS_CD_COMP_CHANGE_TRANSITIONS;
            case PCD -> OFN.FILE_PACK_CD_COMP_CHANGE_TRANSITIONS;
            case HDC -> OFN.FILE_HD_CENTRAL_COMP_CHANGE_TRANSITIONS;
            case HDAE -> OFN.FILE_HD_AFF_EFF_COMP_CHANGE_TRANSITIONS;
            case UDC -> OFN.FILE_UD_CENTRAL_COMP_CHANGE_TRANSITIONS;
            case UDAE -> OFN.FILE_UD_AFF_EFF_COMP_CHANGE_TRANSITIONS;
        };
    }
}
