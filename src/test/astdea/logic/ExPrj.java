package astdea.logic;

import org.astdea.data.smells.interversionsmells.TimeManager;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.data.versions.Version;

import java.time.LocalDate;
import java.util.*;

// ExPrj = ExampleProject
public final class ExPrj
{
    private ExPrj() {}

    public final static int VERSION_ID_A = 0;
    public final static int VERSION_ID_B = VERSION_ID_A + 1;
    public final static int VERSION_ID_C = VERSION_ID_B + 1;
    public final static int VERSION_ID_D = VERSION_ID_C + 1;
    public final static int VERSION_ID_E = VERSION_ID_D + 1;

    // Correct mapping: A1 -> B2, A2 -> B2, B2 -> C1, B2 -> C2, C1 -> D1, C1 -> D2, D2 -> E1, D3 -> E2
    public final static IntraVersionCd[][] CDS;
    public final static IntraVersionCd CD_A1;
    public final static IntraVersionCd CD_A2;
    public final static IntraVersionCd CD_A3;
    public final static IntraVersionCd CD_B1;
    public final static IntraVersionCd CD_B2;
    public final static IntraVersionCd CD_B3;
    public final static IntraVersionCd CD_C1;
    public final static IntraVersionCd CD_C2;
    public final static IntraVersionCd CD_D1;
    public final static IntraVersionCd CD_D2;
    public final static IntraVersionCd CD_D3;
    public final static IntraVersionCd CD_E1;
    public final static IntraVersionCd CD_E2;
    public final static int INTRA_CD_COUNT;

    // Correct mapping: A1 -> B1, A2 -> B2, B1 -> C2
    public final static IntraVersionHd[][] HDS;
    public final static IntraVersionHd HD_A1;
    public final static IntraVersionHd HD_A2;
    public final static IntraVersionHd HD_B1;
    public final static IntraVersionHd HD_B2;
    public final static IntraVersionHd HD_B3;
    public final static IntraVersionHd HD_C1;
    public final static IntraVersionHd HD_C2;

    // Correct mapping: A1 -> B1, A2 -> B2, B1 -> C2
    public final static IntraVersionUd[][] UDS;
    public final static IntraVersionUd UD_A1;
    public final static IntraVersionUd UD_A2;
    public final static IntraVersionUd UD_B1;
    public final static IntraVersionUd UD_B2;
    public final static IntraVersionUd UD_B3;
    public final static IntraVersionUd UD_C1;
    public final static IntraVersionUd UD_C2;

    public final static List<Version> VERSIONS;

    static
    {
        int smellId = 0;

        final Set<String> COMPS_A1 = new HashSet<>(Arrays.asList("A", "B"));
        final int PR_A1 = 1;

        final Set<String> COMPS_A2 = new HashSet<>(Arrays.asList("D", "E"));
        final int PR_A2 = 1;

        final Set<String> COMPS_A3 = new HashSet<>(Arrays.asList("X", "Y"));
        final int PR_A3 = 1;

        final Set<String> COMPS_B1 = new HashSet<>(Arrays.asList("C", "F"));
        final int PR_B1 = 1;

        final Set<String> COMPS_B2 = new HashSet<>(Arrays.asList("A", "B", "D", "E"));
        final int PR_B2 = 1;

        final Set<String> COMPS_B3 = new HashSet<>(Arrays.asList("H", "I"));
        final int PR_B3 = 1;

        final Set<String> COMPS_C1 = new HashSet<>(Arrays.asList("A", "B", "C", "F"));
        final int PR_C1 = 1;

        final Set<String> COMPS_C2 = new HashSet<>(Arrays.asList("D", "E", "G"));
        final int PR_C2 = 1;

        final Set<String> COMPS_D1 = new HashSet<>(Arrays.asList("A", "B"));
        final int PR_D1 = 1;

        final Set<String> COMPS_D2 = new HashSet<>(Arrays.asList("C", "D", "E", "F"));
        final int PR_D2 = 1;

        final Set<String> COMPS_D3 = new HashSet<>(Arrays.asList("I", "H"));
        final int PR_D3 = 1;

        final Set<String> COMPS_E1 = new HashSet<>(Arrays.asList("B", "C", "D", "E", "F"));
        final int PR_E1 = 1;

        final Set<String> COMPS_E2 = new HashSet<>(Arrays.asList("G", "H", "I"));
        final int PR_E2 = 1;

        CD_A1 = new IntraVersionCd(smellId++, VERSION_ID_A, PR_A1, COMPS_A1);
        CD_A2 = new IntraVersionCd(smellId++, VERSION_ID_A, PR_A2, COMPS_A2);
        CD_A3 = new IntraVersionCd(smellId++, VERSION_ID_A, PR_A3, COMPS_A3);
        CD_B1 = new IntraVersionCd(smellId++, VERSION_ID_B, PR_B1, COMPS_B1);
        CD_B2 = new IntraVersionCd(smellId++, VERSION_ID_B, PR_B2, COMPS_B2);
        CD_B3 = new IntraVersionCd(smellId++, VERSION_ID_B, PR_B3, COMPS_B3);
        CD_C1 = new IntraVersionCd(smellId++, VERSION_ID_C, PR_C1, COMPS_C1);
        CD_C2 = new IntraVersionCd(smellId++, VERSION_ID_C, PR_C2, COMPS_C2);
        CD_D1 = new IntraVersionCd(smellId++, VERSION_ID_D, PR_D1, COMPS_D1);
        CD_D2 = new IntraVersionCd(smellId++, VERSION_ID_D, PR_D2, COMPS_D2);
        CD_D3 = new IntraVersionCd(smellId++, VERSION_ID_D, PR_D3, COMPS_D3);
        CD_E1 = new IntraVersionCd(smellId++, VERSION_ID_E, PR_E1, COMPS_E1);
        CD_E2 = new IntraVersionCd(smellId, VERSION_ID_E, PR_E2, COMPS_E2);

        CDS = new IntraVersionCd[][]{
            {CD_A1, CD_A2,CD_A3}, {CD_B1, CD_B2, CD_B3}, {CD_C1, CD_C2}, {CD_D1, CD_D2, CD_D3}, {CD_E1, CD_E2}
        };
        INTRA_CD_COUNT = 12;
    }

