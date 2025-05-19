package astdea.data.smells;

import org.astdea.data.smells.interversionsmells.TimeManager;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class Test_intraversionsmells
{
    @Test
    public void test_TimeManager()
    {
        final LocalDate DATE0 = LocalDate.of(2020,1,1);
        final LocalDate DATE1 = LocalDate.of(2020,1,11);
        final LocalDate DATE2 = LocalDate.of(2020,2,1);
        final LocalDate DATE3 = LocalDate.of(2020,2,21);
        final LocalDate DATE4 = LocalDate.of(2021,1,1);

        LocalDate[] versionTimes = new LocalDate[]{DATE0,DATE1,DATE2,DATE3,DATE4};
        TimeManager timeManager = new TimeManager(versionTimes);

        assertEquals(DATE0,timeManager.getVersionTime(0));
        assertEquals(DATE2,timeManager.getVersionTime(2));
        assertEquals(DATE4,timeManager.getVersionTime(4));

        assertEquals(10,timeManager.getTimeSpan(0));
        assertEquals(20,timeManager.getTimeSpan(2));

        assertEquals(versionTimes.length,timeManager.getAnalysedTimeSpanInVersions());

        final int TOTAL_DAYS = 386;
        assertEquals(TOTAL_DAYS,timeManager.getAnalysedTimeSpanInDays());
    }
}
