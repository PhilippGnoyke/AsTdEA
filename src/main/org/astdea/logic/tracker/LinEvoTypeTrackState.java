package org.astdea.logic.tracker;

import org.astdea.data.smells.intraversionsmells.IntraVersionLinEvoType;

import java.util.List;

public class LinEvoTypeTrackState<IntraType extends IntraVersionLinEvoType>
{
    private int ind;
    private List<IntraType> intrasList;
    private IntraType intra;
    private String mainComp;

    public LinEvoTypeTrackState(List<IntraType> intrasList)
    {
        this.intrasList = intrasList;
        this.intra = intrasList.get(0);
        this.mainComp = intra.getMainComp();
    }

    public int getInd() {return ind;}

    public String getMainComp() {return mainComp;}

    public List<IntraType> getIntrasList() {return intrasList;}

    public IntraType getIntra() {return intra;}

    public boolean update()
    {
        ind++;
        if (ind >= intrasList.size())
        {
            return true;
        }
        intra = intrasList.get(ind);
        mainComp = intra.getMainComp();
        return false;
    }
}