    static
    {
        int smellId = 0;

        final String MAIN_COMP_A1 = "1";
        final Set<String> AFF_COMPS_A1 = new HashSet<>(Arrays.asList("2", "3", "4"));
        final Set<String> EFF_COMPS_A1 = new HashSet<>(Arrays.asList("5", "6", "7", "8"));
        final int PR_A1 = 1;

        final String MAIN_COMP_A2 = "9";
        final Set<String> AFF_COMPS_A2 = new HashSet<>(Arrays.asList("10", "11"));
        final Set<String> EFF_COMPS_A2 = new HashSet<>(Arrays.asList("12", "13"));
        final int PR_A2 = 1;

        final String MAIN_COMP_B1 = "1";
        final Set<String> AFF_COMPS_B1 = new HashSet<>(Arrays.asList("2", "14"));
        final Set<String> EFF_COMPS_B1 = new HashSet<>(Arrays.asList("5", "6"));
        final int PR_B1 = 1;

        final String MAIN_COMP_B2 = "15";
        final Set<String> AFF_COMPS_B2 = new HashSet<>(Arrays.asList("10", "11"));
        final Set<String> EFF_COMPS_B2 = new HashSet<>(Arrays.asList("12", "13", "14"));
        final int PR_B2 = 1;

        final String MAIN_COMP_B3 = "16";
        final Set<String> AFF_COMPS_B3 = new HashSet<>(Arrays.asList("17", "18"));
        final Set<String> EFF_COMPS_B3 = new HashSet<>(Arrays.asList("19", "20", "21"));
        final int PR_B3 = 1;

        final String MAIN_COMP_C1 = "22";
        final Set<String> AFF_COMPS_C1 = new HashSet<>(Arrays.asList("2", "14"));
        final Set<String> EFF_COMPS_C1 = new HashSet<>(Arrays.asList("5", "6"));
        final int PR_C1 = 5;

        final String MAIN_COMP_C2 = "22";
        final Set<String> AFF_COMPS_C2 = new HashSet<>(Arrays.asList("2", "14"));
        final Set<String> EFF_COMPS_C2 = new HashSet<>(Arrays.asList("5", "6"));
        final int PR_C2 = 3;

        HD_A1 = new IntraVersionHd(smellId++, VERSION_ID_A, PR_A1, MAIN_COMP_A1, AFF_COMPS_A1, EFF_COMPS_A1);
        HD_A2 = new IntraVersionHd(smellId++, VERSION_ID_A, PR_A2, MAIN_COMP_A2, AFF_COMPS_A2, EFF_COMPS_A2);
        HD_B1 = new IntraVersionHd(smellId++, VERSION_ID_B, PR_B1, MAIN_COMP_B1, AFF_COMPS_B1, EFF_COMPS_B1);
        HD_B2 = new IntraVersionHd(smellId++, VERSION_ID_B, PR_B2, MAIN_COMP_B2, AFF_COMPS_B2, EFF_COMPS_B2);
        HD_B3 = new IntraVersionHd(smellId++, VERSION_ID_B, PR_B3, MAIN_COMP_B3, AFF_COMPS_B3, EFF_COMPS_B3);
        HD_C1 = new IntraVersionHd(smellId++, VERSION_ID_C, PR_C1, MAIN_COMP_C1, AFF_COMPS_C1, EFF_COMPS_C1);
        HD_C2 = new IntraVersionHd(smellId, VERSION_ID_C, PR_C2, MAIN_COMP_C2, AFF_COMPS_C2, EFF_COMPS_C2);

        HDS = new IntraVersionHd[][]{{HD_A1, HD_A2}, {HD_B1, HD_B2, HD_B3}, {HD_C1, HD_C2}};
    }

