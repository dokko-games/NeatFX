package com.dokko.win4jui.api.element;

import lombok.Getter;

@Getter
public enum Anchors {

    TOP_LEFT(AnchorMasks.yTop | AnchorMasks.xLeft), TOP_CENTER(AnchorMasks.yTop | AnchorMasks.xCenter),
    TOP_RIGHT(AnchorMasks.yTop | AnchorMasks.xRight), TOP_FIX(AnchorMasks.yTop | AnchorMasks.xFix),
    TOP_SCALED(AnchorMasks.yTop | AnchorMasks.xScale),

    CENTER_LEFT(AnchorMasks.yCenter | AnchorMasks.xLeft), CENTERED(AnchorMasks.yCenter | AnchorMasks.xCenter),
    CENTER_RIGHT(AnchorMasks.yCenter | AnchorMasks.xRight), CENTER_FIX(AnchorMasks.yCenter | AnchorMasks.xFix),
    CENTER_SCALED(AnchorMasks.yCenter | AnchorMasks.xScale),

    BOTTOM_LEFT(AnchorMasks.yBottom | AnchorMasks.xLeft), BOTTOM_CENTER(AnchorMasks.yBottom | AnchorMasks.xCenter),
    BOTTOM_RIGHT(AnchorMasks.yBottom | AnchorMasks.xRight), BOTTOM_FIX(AnchorMasks.yBottom | AnchorMasks.xFix),
    BOTTOM_SCALED(AnchorMasks.yBottom | AnchorMasks.xScale),

    FIX_LEFT(AnchorMasks.yFix | AnchorMasks.xLeft), FIX_CENTER(AnchorMasks.yFix | AnchorMasks.xCenter),
    FIX_RIGHT(AnchorMasks.yFix | AnchorMasks.xRight), FIXED(AnchorMasks.yFix | AnchorMasks.xFix),
    FIX_SCALED(AnchorMasks.yFix | AnchorMasks.xScale),

    SCALED_LEFT(AnchorMasks.yScale | AnchorMasks.xLeft), SCALED_CENTER(AnchorMasks.yScale | AnchorMasks.xCenter),
    SCALED_RIGHT(AnchorMasks.yScale | AnchorMasks.xRight), SCALED_FIX(AnchorMasks.yScale | AnchorMasks.xFix),
    SCALED(AnchorMasks.yScale | AnchorMasks.xScale);

    private static class AnchorMasks {
        private static final int xMask = 0x00FF, yMask = 0xFF00;
        private static final int yTop = 0x0100, yCenter = 0x0200, yBottom = 0x0300, yFix = 0x0400, yScale = 0x0500;
        private static final int xLeft = 0x0001, xCenter = 0x0002, xRight = 0x0003, xFix = 0x0004, xScale = 0x0005;
    }
    private final int code;
    Anchors(int code){
        this.code = code;
    }

    public boolean isXLeft() {
        return (getCode() & AnchorMasks.xMask) == AnchorMasks.xLeft;
    }
    public boolean isXRight() {
        return (getCode() & AnchorMasks.xMask) == AnchorMasks.xRight;
    }
    public boolean isXCenter() {
        return (getCode() & AnchorMasks.xMask) == AnchorMasks.xCenter;
    }
    public boolean isXFix() {
        return (getCode() & AnchorMasks.xMask) == AnchorMasks.xFix;
    }
    public boolean isXScaled() {
        return (getCode() & AnchorMasks.xMask) == AnchorMasks.xScale;
    }

    public boolean isYTop() {
        return (getCode() & AnchorMasks.yMask) == AnchorMasks.yTop;
    }
    public boolean isYCenter() {
        return (getCode() & AnchorMasks.yMask) == AnchorMasks.yCenter;
    }
    public boolean isYBottom() {
        return (getCode() & AnchorMasks.yMask) == AnchorMasks.yBottom;
    }
    public boolean isYFix() {
        return (getCode() & AnchorMasks.yMask) == AnchorMasks.yFix;
    }
    public boolean isYScaled() {
        return (getCode() & AnchorMasks.yMask) == AnchorMasks.yScale;
    }

}
