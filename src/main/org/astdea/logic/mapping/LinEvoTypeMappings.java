package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class LinEvoTypeMappings<IntraType extends IntraVersionLinEvoType,
    InterType extends InterVersionLinEvoType> extends Mappings<IntraType, InterType, IntraType>
{
    protected void putMapping(IntraType smellOld, IntraType smellNew)
    {
        mappingsOldAsKey.put(smellOld.getIntraId(), smellNew);
        mappingsNewAsKey.put(smellNew.getIntraId(), smellOld);
    }

    public Set<InterType> buildInterVersionSmells()
    {
        Set<InterType> inters = new HashSet<>();
        for (IntraType startIntra : smellsWOPredecessor)
        {
            List<IntraType> path = new ArrayList<>();
            path.add(startIntra);
            IntraType intra = startIntra;
            IntraId intraId;
            do
            {
                intraId = intra.getIntraId();
                intra = mappingsOldAsKey.get(intraId);
                path.add(intra);
            }
            while (doesSmellHaveSuccessor(intra));
            inters.add(instantiateInter(startIntra.getVersionId(), path));
        }
        return inters;
    }

    protected abstract InterType instantiateInter(int versionOfIntroduction, List<IntraType> intraVersionSmells);
}
