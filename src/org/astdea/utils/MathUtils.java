package org.astdea.utils;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.Set;

public class MathUtils <Type> implements OverlapUtils <Type> {

    public final static int INFINITY = Integer.MAX_VALUE;
    public final static int PERCENTILE_MEDIAN = 50;
    private final static Percentile PERCENTILE = new Percentile().withEstimationType(Percentile.EstimationType.R_7);

    protected MathUtils() {}

    public static Percentile percentile() {return PERCENTILE;}

    public static double median(double[] vals, int begin, int length) {
        return PERCENTILE.evaluate(vals, begin, length, PERCENTILE_MEDIAN);
    }

    public static double median(double[] vals) {
        return PERCENTILE.evaluate(vals, PERCENTILE_MEDIAN);
    }

    @Override
    public double jaccard(Set<Type> set1, Set<Type> set2) {
        int intersection = sizeOfIntersection(set1, set2);
        int union = sizeOfUnion(set1, set2, intersection);
        return jaccard(intersection, union);
    }

    // Use this method variant if the intersection is already known to increase efficiency
    @Override
    public double jaccard(Set<Type> set1, Set<Type> set2, int intersection) {
        int union = sizeOfUnion(set1, set2, intersection);
        return jaccard(intersection, union);
    }

    // Use this method variant if the intersection and the union are already known to increase efficiency
    @Override
    public double jaccard(int intersection, int union) {
        return (double) intersection / union;
    }

    public Set<Type> biggerSet(Set<Type> set1, Set<Type> set2) {
        return set1.size() >= set2.size() ? set1 : set2;
    }

    @Override
    public int sizeOfIntersection(Set<Type> set1, Set<Type> set2) {
        Set<Type> biggerSet = biggerSet(set1, set2);
        Set<Type> smallerSet = set1 == biggerSet ? set2 : set1;

        int intersection = 0;
        for (Type entry : smallerSet) {
            if (biggerSet.contains(entry)) {
                intersection++;
            }
        }
        return intersection;
    }

    @Override
    public int sizeOfUnion(Set<Type> set1, Set<Type> set2) {
        int intersection = sizeOfIntersection(set1, set2);
        return set1.size() + set2.size() - intersection;
    }

    // Use this method variant if the intersection is already known to increase efficiency
    @Override
    public  int sizeOfUnion(Set<Type> set1, Set<Type> set2, int sizeOfIntersection) {
        return set1.size() + set2.size() - sizeOfIntersection;
    }

    public boolean intersectionAtLeastN(Set<Type> set1, Set<Type> set2, int min) {
        Set<Type> biggerSet = biggerSet(set1, set2);
        Set<Type> smallerSet = (set1 == biggerSet) ? set2 : set1;

        int intersection = 0;
        for (Type entry : smallerSet) {
            if (biggerSet.contains(entry)) {
                intersection++;
                if (intersection == min) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean intersectionAtLeast2(Set<Type> set1, Set<Type> set2) {
        final int MIN = 2;
        return intersectionAtLeastN(set1, set2, MIN);
    }

    public static double weightedHarmonicMeanOfTwo(double value1, double value2, double weight1, double weight2) {
        return (weight1 + weight2) / (weight1 / value1 + weight2 / value2);
    }

    public static double calcChange(int after, int before) {
        return (double) (after - before) / before;
    }
}
