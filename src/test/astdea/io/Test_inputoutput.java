package astdea.io;

import org.astdea.io.inputoutput.IOUtils;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_inputoutput
{
    @Test
    public void parseStringToSet()
    {
        final String DEL = ",";
        final String PT1 = "aaa";
        final String PT2 = "b";
        final String PT3 = "c.c";
        final String PT4 = "e35";
        final String PT5 = "";
        final String PT6 = "1f";
        final String[] EXPECTED_STRINGS = {PT1,PT2,PT3,PT4,PT5,PT6};
        final String TO_PARSE = PT1 + DEL + PT2 + DEL + PT3 + DEL + PT4 + DEL + PT5 + DEL + PT6;
        Set<String> actualStrings = IOUtils.parseStringToSet(TO_PARSE,DEL);
        for(final String EXPECTED : EXPECTED_STRINGS)
        {
            assertTrue(actualStrings.contains(EXPECTED));
        }

    }


}
