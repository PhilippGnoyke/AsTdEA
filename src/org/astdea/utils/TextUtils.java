package org.astdea.utils;

public final class TextUtils {

    private TextUtils() {}

    public static String wrap(String in, int len)
    {
        String[] parts = in.split(" ");
        StringBuilder result = new StringBuilder();
        int lineLen = 0;
        for (int i=0;i<parts.length;i++)
        {
            String word = parts[i];
            int wordLen = word.length();
            if(i>0)
            {
                if(lineLen+wordLen>len)
                {
                    result.append("\n");
                    lineLen=0;
                }
                else
                {
                    result.append(" ");
                }
            }
            lineLen+=wordLen;
            result.append(word);
        }
    return result.toString();
    }
}
