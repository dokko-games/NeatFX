package com.dokko.win4jui.api.element;

import lombok.Getter;

@Getter
public enum Anchors {

    TOP_LEFT(0x0101), TOP_CENTER(0x0102), TOP_RIGHT(0x0103), TOP_FIX(0x0104), TOP_SCALED(0x0105),
    CENTER_LEFT(0x0201), CENTERED(0x0202), CENTER_RIGHT(0x0203), CENTER_FIX(0x0204), CENTER_SCALED(0x0205),
    BOTTOM_LEFT(0x0301), BOTTOM_CENTER(0x0302), BOTTOM_RIGHT(0x0303), BOTTOM_FIX(0x0304), BOTTOM_SCALED(0x0305),
    FIX_LEFT(0x0401), FIX_CENTER(0x0402), FIX_RIGHT(0x0403), FIXED(0x0404), FIX_SCALED(0x0405),
    SCALED_LEFT(0x0501), SCALED_CENTER(0x0502), SCALED_RIGHT(0x0503), SCALED_FIX(0x0504), SCALED(0x0505);
    private static final int xMask = 0x00FF, yMask = 0xFF00;
    private static final int yTop = 0x0100, yCENTER = 0x0200, yBottom = 0x0300, yFix = 0x0400, yScale = 0x0500;
    private static final int xLeft = 0x0001, xCENTER = 0x0002, xRight = 0x0003, xFix = 0x0004, xScale = 0x0005;
    private final int code;
    Anchors(int code){
        this.code = code;
        System.out.println(name()+": "+code);
    }

    public boolean isXLeft() {
        return (getCode() & xMask) == xLeft;
    }
    public boolean isXRight() {
        return (getCode() & xMask) == xRight;
    }
    public boolean isXCenter() {
        return (getCode() & xMask) == xCENTER;
    }
    public boolean isXFix() {
        return (getCode() & xMask) == xFix;
    }
    public boolean isXScaled() {
        return (getCode() & xMask) == xScale;
    }

    public boolean isYTop() {
        return (getCode() & yMask) == yTop;
    }
    public boolean isYCenter() {
        return (getCode() & yMask) == yCENTER;
    }
    public boolean isYBottom() {
        return (getCode() & yMask) == yBottom;
    }
    public boolean isYFix() {
        return (getCode() & yMask) == yFix;
    }
    public boolean isYScaled() {
        return (getCode() & yMask) == yScale;
    }

}
