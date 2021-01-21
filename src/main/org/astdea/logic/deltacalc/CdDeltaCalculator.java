package org.astdea.logic.deltacalc;

import org.astdea.data.smells.interversionsmells.InterVersionCd;
import org.astdea.data.smells.intraversionsmells.IntraVersionCd;
import org.astdea.logic.mapping.CdMappings;
import org.astdea.utils.MathUtils;
import org.psjava.algo.graph.matching.HopcroftKarpAlgorithm;
import org.psjava.algo.graph.matching.MaximumBipartiteMatchingResult;
import org.psjava.ds.graph.MutableBipartiteGraph;

import java.util.List;
import java.util.Set;

public class CdDeltaCalculator
{
    private CdMappings mappings;
    private Set<InterVersionCd> inters;
    private Deltas deltas;

    public CdDeltaCalculator(CdMappings mappings, Set<InterVersionCd> inters, int analysedVersions)
    {
        this.mappings = mappings;
        this.inters = inters;
        deltas = new Deltas(analysedVersions);
    }

    public Deltas calc()
    {
        initIntrosForIntrasWOPred();
        for (InterVersionCd inter : inters)
        {
            List<Set<IntraVersionCd>> intrasOfInter = inter.getIntraVersionSmells();
            for (int versionId = 0; versionId < intrasOfInter.size() - 1; versionId++)
            {
                initWeightsInVersion(intrasOfInter, versionId);
                MutableBipartiteGraph<IntraVersionCd> graph = buildInterBiparGraph(intrasOfInter, versionId);
                updateIntrosForIntrasAfterSplit(versionId, graph);
                transferWeights(graph);
            }
            // Not necessary for calculations, but for correct printing results in interVersion/xCdComponents.csv
            initWeightsInVersion(intrasOfInter, intrasOfInter.size() - 1);
        }
        calcRemovs();
        return deltas;
    }

    private void initIntrosForIntrasWOPred()
    {
        for (IntraVersionCd intra : mappings.getSmellsWOPredecessor())
        {
            deltas.incrIntros(intra.getVersionId());
        }
    }

    private void initWeightsInVersion(List<Set<IntraVersionCd>> intrasOfInter, int versionId)
    {
        for (IntraVersionCd intra : intrasOfInter.get(versionId))
        {
            intra.initRemovalWeight();
        }
    }

    private void updateIntrosForIntrasAfterSplit(int versionId, MutableBipartiteGraph<IntraVersionCd> graph)
    {
        int deltaConnectedSmells = graph.getRightVertices().size() - graph.getLeftVertices().size();
        if (deltaConnectedSmells > 0)
        {
            deltas.addIntros(versionId + 1, deltaConnectedSmells);
        }
    }

    private MutableBipartiteGraph<IntraVersionCd> buildInterBiparGraph
        (List<Set<IntraVersionCd>> intrasInVersion, int versionId)
    {
        MutableBipartiteGraph<IntraVersionCd> graph = MutableBipartiteGraph.create();
        for (IntraVersionCd intraA : intrasInVersion.get(versionId))
        {
            if (mappings.doesSmellHaveSuccessor(intraA))
            {
                graph.insertLeftVertex(intraA);
            }
        }
        for (IntraVersionCd intraB : intrasInVersion.get(versionId + 1))
        {
            if (mappings.doesSmellHavePredecessor(intraB))
            {
                graph.insertRightVertex(intraB);
                Set<IntraVersionCd> edges = mappings.getByNewIntra(intraB);
                for (IntraVersionCd intraA : edges)
                {
                    graph.addEdge(intraA, intraB);
                }
            }
        }
        return graph;
    }

    private void calcRemovs()
    {
        for (IntraVersionCd intra : mappings.getSmellsWOSuccessor())
        {
            deltas.addRemovs(intra.getVersionId()+1, intra.getRemovalWeight());
        }
    }

    private void transferWeights(MutableBipartiteGraph<IntraVersionCd> graph)
    {
        MaximumBipartiteMatchingResult<IntraVersionCd> maxCardSubset = HopcroftKarpAlgorithm.getInstance().calc(graph);
        for (IntraVersionCd intraA : graph.getLeftVertices())
        {
            int restWeightA = intraA.getRemovalWeight();
            if (maxCardSubset.hasMatch(intraA))
            {
                restWeightA--;
            }
            if (restWeightA > 0)
            {
                transferRemainWeight(intraA, restWeightA);
            }
        }
    }

    private void transferRemainWeight(IntraVersionCd intraA, int restWeightA)
    {
        Set<IntraVersionCd> edges = mappings.getByOldIntra(intraA);
        IntraVersionCd mostSimilar = null;
        double jaccMax = 0;
        for (IntraVersionCd intraB : edges)
        {
            double jacc = MathUtils.jaccard(intraA.getComps(), intraB.getComps());
            if (jacc > jaccMax)
            {
                jaccMax = jacc;
                mostSimilar = intraB;
            }
            else if (jacc == jaccMax)
            {
                assert mostSimilar != null;
                double prDiffCurrent = Math.abs(intraA.getPageRank() - mostSimilar.getPageRank());
                double prDiffOther = Math.abs(intraA.getPageRank() - intraB.getPageRank());
                if (prDiffOther < prDiffCurrent)
                {
                    mostSimilar = intraB;
                }
            }
        }
        assert mostSimilar != null;
        mostSimilar.addRemovalWeight(restWeightA);
    }
}
