package astdea.logic;

import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionHd;
import org.astdea.data.smells.intraversionsmells.IntraVersionUd;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.logic.mapping.HdMappings;
import org.astdea.logic.mapping.UdMappings;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class Test_mapping
{
    @Test
    public void test_CdMappings()
    {
        CdMappings mappings = new CdMappings(ExPrj.INTRA_CD_COUNT);
        mappings.put(ExPrj.CD_A1, ExPrj.CD_B2);
        mappings.put(ExPrj.CD_A2, ExPrj.CD_B2);
        mappings.put(ExPrj.CD_B1, ExPrj.CD_C1);
        mappings.put(ExPrj.CD_B2, ExPrj.CD_C1);
        mappings.put(ExPrj.CD_B2, ExPrj.CD_C2);
        mappings.put(ExPrj.CD_C1, ExPrj.CD_D1);
        mappings.put(ExPrj.CD_C1, ExPrj.CD_D2);
        mappings.put(ExPrj.CD_C2, ExPrj.CD_D2);
        mappings.put(ExPrj.CD_D2, ExPrj.CD_E1);
        mappings.put(ExPrj.CD_D3, ExPrj.CD_E2);

        final Set<IntraVersionCd> unmapped = new HashSet<>(Arrays.asList(ExPrj.CD_A3,ExPrj.CD_B3));
        mappings.addToSmellsWOPredecessor(unmapped);
        mappings.addToSmellsWOSuccessor(unmapped);

        final Set<IntraVersionCd> NO_PRED = mappings.getSmellsWOPredecessor();
        assertTrue(NO_PRED.contains(ExPrj.CD_A1));
        assertTrue(NO_PRED.contains(ExPrj.CD_A2));
        assertTrue(NO_PRED.contains(ExPrj.CD_A3));
        assertTrue(NO_PRED.contains(ExPrj.CD_B1));
        assertFalse(NO_PRED.contains(ExPrj.CD_B2));
        assertTrue(NO_PRED.contains(ExPrj.CD_B3));
        assertFalse(NO_PRED.contains(ExPrj.CD_C1));
        assertFalse(NO_PRED.contains(ExPrj.CD_C2));
        assertFalse(NO_PRED.contains(ExPrj.CD_D1));
        assertFalse(NO_PRED.contains(ExPrj.CD_D2));
        assertTrue(NO_PRED.contains(ExPrj.CD_D3));
        assertFalse(NO_PRED.contains(ExPrj.CD_E1));
        assertFalse(NO_PRED.contains(ExPrj.CD_E2));

        final Set<IntraVersionCd> NO_SUC = mappings.getSmellsWOSuccessor();
        assertFalse(NO_SUC.contains(ExPrj.CD_A1));
        assertFalse(NO_SUC.contains(ExPrj.CD_A2));
        assertTrue(NO_SUC.contains(ExPrj.CD_A3));
        assertFalse(NO_SUC.contains(ExPrj.CD_B1));
        assertFalse(NO_SUC.contains(ExPrj.CD_B2));
        assertTrue(NO_SUC.contains(ExPrj.CD_B3));
        assertFalse(NO_SUC.contains(ExPrj.CD_C1));
        assertFalse(NO_SUC.contains(ExPrj.CD_C2));
        assertTrue(NO_SUC.contains(ExPrj.CD_D1));
        assertFalse(NO_SUC.contains(ExPrj.CD_D2));
        assertFalse(NO_SUC.contains(ExPrj.CD_D3));
        assertTrue(NO_SUC.contains(ExPrj.CD_E1));
        assertTrue(NO_SUC.contains(ExPrj.CD_E2));

        final Set<IntraVersionCd> PREDS_C1 = mappings.getByNewIntra(ExPrj.CD_C1);
        final Set<IntraVersionCd> SUCS_C1 = mappings.getByOldIntra(ExPrj.CD_C1);

        assertFalse(PREDS_C1.contains(ExPrj.CD_A1));
        assertTrue(PREDS_C1.contains(ExPrj.CD_B1));
        assertTrue(PREDS_C1.contains(ExPrj.CD_B2));
        assertFalse(PREDS_C1.contains(ExPrj.CD_B3));
        assertFalse(PREDS_C1.contains(ExPrj.CD_C1));
        assertFalse(PREDS_C1.contains(ExPrj.CD_C2));
        assertFalse(PREDS_C1.contains(ExPrj.CD_E1));

        assertFalse(SUCS_C1.contains(ExPrj.CD_B1));
        assertFalse(SUCS_C1.contains(ExPrj.CD_C1));
        assertFalse(SUCS_C1.contains(ExPrj.CD_C2));
        assertTrue(SUCS_C1.contains(ExPrj.CD_D1));
        assertTrue(SUCS_C1.contains(ExPrj.CD_D2));
        assertFalse(SUCS_C1.contains(ExPrj.CD_D3));
        assertFalse(SUCS_C1.contains(ExPrj.CD_D3));
        assertFalse(SUCS_C1.contains(ExPrj.CD_E1));
    }

    @Test
    public void test_HdMappings()
    {
        HdMappings mappings = new HdMappings();
        mappings.put(ExPrj.HD_A1, ExPrj.HD_B1);
        mappings.put(ExPrj.HD_A2, ExPrj.HD_B2);
        mappings.put(ExPrj.HD_B1, ExPrj.HD_C2);
        final Set<IntraVersionHd> unmapped = new HashSet<>(Arrays.asList(ExPrj.HD_B3, ExPrj.HD_C1));
        mappings.addToSmellsWOPredecessor(unmapped);
        mappings.addToSmellsWOSuccessor(unmapped);

        final Set<IntraVersionHd> NO_PRED = mappings.getSmellsWOPredecessor();
        assertTrue(NO_PRED.contains(ExPrj.HD_A1));
        assertTrue(NO_PRED.contains(ExPrj.HD_A2));
        assertTrue(NO_PRED.contains(ExPrj.HD_B3));
        assertTrue(NO_PRED.contains(ExPrj.HD_C1));
        assertFalse(NO_PRED.contains(ExPrj.HD_B1));
        assertFalse(NO_PRED.contains(ExPrj.HD_B2));
        assertFalse(NO_PRED.contains(ExPrj.HD_C2));

        final Set<IntraVersionHd> NO_SUC = mappings.getSmellsWOSuccessor();
        assertTrue(NO_SUC.contains(ExPrj.HD_B2));
        assertTrue(NO_SUC.contains(ExPrj.HD_B3));
        assertTrue(NO_SUC.contains(ExPrj.HD_C1));
        assertTrue(NO_SUC.contains(ExPrj.HD_C2));
        assertFalse(NO_SUC.contains(ExPrj.HD_A1));
        assertFalse(NO_SUC.contains(ExPrj.HD_A2));
        assertFalse(NO_SUC.contains(ExPrj.HD_B1));

        assertEquals(ExPrj.HD_B1, mappings.getByOldIntra(ExPrj.HD_A1));
        assertEquals(ExPrj.HD_B2, mappings.getByOldIntra(ExPrj.HD_A2));
        assertEquals(ExPrj.HD_C2, mappings.getByOldIntra(ExPrj.HD_B1));

        assertEquals(ExPrj.HD_A1, mappings.getByNewIntra(ExPrj.HD_B1));
        assertEquals(ExPrj.HD_A2, mappings.getByNewIntra(ExPrj.HD_B2));
        assertEquals(ExPrj.HD_B1, mappings.getByNewIntra(ExPrj.HD_C2));
    }

    @Test
    public void test_UdMappings()
    {
        UdMappings mappings = new UdMappings();
        mappings.put(ExPrj.UD_A1, ExPrj.UD_B1);
        mappings.put(ExPrj.UD_A2, ExPrj.UD_B2);
        mappings.put(ExPrj.UD_B1, ExPrj.UD_C2);
        final Set<IntraVersionUd> unmapped = new HashSet<>(Arrays.asList(ExPrj.UD_B3, ExPrj.UD_C1));
        mappings.addToSmellsWOPredecessor(unmapped);
        mappings.addToSmellsWOSuccessor(unmapped);

        final Set<IntraVersionUd> NO_PRED = mappings.getSmellsWOPredecessor();
        assertTrue(NO_PRED.contains(ExPrj.UD_A1));
        assertTrue(NO_PRED.contains(ExPrj.UD_A2));
        assertTrue(NO_PRED.contains(ExPrj.UD_B3));
        assertTrue(NO_PRED.contains(ExPrj.UD_C1));
        assertFalse(NO_PRED.contains(ExPrj.UD_B1));
        assertFalse(NO_PRED.contains(ExPrj.UD_B2));
        assertFalse(NO_PRED.contains(ExPrj.UD_C2));

        final Set<IntraVersionUd> NO_SUC = mappings.getSmellsWOSuccessor();
        assertTrue(NO_SUC.contains(ExPrj.UD_B2));
        assertTrue(NO_SUC.contains(ExPrj.UD_B3));
        assertTrue(NO_SUC.contains(ExPrj.UD_C1));
        assertTrue(NO_SUC.contains(ExPrj.UD_C2));
        assertFalse(NO_SUC.contains(ExPrj.UD_A1));
        assertFalse(NO_SUC.contains(ExPrj.UD_A2));
        assertFalse(NO_SUC.contains(ExPrj.UD_B1));

        assertEquals(ExPrj.UD_B1, mappings.getByOldIntra(ExPrj.UD_A1));
        assertEquals(ExPrj.UD_B2, mappings.getByOldIntra(ExPrj.UD_A2));
        assertEquals(ExPrj.UD_C2, mappings.getByOldIntra(ExPrj.UD_B1));

        assertEquals(ExPrj.UD_A1, mappings.getByNewIntra(ExPrj.UD_B1));
        assertEquals(ExPrj.UD_A2, mappings.getByNewIntra(ExPrj.UD_B2));
        assertEquals(ExPrj.UD_B1, mappings.getByNewIntra(ExPrj.UD_C2));
    }
}
