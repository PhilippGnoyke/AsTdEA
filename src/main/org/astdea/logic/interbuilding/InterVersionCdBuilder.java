package org.astdea.logic.interbuilding;

import org.astdea.data.graphelements.MergeSplit;
import org.astdea.data.graphelements.Transition;
import org.astdea.data.graphelements.TransitionType;
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
    protected Map<IntraId, Set<IntraVersionCd>> mappingsNewAsKey;
    protected Set<IntraVersionCd> smellsWOPredecessor;
    protected Set<IntraVersionCd> smellsWOSuccessor;

    public InterVersionCdBuilder(int totalNumOfIntras,
                                 Map<IntraId, Set<IntraVersionCd>> mappingsOldAsKey,
                                 Map<IntraId, Set<IntraVersionCd>> mappingsNewAsKey,
                                 Set<IntraVersionCd> smellsWOPredecessor,
                                 Set<IntraVersionCd> smellsWOSuccessor)
    {
        this.mappingsOldAsKey = mappingsOldAsKey;
        this.mappingsNewAsKey = mappingsNewAsKey;
        this.smellsWOPredecessor = smellsWOPredecessor;
        this.smellsWOSuccessor = smellsWOSuccessor;
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
                setAges(intrasSetList);
                ProtoTransitions ts = buildTransitionsOfInter(intrasSetList, protoInter.intras.size());
                InterVersionCd cd = new InterVersionCd(firstVersion, intrasSetList,ts.transitions,ts.merges,ts.splits);
                cd.setTransitionCounts(ts.pure,ts.mergeOnly,ts.splitOnly,ts.mergeAndSplit);
                inters.add(cd);

            }
        }
        return inters;
    }

    private void setAges(List<Set<IntraVersionCd>> intrasSetList)
    {
        for (Set<IntraVersionCd> intras: intrasSetList)
        {
            for (IntraVersionCd intra : intras)
            {
                setAge(intra);
            }
        }
        for (int i = intrasSetList.size() - 1; i >= 0; i--) {
            Set<IntraVersionCd> intras =  intrasSetList.get(i);
            for (IntraVersionCd intra : intras)
            {
                setRemainingAge(intra);
            }
        }
    }

    private void setAge(IntraVersionCd intra)
    {
        if(!smellsWOPredecessor.contains(intra))
        {
            int ageMax = 0;
            Set<IntraVersionCd> predecessors = mappingsNewAsKey.get(intra.getIntraId());
            intra.addPreds(predecessors);
            for (IntraVersionCd predecessor : predecessors)
            {
                ageMax = Math.max(ageMax,predecessor.getAge());
                intra.addPreds(predecessor.getAllPreds());
            }
            intra.setNumDirectPreds(predecessors.size());
            intra.setAge(ageMax+1);
        }
    }

    private void setRemainingAge(IntraVersionCd intra)
    {
        if(!smellsWOSuccessor.contains(intra))
        {
            int ageMax = 0;
            Set<IntraVersionCd> successors = mappingsOldAsKey.get(intra.getIntraId());
            intra.addSuccs(successors);
            for (IntraVersionCd successor : successors)
            {
                ageMax = Math.max(ageMax,successor.getRemainingAge());
                intra.addSuccs(successor.getAllSuccs());
            }
            intra.setNumDirectSuccs(successors.size());
            intra.setRemainingAge(ageMax+1);
        }
    }

    private ProtoTransitions buildTransitionsOfInter(List<Set<IntraVersionCd>> intrasSetList, int intraCount)
    {
        ProtoTransitions transitions = new ProtoTransitions(intraCount);
        ProtoMergesSplits protoMerges = new ProtoMergesSplits();
        ProtoMergesSplits protoSplits = new ProtoMergesSplits();
        for (Set<IntraVersionCd> intrasSet : intrasSetList)
        {
            for (IntraVersionCd source : intrasSet)
            {
                if (!smellsWOSuccessor.contains(source))
                {
                    Set<IntraVersionCd> targets = mappingsOldAsKey.get(source.getIntraId());

                    int targetCount = targets.size();
                    for (IntraVersionCd target : targets)
                    {
                        int sourceCount = mappingsNewAsKey.get(target.getIntraId()).size();
                        TransitionType transitionType = TransitionType.getTransitionType(sourceCount, targetCount);
                        transitions.add(new Transition(source, target, transitionType));
                        if (targetCount > 1) { protoSplits.add(source,target); }
                        if (sourceCount > 1) { protoMerges.add(target,source); }
                    }
                }
            }
        }
        transitions.merges = buildMergesOrSplitsOfInter(protoMerges);
        transitions.splits = buildMergesOrSplitsOfInter(protoSplits);
        return transitions;
    }

    private Set<MergeSplit> buildMergesOrSplitsOfInter(ProtoMergesSplits protoMergesSplits)
    {
        Set<MergeSplit> mergesOrSplits = new HashSet<>();
        for (IntraVersionCd targetOrSource : protoMergesSplits.protos.keySet())
        {
            Set<IntraVersionCd> targetsOrSources = protoMergesSplits.protos.get(targetOrSource);
            mergesOrSplits.add(new MergeSplit(targetOrSource,targetsOrSources));
        }
        return mergesOrSplits;
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


    private static class ProtoMergesSplits
    {
        Map<IntraVersionCd,Set<IntraVersionCd>> protos = new HashMap<>();

        void add(IntraVersionCd key, IntraVersionCd val)
        {
            if(protos.containsKey(key))
            {
                protos.get(key).add(val);
            }
            else
            {
                Set<IntraVersionCd> vals = new HashSet<>();
                vals.add(val);
                protos.put(key,vals);
            }
        }
    }

    private static class ProtoTransitions
    {
        // Optimization strategy:
        // Even in complex families, most transitions are pure.
        // Merges usually originate from pure branches, while splits usually result in pure branches.
        // The overall number of transitions will probably not exceed the number of intra-version smells.
        Set<Transition> transitions;
        Set<MergeSplit> merges = new HashSet<>();
        Set<MergeSplit> splits = new HashSet<>();

        int pure = 0;
        int mergeOnly = 0;
        int splitOnly = 0;
        int mergeAndSplit = 0;

        public ProtoTransitions(int intraCount)
        {
            transitions = new HashSet<>(intraCount);
        }

        public void add(Transition transition)
        {
            transitions.add(transition);
            switch (transition.getTransitionType())
            {
                case PURE -> pure++;
                case MERGE_ONLY -> mergeOnly++;
                case SPLIT_ONLY -> splitOnly++;
                case MERGE_AND_SPLIT -> mergeAndSplit++;
            }
        }
    }

}
