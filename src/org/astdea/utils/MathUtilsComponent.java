package org.astdea.utils;

import org.astdea.data.versions.Component;

public class MathUtilsComponent extends MathUtils<Component> {

    private MathUtilsComponent() {}

    private static MathUtilsComponent instance;

    public static MathUtilsComponent getInstance() {
        if (instance == null) {
            instance = new MathUtilsComponent();
        }
        return instance;
    }

}

