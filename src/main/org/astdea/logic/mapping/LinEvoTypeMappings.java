package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionLinEvoType;
import org.astdea.data.smells.interversionsmells.TimeManager;
import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;

import java.sql.Time;
import java.util.*;

public abstract class LinEvoTypeMappings<IntraType extends IntraVersionLinEvoType,
    InterType extends InterVersionLinEvoType> extends Mappings<IntraType, InterType, IntraType>
{
    public LinEvoTypeMappings(TimeManager timeManager) { super(timeManager); }

    @Override
    protected void putMapping(IntraType smellOld, IntraType smellNew)
    {
        mappingsOldAsKey.put(smellOld.getIntraId(), smellNew);
        mappingsNewAsKey.put(smellNew.getIntraId(), smellOld);
    }

    @Override
    public Set<InterType> buildInterVersionSmells()
    {
        Set<InterType> inters = new HashSet<>();
        for (IntraType startIntra : smellsWOPredecessor)
        {
            List<IntraType> path = new ArrayList<>();
            path.add(startIntra);
            IntraType intra = startIntra;
            while (doesSmellHaveSuccessor(intra))
            {
                intra = mappingsOldAsKey.get(intra.getIntraId());
                path.add(intra);
            }

            inters.add(instantiateInter(startIntra.getVersionId(), path));
        }
        return inters;
    }

    protected abstract InterType instantiateInter(int versionOfIntroduction, List<IntraType> intraVersionSmells);
}