    static
    {
        int smellId = 0;

        final String MAIN_COMP_A1 = "1";
        final Set<String> LESS_STABLES_A1 = new HashSet<>(Arrays.asList("2", "3", "4"));
        final int PR_A1 = 1;

        final String MAIN_COMP_A2 = "9";
        final Set<String> LESS_STABLES_A2 = new HashSet<>(Arrays.asList("10", "11"));
        final int PR_A2 = 1;

        final String MAIN_COMP_B1 = "1";
        final Set<String> LESS_STABLES_B1 = new HashSet<>(Arrays.asList("2", "14", "15"));
        final int PR_B1 = 1;

        final String MAIN_COMP_B2 = "15";
        final Set<String> LESS_STABLES_B2 = new HashSet<>(Arrays.asList("10", "11", "12"));
        final int PR_B2 = 1;

        final String MAIN_COMP_B3 = "16";
        final Set<String> LESS_STABLES_B3 = new HashSet<>(Arrays.asList("17", "18"));
        final int PR_B3 = 1;

        final String MAIN_COMP_C1 = "22";
        final Set<String> LESS_STABLES_C1 = new HashSet<>(Arrays.asList("2", "14"));
        final int PR_C1 = 5;

        final String MAIN_COMP_C2 = "22";
        final Set<String> LESS_STABLES_C2 = new HashSet<>(Arrays.asList("2", "14"));
        final int PR_C2 = 3;

        UD_A1 = new IntraVersionUd(smellId++, VERSION_ID_A, PR_A1, MAIN_COMP_A1, LESS_STABLES_A1);
        UD_A2 = new IntraVersionUd(smellId++, VERSION_ID_A, PR_A2, MAIN_COMP_A2, LESS_STABLES_A2);
        UD_B1 = new IntraVersionUd(smellId++, VERSION_ID_B, PR_B1, MAIN_COMP_B1, LESS_STABLES_B1);
        UD_B2 = new IntraVersionUd(smellId++, VERSION_ID_B, PR_B2, MAIN_COMP_B2, LESS_STABLES_B2);
        UD_B3 = new IntraVersionUd(smellId++, VERSION_ID_B, PR_B3, MAIN_COMP_B3, LESS_STABLES_B3);
        UD_C1 = new IntraVersionUd(smellId++, VERSION_ID_C, PR_C1, MAIN_COMP_C1, LESS_STABLES_C1);
        UD_C2 = new IntraVersionUd(smellId, VERSION_ID_C, PR_C2, MAIN_COMP_C2, LESS_STABLES_C2);

        UDS = new IntraVersionUd[][]{{UD_A1, UD_A2}, {UD_B1, UD_B2, UD_B3}, {UD_C1, UD_C2}};
    }
    static
    {
        final int VERSION_COUNT = CDS.length;
        final String OUTDIR = "out";
        final int VERSION_DURATION = 1;
        VERSIONS = new ArrayList<>();
        LocalDate[] versionTimes = new LocalDate[VERSION_COUNT];
        for(int versionId = 0;versionId<VERSION_COUNT;versionId++)
        {
            LocalDate date = LocalDate.of(2020,1,1+versionId);
            versionTimes[versionId] = date;
            Set<IntraVersionCd> classCds = new HashSet<>(Arrays.asList(CDS[versionId]));
            Set<IntraVersionCd> packCds = new HashSet<>(Arrays.asList(CDS[versionId]));
            Set<IntraVersionHd> hds = new HashSet<>();
            Set<IntraVersionUd> uds = new HashSet<>();
            if(versionId<HDS.length){hds.addAll(Arrays.asList(HDS[versionId]));}
            if(versionId<UDS.length){uds.addAll(Arrays.asList(UDS[versionId]));}
            VERSIONS.add(new Version(versionId, OUTDIR, date, VERSION_DURATION, classCds,packCds,hds,uds));
        }
        TimeManager.initManually(versionTimes);
    }
}
