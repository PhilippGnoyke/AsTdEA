package org.astdea.io.input;

public interface HelperCsvRetriever<Type>
{
    Type[] instantiateArray(int rows);

    Type parseValue(String input);
}
