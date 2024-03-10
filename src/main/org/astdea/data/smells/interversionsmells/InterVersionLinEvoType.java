package org.astdea.data.smells.interversionsmells;

import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;

import java.util.List;

public abstract class InterVersionLinEvoType<IntraType extends IntraVersionLinEvoType>
    extends InterVersionSmell<List<IntraType>>
{
    public InterVersionLinEvoType(int versionOfIntroduction, List<IntraType> intraVersionSmells)
    {
        super(versionOfIntroduction, intraVersionSmells);
        setAges();
    }

    private void setAges()
    {
        for(int i = 0;i<intraVersionSmells.size();i++)
        {
            IntraType intra = intraVersionSmells.get(i);
            intra.setAge(i);
            intra.setRemainingAge(intraVersionSmells.size()-1-i);
        }
    }
}
