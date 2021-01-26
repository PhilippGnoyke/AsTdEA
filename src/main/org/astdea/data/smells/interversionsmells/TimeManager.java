package org.astdea.data.smells.interversionsmells;

import org.astdea.io.input.DatesReader;
import org.astdea.utils.MathUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class TimeManager
{
    private TimeManager() {}

    private static LocalDate[] versionTimes;
    private static double[] timeSpans;
    private static int analysedTimeSpanInVersions;
    private static int analysedTimeSpanInDays;

    public static void initFromFile(String inDir, int analysedVersions) throws IOException
    {
        versionTimes = DatesReader.retrieveDates(inDir, analysedVersions);
        calcAll(analysedVersions);
    }

    public static void initManually(LocalDate[] newVersionTimes)
    {
        int analysedVersions = newVersionTimes.length;
        versionTimes = new LocalDate[analysedVersions+1];
        System.arraycopy(newVersionTimes, 0, versionTimes, 0, newVersionTimes.length);
        calcAll(versionTimes.length-1);
    }

    private static void calcAll(int analysedVersions)
    {
        analysedTimeSpanInVersions = analysedVersions;
        timeSpans = new double[analysedVersions];
        for (int i = 0; i < analysedVersions - 1; i++)
        {
            timeSpans[i] = (int) ChronoUnit.DAYS.between(versionTimes[i], versionTimes[i + 1]);
        }
        double median = MathUtils.median(timeSpans, 0, analysedVersions - 1);
        versionTimes[analysedVersions] = versionTimes[analysedVersions - 1].plusDays((int) median);
        timeSpans[analysedVersions - 1] = median;
        analysedTimeSpanInDays = (int) ChronoUnit.DAYS.between(versionTimes[0], versionTimes[analysedVersions]);
    }

    public static LocalDate getVersionTime(int ind) {return versionTimes[ind];}

    public static int getTimeSpan(int ind) {return (int) timeSpans[ind];}

    public static int getAnalysedTimeSpanInVersions() {return analysedTimeSpanInVersions;}

    public static int getAnalysedTimeSpanInDays() {return analysedTimeSpanInDays;}
}
