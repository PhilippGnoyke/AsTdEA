package org.astdea.io.input;

import it.unimib.disco.essere.main.AsTdEvolutionPrinter;
import it.unimib.disco.essere.main.graphmanager.GraphBuilder;
import it.unimib.disco.essere.main.metricsengine.ProjectMetricsCalculator;

// IPN = InPropertyNames
public final class IPN
{
    private IPN() {}

    public static final String DATE = "date";
    public static final String VERSION = "version";

    public static final String VERSION_OLD = "versionOld";
    public static final String VERSION_NEW = "versionNew";
    public static final String FQCN_OLD = "fqcnOld";
    public static final String FQCN_NEW = "fqcnNew";

    public static final String ID = AsTdEvolutionPrinter.ID;
    public static final String LOC = ProjectMetricsCalculator.PROPERTY_LOC;
    public static final String CLASS_COUNT = ProjectMetricsCalculator.PROPERTY_INT_CLASS_COUNT;
    public static final String PACK_COUNT = ProjectMetricsCalculator.PROPERTY_INT_PACK_COUNT;
    public static final String ORDER = GraphBuilder.PROPERTY_ORDER;
    public static final String SIZE = GraphBuilder.PROPERTY_SIZE;
    public static final String NUM_SUBCYCLES = GraphBuilder.PROPERTY_NUM_SUBCYCLES;
    public static final String MFES_SIZE = GraphBuilder.PROPERTY_MFES_SIZE;
    public static final String MFES_WO_TINYS_SIZE = GraphBuilder.PROPERTY_MFES_SIZE_WO_TINYS;
    public static final String SHAPE = GraphBuilder.PROPERTY_SHAPE;
    public static final String CENTRALITY = GraphBuilder.PROPERTY_CENTRALITY;
    public static final String AFFECTED_COMPS = AsTdEvolutionPrinter.AFFECTED_COMPS;
    public static final String MAIN_COMP = AsTdEvolutionPrinter.MAIN_COMP;
    public static final String AFF_COMPS = AsTdEvolutionPrinter.AFFERENT_CLASSES;
    public static final String EFF_COMPS = AsTdEvolutionPrinter.EFFERENT_CLASSES;
    public static final String LESS_STABLE_PACKS = AsTdEvolutionPrinter.LESS_STABLE_PACKS;

}
