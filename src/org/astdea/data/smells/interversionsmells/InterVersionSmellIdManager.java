package org.astdea.data.smells.interversionsmells;

public final class InterVersionSmellIdManager
{
    private InterVersionSmellIdManager() {}

    private static int interCount = 0;

    public static int assignId() {return interCount++;}
}
