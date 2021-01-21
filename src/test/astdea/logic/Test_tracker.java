package astdea.logic;

import org.astdea.data.smells.Level;
import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.interversionsmells.InterVersionHd;
import org.astdea.data.smells.interversionsmells.InterVersionUd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.logic.mapping.HdMappings;
import org.astdea.logic.mapping.UdMappings;
import org.astdea.logic.tracker.projecttracker.CdProjectTracker;
import org.astdea.logic.tracker.projecttracker.HdProjectTracker;
import org.astdea.logic.tracker.projecttracker.UdProjectTracker;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_tracker
{

    @Test
    public void test_CdProjectTracker()
    {
        CdProjectTracker projectTracker = new CdProjectTracker(Level.CLASS, ExPrj.INTRA_CD_COUNT);
        final Set<InterVersionCd> ACTUAL_INTERS = projectTracker.track(ExPrj.VERSIONS);
        final CdMappings ACTUAL_MAPPINGS = projectTracker.getMappings();

        Set<List<Set<IntraVersionCd>>> intrasOfExpectedInters = new HashSet<>();

        List<Set<IntraVersionCd>> intrasInter0 = new ArrayList<>();
        intrasInter0.add(new HashSet<>(Arrays.asList(ExPrj.CD_A1, ExPrj.CD_A2)));
        intrasInter0.add(new HashSet<>(Arrays.asList(ExPrj.CD_B1, ExPrj.CD_B2)));
        intrasInter0.add(new HashSet<>(Arrays.asList(ExPrj.CD_C1, ExPrj.CD_C2)));
        intrasInter0.add(new HashSet<>(Arrays.asList(ExPrj.CD_D1, ExPrj.CD_D2)));
        intrasInter0.add(new HashSet<>(Arrays.asList(ExPrj.CD_E1)));
        intrasOfExpectedInters.add(intrasInter0);

        List<Set<IntraVersionCd>> intrasInter1 = new ArrayList<>();
        intrasInter1.add(new HashSet<>(Arrays.asList(ExPrj.CD_B3)));
        intrasOfExpectedInters.add(intrasInter1);

        List<Set<IntraVersionCd>> intrasInter2 = new ArrayList<>();
        intrasInter2.add(new HashSet<>(Arrays.asList(ExPrj.CD_D3)));
        intrasInter2.add(new HashSet<>(Arrays.asList(ExPrj.CD_E2)));
        intrasOfExpectedInters.add(intrasInter2);

        assertEquals(new HashSet<>(Arrays.asList(ExPrj.CD_B2)), ACTUAL_MAPPINGS.getByOldIntra(ExPrj.CD_A1));
        assertEquals(new HashSet<>(Arrays.asList(ExPrj.CD_C1, ExPrj.CD_C2)), ACTUAL_MAPPINGS.getByOldIntra(ExPrj.CD_B2));
        assertEquals(new HashSet<>(Arrays.asList(ExPrj.CD_B1, ExPrj.CD_B2)), ACTUAL_MAPPINGS.getByNewIntra(ExPrj.CD_C1));

        for (final InterVersionCd ACTUAL_INTER : ACTUAL_INTERS)
        {
            assertTrue(intrasOfExpectedInters.remove(ACTUAL_INTER.getIntraVersionSmells()));
        }
        assertEquals(0, intrasOfExpectedInters.size());
    }

    @Test
    public void test_HdProjectTracker()
    {
        HdProjectTracker projectTracker = new HdProjectTracker();
        final Set<InterVersionHd> ACTUAL_INTERS = projectTracker.track(ExPrj.VERSIONS);
        final HdMappings ACTUAL_MAPPINGS = projectTracker.getMappings();

        assertEquals(ExPrj.HD_B1, ACTUAL_MAPPINGS.getByOldIntra(ExPrj.HD_A1));
        assertEquals(ExPrj.HD_B2, ACTUAL_MAPPINGS.getByOldIntra(ExPrj.HD_A2));
        assertEquals(ExPrj.HD_C2, ACTUAL_MAPPINGS.getByOldIntra(ExPrj.HD_B1));

        assertEquals(ExPrj.HD_A1, ACTUAL_MAPPINGS.getByNewIntra(ExPrj.HD_B1));
        assertEquals(ExPrj.HD_A2, ACTUAL_MAPPINGS.getByNewIntra(ExPrj.HD_B2));
        assertEquals(ExPrj.HD_B1, ACTUAL_MAPPINGS.getByNewIntra(ExPrj.HD_C2));

        Set<List<IntraVersionHd>> intrasOfExpectedInters = new HashSet<>();

        intrasOfExpectedInters.add(Arrays.asList(ExPrj.HD_A1, ExPrj.HD_B1, ExPrj.HD_C2));
        intrasOfExpectedInters.add(Arrays.asList(ExPrj.HD_A2, ExPrj.HD_B2));
        intrasOfExpectedInters.add(Arrays.asList(ExPrj.HD_B3));
        intrasOfExpectedInters.add(Arrays.asList(ExPrj.HD_C1));

        for (final InterVersionHd ACTUAL_INTER : ACTUAL_INTERS)
        {
            assertTrue(intrasOfExpectedInters.remove(ACTUAL_INTER.getIntraVersionSmells()));
        }
        assertEquals(0, intrasOfExpectedInters.size());
    }

    @Test
    public void test_UdProjectTracker()
    {
        UdProjectTracker projectTracker = new UdProjectTracker();
        final Set<InterVersionUd> ACTUAL_INTERS = projectTracker.track(ExPrj.VERSIONS);
        final UdMappings ACTUAL_MAPPINGS = projectTracker.getMappings();

        assertEquals(ExPrj.UD_B1, ACTUAL_MAPPINGS.getByOldIntra(ExPrj.UD_A1));
        assertEquals(ExPrj.UD_B2, ACTUAL_MAPPINGS.getByOldIntra(ExPrj.UD_A2));
        assertEquals(ExPrj.UD_C2, ACTUAL_MAPPINGS.getByOldIntra(ExPrj.UD_B1));

        assertEquals(ExPrj.UD_A1, ACTUAL_MAPPINGS.getByNewIntra(ExPrj.UD_B1));
        assertEquals(ExPrj.UD_A2, ACTUAL_MAPPINGS.getByNewIntra(ExPrj.UD_B2));
        assertEquals(ExPrj.UD_B1, ACTUAL_MAPPINGS.getByNewIntra(ExPrj.UD_C2));

        Set<List<IntraVersionUd>> intrasOfExpectedInters = new HashSet<>();

        intrasOfExpectedInters.add(Arrays.asList(ExPrj.UD_A1, ExPrj.UD_B1, ExPrj.UD_C2));
        intrasOfExpectedInters.add(Arrays.asList(ExPrj.UD_A2, ExPrj.UD_B2));
        intrasOfExpectedInters.add(Arrays.asList(ExPrj.UD_B3));
        intrasOfExpectedInters.add(Arrays.asList(ExPrj.UD_C1));

        for (final InterVersionUd ACTUAL_INTER : ACTUAL_INTERS)
        {
            assertTrue(intrasOfExpectedInters.remove(ACTUAL_INTER.getIntraVersionSmells()));
        }
        assertEquals(0, intrasOfExpectedInters.size());
    }
}
