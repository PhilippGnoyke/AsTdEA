package org.astdea.utils;

import org.astdea.data.versions.Component;

import java.util.HashSet;
import java.util.Set;

public final class ComponentCollectionMathUtils implements OverlapUtils<Component> {

    private ComponentCollectionMathUtils() {}

    private static ComponentCollectionMathUtils instance;
    public static ComponentCollectionMathUtils getInstance()
    {
        if (instance==null)
        {
            instance = new ComponentCollectionMathUtils();
        }
        return instance;
    }

    @Override
    public  boolean intersectionAtLeast2(Set<Component> setOld, Set<Component> setNew) {
        int intersection = 0;
        int MIN = 2;
        for (Component compOld : setOld) {
            if (setNew.contains(compOld)) {
                intersection++;
                if (intersection == MIN) {
                    return true;
                }
            }
            else if (compOld.hasSuccessors()) {
                for (Component compNew : setNew) {
                    if (compOld.fcqnOrSuccessorsMatch(compNew)) {
                        intersection++;
                        if (intersection == MIN) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public  double jaccard(Set<Component> setOld, Set<Component> setNew)
    {
        int intersection = sizeOfIntersection(setOld, setNew);
        int union = sizeOfUnion(setOld, setNew, intersection);
        return jaccard(intersection, union);
    }

    // Use this method variant if the intersection is already known to increase efficiency
    @Override

    public   double jaccard(Set<Component> setOld, Set<Component> setNew, int intersection)
    {
        int union = sizeOfUnion(setOld, setNew, intersection);
        return jaccard(intersection, union);
    }

    // Use this method variant if the intersection and the union are already known to increase efficiency
    @Override
    public  double jaccard(int intersection, int union)
    {
        return (double) intersection / union;
    }

    @Override
    public   int sizeOfIntersection(Set<Component> setOld, Set<Component> setNew)
    {
        Set<String> fqcnsNew = new HashSet<>();
        for (Component compNew: setNew)
        {
            fqcnsNew.add(compNew.getFqcn());
        }

        int intersection = 0;
        for (Component compOld : setOld)
        {
            if (setNew.contains(compOld))
            {
                intersection++;
            }
            else if(compOld.hasSuccessors())
            {
                for (String fqcn : compOld.getSuccessors())
                {
                    if (fqcnsNew.contains(fqcn))
                    {
                        intersection++;
                        break;
                    }
                }
            }
        }
        return intersection;
    }

    @Override
    public   int sizeOfUnion(Set<Component> setOld, Set<Component> setNew)
    {
        int intersection = sizeOfIntersection(setOld, setNew);
        return setOld.size() + setNew.size() - intersection;
    }

    // Use this method variant if the intersection is already known to increase efficiency
    @Override
    public   int sizeOfUnion(Set<Component> setOld, Set<Component> setNew, int sizeOfIntersection)
    {
        return setOld.size() + setNew.size() - sizeOfIntersection;
    }


}
