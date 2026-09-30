package org.astdea.utils;

import java.util.Set;

public interface  OverlapUtils<Type> {

    boolean intersectionAtLeast2(Set<Type> setOld, Set<Type> setNew);

    double jaccard(Set<Type> setOld, Set<Type> setNew);

     double jaccard(Set<Type> setOld, Set<Type> setNew, int intersection);

     double jaccard(int intersection, int union);

     int sizeOfIntersection(Set<Type> setOld, Set<Type> setNew);

     int sizeOfUnion(Set<Type> setOld, Set<Type> setNew);

     int sizeOfUnion(Set<Type> setOld, Set<Type> setNew, int sizeOfIntersection);
}

