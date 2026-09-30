package org.astdea.data;

import org.astdea.data.smells.Level;

public enum AffType {
    CCD,
    PCD,
    HDC,
    HDAE,
    UDC,
    UDAE;

    public static Level getLevel(AffType affType) {
        return switch (affType) {
            case CCD -> Level.CLASS;
            case HDC -> Level.CLASS;
            case HDAE -> Level.CLASS;
            default -> Level.PACK;
        };
    }

    public static boolean isClassLevel(AffType affType) {
        return getLevel(affType) == Level.CLASS;
    }

    public static AffType getCdAffTypeFromLevel(Level level) {
        return level == Level.CLASS ? CCD : PCD;
    }




}


