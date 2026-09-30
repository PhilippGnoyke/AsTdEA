package utils;

import org.astdea.utils.CollectionUtils;
import org.astdea.utils.MathUtils;
import org.astdea.utils.MathUtilsString;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class Test_utils
{
    final double ALLOWED_DELTA = 0.00001;
    final static MathUtilsString mathUtils = MathUtilsString.getInstance();

    @Test
    public void test_MathUtils_median()
    {

        final double[] VALS = new double[]{8, 41, 3, 4.5, 7.2, 14.1};
        assertEquals(7.6, MathUtils.median(VALS), ALLOWED_DELTA);
        assertEquals(7.2, MathUtils.median(VALS, 0, 5), ALLOWED_DELTA);
        assertEquals(5.85, MathUtils.median(VALS, 1, 4), ALLOWED_DELTA);
    }

    @Test
    public void test_MathUtils_SetOperations()
    {
        final double SET1_SET2_JACC = 0.1666666667;
        final int SET1_SET1_JACC = 1;
        final int SET1_SET2_INT = 1;
        final int SET1_SET2_UNI = 6;
        final int SET1_SET3_INT = 0;

        final Set<String> SET1 = new HashSet<>(Arrays.asList("A", "B", "C"));
        final Set<String> SET2 = new HashSet<>(Arrays.asList("C", "D", "E", "F"));
        final Set<String> SET3 = new HashSet<>(Arrays.asList("G", "H"));

        assertEquals(SET1_SET1_JACC, mathUtils.jaccard(SET1, SET1), ALLOWED_DELTA);
        assertEquals(SET1_SET3_INT, mathUtils.jaccard(SET1, SET3), ALLOWED_DELTA);
        assertEquals(SET1_SET2_JACC, mathUtils.jaccard(SET1, SET2), ALLOWED_DELTA);
        assertEquals(SET1_SET2_JACC, mathUtils.jaccard(SET2, SET1), ALLOWED_DELTA);
        assertEquals(SET1_SET2_JACC, mathUtils.jaccard(SET1_SET2_INT, SET1_SET2_UNI), ALLOWED_DELTA);
        assertEquals(SET2, mathUtils.biggerSet(SET1, SET2));
        assertEquals(SET2, mathUtils.biggerSet(SET2, SET1));

        assertEquals(SET1.size(), mathUtils.sizeOfIntersection(SET1, SET1));
        assertEquals(SET1_SET3_INT, mathUtils.sizeOfIntersection(SET1, SET3));
        assertEquals(SET1_SET2_INT, mathUtils.sizeOfIntersection(SET2, SET1));
        assertEquals(SET1_SET2_INT, mathUtils.sizeOfIntersection(SET1, SET2));
        assertEquals(SET1.size(), mathUtils.sizeOfUnion(SET1, SET1));
        assertEquals(SET1.size() + SET3.size(), mathUtils.sizeOfUnion(SET1, SET3));
        assertEquals(SET1_SET2_UNI, mathUtils.sizeOfUnion(SET2, SET1));
        assertEquals(SET1_SET2_UNI, mathUtils.sizeOfUnion(SET1, SET2));
        assertEquals(SET1.size(), mathUtils.sizeOfUnion(SET1, SET1, SET1.size()));
        assertEquals(SET1.size() + SET3.size(), mathUtils.sizeOfUnion(SET1, SET3, SET1_SET3_INT));
        assertEquals(SET1_SET2_UNI, mathUtils.sizeOfUnion(SET2, SET1, SET1_SET2_INT));
        assertEquals(SET1_SET2_UNI, mathUtils.sizeOfUnion(SET1, SET2, SET1_SET2_INT));
        assertFalse(mathUtils.intersectionAtLeast2(SET1, SET2));
        assertTrue(mathUtils.intersectionAtLeast2(SET1, SET1));
        assertTrue(mathUtils.intersectionAtLeastN(SET1, SET1, SET1_SET2_INT));
        assertFalse(mathUtils.intersectionAtLeastN(SET1, SET2, SET1_SET2_INT + 1));
    }

    @Test
    public void test_weightedHarmonicMeanOfTwo()
    {
        final double ACTUAL = mathUtils.weightedHarmonicMeanOfTwo(24.6, 3.17, 3.6, 2.9);
        final double EXPECTED = 6.1253262761;
        assertEquals(EXPECTED, ACTUAL, ALLOWED_DELTA);
    }

    @Test
    public void test_mergeStringArrays()
    {
        final String[] ARR1 = new String[]{"E", "F", "G"};
        final String[] ARR2 = new String[]{"A", "B", "C", "D"};
        final String[] ACTUAL = CollectionUtils.mergeStringArrays(ARR1, ARR2);
        assertEquals("E", ACTUAL[0]);
        assertEquals("G", ACTUAL[2]);
        assertEquals("A", ACTUAL[3]);
        assertEquals("D", ACTUAL[6]);
    }
}
