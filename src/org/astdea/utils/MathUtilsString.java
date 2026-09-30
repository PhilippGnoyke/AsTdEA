package org.astdea.utils;

public class MathUtilsString extends MathUtils<String>{

    private MathUtilsString() {}

    private static MathUtilsString instance;

    public static MathUtilsString getInstance() {
        if (instance == null) {
            instance = new MathUtilsString();
        }
        return instance;
    }

}
