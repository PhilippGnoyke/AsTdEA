package org.astdea.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public final class CollectionUtils
{
    private CollectionUtils() {}

    public final static float HASH_LOAD_FACTOR = 0.75f;

    public static int calcCapacity(int numElements)
    {
        return (int) (numElements / HASH_LOAD_FACTOR + 1);
    }

    public static <KeyType, ValType> HashMap<KeyType, ValType> initHashMap(int numElements)
    {
        return new HashMap<>(calcCapacity(numElements), HASH_LOAD_FACTOR);
    }

    public static <ValType> HashSet<ValType> initHashSet(int numElements)
    {
        return new HashSet<>(calcCapacity(numElements), HASH_LOAD_FACTOR);
    }

    public static String[] mergeStringArrays(String[]... arrays)
    {
        return Stream.of(arrays).flatMap(Stream::of).toArray(String[]::new);
    }
}
