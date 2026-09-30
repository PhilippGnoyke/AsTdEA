package org.astdea.data.versions.initialising;

import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;

abstract class SmellTypeInitHelper<IntraType extends IntraVersionSmell>
{
    protected VersionCompManager versionCompManager;

    public SmellTypeInitHelper(VersionCompManager versionCompManager)
    {
        this.versionCompManager = versionCompManager;
    }

    abstract String getCompsFile();

    abstract String getPropsFile();

    abstract String[] getCompsHeaders();

    abstract String[] getPropsHeaders();

    abstract IntraType initIntra(CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank, int order, int size);
}
