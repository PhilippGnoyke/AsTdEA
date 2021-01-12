package org.astdea.logic.interbuilding;

import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.utils.CollectionUtils;
import org.astdea.utils.MathUtils;

import java.util.*;

public class InterVersionCdBuilder
{
    private Set<IntraVersionCd> visited;
    private Map<IntraId, ProtoCdInter> interAssigns;
    protected Map<IntraId, Set<IntraVersionCd>> mappingsOldAsKey;
    protected Set<IntraVersionCd> smellsWOPredecessor;

    public InterVersionCdBuilder(int totalNumOfIntras, Map<IntraId, Set<IntraVersionCd>> mappingsOldAsKey,
                                 Set<IntraVersionCd> smellsWOPredecessor)
    {
        this.mappingsOldAsKey = mappingsOldAsKey;
        this.smellsWOPredecessor = smellsWOPredecessor;
        visited = CollectionUtils.initHashSet(totalNumOfIntras);
        interAssigns = CollectionUtils.initHashMap(totalNumOfIntras);
    }

    /* Strategy:
     * Recursive depth-first search.
     * Begin at each intra without predecessor. Visit all of its direct and indirect successors.
     * Add all unvisited intras to the current inter. Visiting an already visited intra means that it already has an inter.
     * Then, merge the current inter with the found inter and propagate the found inter back through the recursion.
     */
    public Set<InterVersionCd> buildInterVersionSmells()
    {
        Set<ProtoCdInter> intersTemp = buildProtoInters();
        return buildIntersCore(intersTemp);
    }

    private Set<ProtoCdInter> buildProtoInters()
    {
        Set<ProtoCdInter> protoInters = new HashSet<>();
        for (IntraVersionCd startIntra : smellsWOPredecessor)
        {
            ProtoCdInter inter = new ProtoCdInter();
            inter = visit(startIntra, inter);
            protoInters.add(inter);
            inter.firstVersionId = Math.min(inter.firstVersionId, startIntra.getVersionId());
        }
        return protoInters;
    }

    private Set<InterVersionCd> buildIntersCore(Set<ProtoCdInter> intersTemp)
    {
        Set<InterVersionCd> inters = CollectionUtils.initHashSet(intersTemp.size());
        for (ProtoCdInter protoInter : intersTemp)
        {
            if (protoInter.intras.size() > 0)
            {
                int versionCount = protoInter.getVersionCount();
                List<Set<IntraVersionCd>> intrasSetList = new ArrayList<>(versionCount);
                for (int i = 0; i < versionCount; i++)
                {
                    intrasSetList.add(new HashSet<>());
                }
                int firstVersion = protoInter.firstVersionId;
                for (IntraVersionCd intra : protoInter.intras)
                {
                    int index = intra.getVersionId() - firstVersion;
                    intrasSetList.get(index).add(intra);
                }
                inters.add(new InterVersionCd(firstVersion, intrasSetList));
            }
        }
        return inters;
    }

    private ProtoCdInter visit(IntraVersionCd intra, ProtoCdInter currentInter)
    {
        IntraId intraId = intra.getIntraId();
        if (visited.contains(intra))
        {
            ProtoCdInter existingInter = interAssigns.get(intraId);
            existingInter.intras.addAll(currentInter.intras);
            for (IntraVersionCd intraToReassign : currentInter.intras)
            {
                interAssigns.put(intraToReassign.getIntraId(), existingInter);
            }
            currentInter.intras.clear();
            currentInter = existingInter;
        }
        else
        {
            visited.add(intra);
            currentInter.intras.add(intra);
            interAssigns.put(intraId, currentInter);
            if (mappingsOldAsKey.containsKey(intraId))
            {
                for (IntraVersionCd intraNew : mappingsOldAsKey.get(intraId))
                {
                    currentInter = visit(intraNew, currentInter);
                }
            }
            currentInter.lastVersionId = Math.max(currentInter.lastVersionId, intra.getVersionId());
        }
        return currentInter;
    }

    private static class ProtoCdInter
    {
        Set<IntraVersionCd> intras = new HashSet<>();
        int firstVersionId = MathUtils.INFINITY;
        int lastVersionId = 0;

        int getVersionCount()
        {
            return lastVersionId - firstVersionId + 1;
        }
    }
}
