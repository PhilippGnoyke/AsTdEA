package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.interversionsmells.TimeManager;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.logic.interbuilding.InterVersionCdBuilder;

import java.util.HashSet;
import java.util.Set;

public class CdMappings extends Mappings<IntraVersionCd, InterVersionCd, Set<IntraVersionCd>>
{
    private int totalNumOfIntras;

    public CdMappings(TimeManager timeManager, int totalNumOfIntras)
    {
        super(timeManager);
        this.totalNumOfIntras = totalNumOfIntras;
    }

    @Override
    protected void putMapping(IntraVersionCd smellOld, IntraVersionCd smellNew)
    {
        IntraId oldId = smellOld.getIntraId();
        IntraId newId = smellNew.getIntraId();
        Set<IntraVersionCd> setOld = mappingsOldAsKey.get(oldId);
        Set<IntraVersionCd> setNew = mappingsNewAsKey.get(newId);
        if (setOld == null)
        {
            setOld = new HashSet<>();
        }
        if (setNew == null)
        {
            setNew = new HashSet<>();
        }
        setOld.add(smellNew);
        setNew.add(smellOld);
        mappingsOldAsKey.put(oldId, setOld);
        mappingsNewAsKey.put(newId, setNew);
    }

    @Override
    public Set<InterVersionCd> buildInterVersionSmells()
    {
        return new InterVersionCdBuilder(totalNumOfIntras, mappingsOldAsKey,mappingsNewAsKey,
            smellsWOPredecessor,smellsWOSuccessor,timeManager).buildInterVersionSmells();
    }

    private void setSmellAges()
    {
        // Strategy: Start on nodes without predecessor Vc; age(Vc)=0
        // For every successor: if the successor Vs only has a single predecessor (the current node Vc), go there, age(Vs)=age(Vc)+1
        // If Vs has multiple predecessors, put the successor in a set and finish the other initial branches
        // Then, for every node Vc in the set, determine the max of the ages in the predecessors Vps: age(Vc) = max(age(Vps))+1
        // Continue with the same strategy from there
        Set<IntraVersionCd> multiplePreds = new HashSet<>();
        for (IntraVersionCd intra : smellsWOPredecessor)
        {
            intra.setAge(0);
        }
        //TODO
    }

    private void setSmellAgeInSuccessors(IntraVersionCd intra)
    {
        for (IntraVersionCd successor : mappingsOldAsKey.get(intra))
        {
            if (mappingsNewAsKey.get(successor).size()==1)
            {
                successor.setAge(intra.getAge()+1);
            }
            else
            {

            }
        }
    }
}
