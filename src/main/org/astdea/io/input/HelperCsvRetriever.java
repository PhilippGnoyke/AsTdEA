package org.astdea.io.input;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public interface HelperCsvRetriever<Type>
{
    Type[] instantiateArray(int rows);

    Type parseValue(String input);
}
