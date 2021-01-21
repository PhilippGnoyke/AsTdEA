package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.logic.interbuilding.InterVersionCdBuilder;

import java.util.HashSet;
import java.util.Set;

public class CdMappings extends Mappings<IntraVersionCd, InterVersionCd, Set<IntraVersionCd>>
{
    private int totalNumOfIntras;

    public CdMappings(int totalNumOfIntras) {this.totalNumOfIntras = totalNumOfIntras;}

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
        return new InterVersionCdBuilder(totalNumOfIntras, mappingsOldAsKey, smellsWOPredecessor)
            .buildInterVersionSmells();
    }
}
