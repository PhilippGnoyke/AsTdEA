package org.astdea.logic.mapping;

import org.astdea.data.smells.interversionsmells.InterVersionSmell;
import org.astdea.data.smells.intraversionsmells.IntraId;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Mappings<IntraType extends IntraVersionSmell, InterType extends InterVersionSmell, MappingType>
{
    protected Map<IntraId, MappingType> mappingsOldAsKey; // All outgoing mappings from the intra
    protected Map<IntraId, MappingType> mappingsNewAsKey; // All incoming mappings to the intra
    protected Set<IntraType> smellsWOPredecessor;
    protected Set<IntraType> smellsWOSuccessor;

    /*
     * Potential for optimising:
     * Retrieve how many mappings are expected to arise from a given number of intra-version smells.
     * Init the map and set sizes to a corresponding value.
     */
    public Mappings()
    {
        this.mappingsOldAsKey = new HashMap<>();
        this.mappingsNewAsKey = new HashMap<>();
        this.smellsWOPredecessor = new HashSet<>();
        this.smellsWOSuccessor = new HashSet<>();
    }

    public abstract Set<InterType> buildInterVersionSmells();

    protected abstract void putMapping(IntraType smellOld, IntraType smellNew);

    public Set<IntraType> getSmellsWOPredecessor() {return smellsWOPredecessor;}

    public Set<IntraType> getSmellsWOSuccessor() {return smellsWOSuccessor;}

    public MappingType getByOldId(IntraId intraId) {return mappingsOldAsKey.get(intraId);}

    public MappingType getByNewId(IntraId intraId) {return mappingsNewAsKey.get(intraId);}

    public MappingType getByOldIntra(IntraType intra) {return getByOldId(intra.getIntraId());}

    public MappingType getByNewIntra(IntraType intra) {return getByNewId(intra.getIntraId());}

    public boolean doesSmellHaveSuccessor(IntraType smell) {return contains(mappingsOldAsKey, smell);}

    public boolean doesSmellHavePredecessor(IntraType smell) {return contains(mappingsNewAsKey, smell);}

    private boolean contains(Map<IntraId, MappingType> mappings, IntraType smell)
    {
        return mappings.containsKey(smell.getIntraId());
    }

    public void put(IntraType smellOld, IntraType smellNew)
    {
        putMapping(smellOld, smellNew);
        if (!doesSmellHavePredecessor(smellOld))
        {
            smellsWOPredecessor.add(smellOld);
        }
        if (!doesSmellHaveSuccessor(smellNew))
        {
            smellsWOSuccessor.add(smellNew);
        }
        smellsWOPredecessor.remove(smellNew);
        smellsWOSuccessor.remove(smellOld);
    }

    public void addToSmellsWOPredecessor(Set<IntraType> remaining)
    {
        smellsWOPredecessor.addAll(remaining);
    }

    public void addToSmellsWOSuccessor(Set<IntraType> remaining)
    {
        smellsWOSuccessor.addAll(remaining);
    }


}
