package org.astdea.data.versions.initialising;

import org.apache.commons.csv.CSVRecord;
import org.astdea.data.smells.intraversionsmells.IntraVersionSmell;

interface SmellTypeInitHelper<IntraType extends IntraVersionSmell>
{
    String getCompsFile();

    String getPropsFile();

    String[] getCompsHeaders();

    String[] getPropsHeaders();

    IntraType initIntra(CSVRecord compRecord, CSVRecord propRecord, int versionId, int smellId, double pageRank);
}
