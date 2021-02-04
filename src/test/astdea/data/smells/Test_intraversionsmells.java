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
        TimeManager.initManually(versionTimes);

        assertEquals(DATE0,TimeManager.getVersionTime(0));
        assertEquals(DATE2,TimeManager.getVersionTime(2));
        assertEquals(DATE4,TimeManager.getVersionTime(4));

        assertEquals(10,TimeManager.getTimeSpan(0));
        assertEquals(20,TimeManager.getTimeSpan(2));

        assertEquals(versionTimes.length,TimeManager.getAnalysedTimeSpanInVersions());

        final int TOTAL_DAYS = 386;
        assertEquals(TOTAL_DAYS,TimeManager.getAnalysedTimeSpanInDays());
    }
}
