package org.astdea.data.versions;

import org.astdea.io.input.DateReader;
import org.astdea.utils.MathUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public final class VersionTimeManager
{
    private VersionTimeManager() {}

    private static LocalDate[] versionTimes;
    private static double[] timeSpans;
    private static int analysedTimeSpanInVersions;
    private static int analysedTimeSpanInDays;

    public static void init(String inDir, int analysedVersions) throws IOException
    {
        analysedTimeSpanInVersions = analysedVersions;
        versionTimes = DateReader.retrieveDates(inDir, analysedVersions);
        timeSpans = new double[analysedVersions];
        for (int i = 0; i < analysedVersions - 1; i++)
        {
            timeSpans[i] = Period.between(versionTimes[i], versionTimes[i + 1]).getDays();
        }
        double median = MathUtils.median(timeSpans, 0, analysedVersions - 1);
        versionTimes[analysedVersions] = versionTimes[analysedVersions - 1].plusDays((int) median);
        timeSpans[analysedVersions - 1] = median;
        analysedTimeSpanInDays = Period.between(versionTimes[0], versionTimes[analysedVersions]).getDays() + (int) median;
    }

    public static LocalDate getVersionTime(int ind) {return versionTimes[ind];}

    public static int getTimeSpan(int ind) {return (int) timeSpans[ind];}

    public static int getAnalysedTimeSpanInVersions() {return analysedTimeSpanInVersions;}

    public static int getAnalysedTimeSpanInDays() {return analysedTimeSpanInDays;}
}
