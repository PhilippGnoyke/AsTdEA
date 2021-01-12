package org.astdea.io.input;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.regex.Pattern;

// From https://stackoverflow.com/a/48946673

public final class FilenameComparator implements Comparator<String>
{
    private static final Pattern NUMBERS =
        Pattern.compile("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    @Override
    public final int compare(String o1, String o2)
    {
        // Optional "NULLS LAST" semantics:
        if (o1 == null || o2 == null)
        { return o1 == null ? o2 == null ? 0 : -1 : 1; }
        // Splitting both input strings by the above patterns
        String[] split1 = NUMBERS.split(o1);
        String[] split2 = NUMBERS.split(o2);
        for (int i = 0; i < Math.min(split1.length, split2.length); i++)
        {
            char c1 = split1[i].charAt(0);
            char c2 = split2[i].charAt(0);
            int cmp = 0;
            // If both segments start with a digit, sort them numerically using
            // BigInteger to stay safe
            if (c1 >= '0' && c1 <= '9' && c2 >= '0' && c2 <= '9')
            { cmp = new BigInteger(split1[i]).compareTo(new BigInteger(split2[i])); }
            // If we haven't sorted numerically before, or if numeric sorting yielded
            // equality (e.g 007 and 7) then sort lexicographically
            if (cmp == 0)
            { cmp = split1[i].compareTo(split2[i]); }
            // Abort once some prefix has unequal ordering
            if (cmp != 0)
            { return cmp; }
        }
        // If we reach this, then both strings have equally ordered prefixes, but
        // maybe one string is longer than the other (i.e. has more segments)
        return split1.length - split2.length;
    }
}