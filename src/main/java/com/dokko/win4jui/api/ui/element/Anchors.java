package com.dokko.win4jui.api.ui.element;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.Logger4JUI;
import lombok.Getter;

@Getter
public enum Anchors {

    //UNSCALED
    TOP_LEFT(AnchorMasks.yTop | AnchorMasks.xLeft),
    TOP_CENTER(AnchorMasks.yTop | AnchorMasks.xCenter),
    TOP_RIGHT(AnchorMasks.yTop | AnchorMasks.xRight),

    CENTER_LEFT(AnchorMasks.yCenter | AnchorMasks.xLeft),
    CENTERED(AnchorMasks.yCenter | AnchorMasks.xCenter),
    CENTER_RIGHT(AnchorMasks.yCenter | AnchorMasks.xRight),

    BOTTOM_LEFT(AnchorMasks.yBottom | AnchorMasks.xLeft),
    BOTTOM_CENTER(AnchorMasks.yBottom | AnchorMasks.xCenter),
    BOTTOM_RIGHT(AnchorMasks.yBottom | AnchorMasks.xRight),

    //SCALED Y
    TOP_SCALE_LEFT(AnchorMasks.yTop | AnchorMasks.wScale | AnchorMasks.xLeft),
    TOP_SCALE_CENTER(AnchorMasks.yTop | AnchorMasks.wScale | AnchorMasks.xCenter),
    TOP_SCALE_RIGHT(AnchorMasks.yTop | AnchorMasks.wScale | AnchorMasks.xRight),

    CENTER_SCALE_LEFT(AnchorMasks.yCenter | AnchorMasks.wScale | AnchorMasks.xLeft),
    CENTER_SCALE_CENTER(AnchorMasks.yCenter | AnchorMasks.wScale | AnchorMasks.xCenter),
    CENTER_SCALE_RIGHT(AnchorMasks.yCenter | AnchorMasks.wScale | AnchorMasks.xRight),

    BOTTOM_SCALE_LEFT(AnchorMasks.yBottom | AnchorMasks.wScale | AnchorMasks.xLeft),
    BOTTOM_SCALE_CENTER(AnchorMasks.yBottom | AnchorMasks.wScale | AnchorMasks.xCenter),
    BOTTOM_SCALE_RIGHT(AnchorMasks.yBottom | AnchorMasks.wScale | AnchorMasks.xRight),

    //SCALE X
    TOP_LEFT_SCALE(AnchorMasks.yTop | AnchorMasks.xLeft | AnchorMasks.hScale),
    TOP_CENTER_SCALE(AnchorMasks.yTop | AnchorMasks.xCenter | AnchorMasks.hScale),
    TOP_RIGHT_SCALE(AnchorMasks.yTop | AnchorMasks.xRight | AnchorMasks.hScale),

    CENTER_LEFT_SCALE(AnchorMasks.yCenter | AnchorMasks.xLeft | AnchorMasks.hScale),
    CENTER_CENTER_SCALE(AnchorMasks.yCenter | AnchorMasks.xCenter | AnchorMasks.hScale),
    CENTER_RIGHT_SCALE(AnchorMasks.yCenter | AnchorMasks.xRight | AnchorMasks.hScale),

    BOTTOM_LEFT_SCALE(AnchorMasks.yBottom | AnchorMasks.xLeft | AnchorMasks.hScale),
    BOTTOM_CENTER_SCALE(AnchorMasks.yBottom | AnchorMasks.xCenter | AnchorMasks.hScale),
    BOTTOM_RIGHT_SCALE(AnchorMasks.yBottom | AnchorMasks.xRight | AnchorMasks.hScale),

    //SCALE XY
    SCALE_TOP_LEFT(AnchorMasks.yTop | AnchorMasks.wScale | AnchorMasks.xLeft | AnchorMasks.hScale),
    SCALE_TOP_CENTER(AnchorMasks.yTop | AnchorMasks.wScale | AnchorMasks.xCenter | AnchorMasks.hScale),
    SCALE_TOP_RIGHT(AnchorMasks.yTop | AnchorMasks.wScale | AnchorMasks.xRight | AnchorMasks.hScale),

    SCALE_CENTER_LEFT(AnchorMasks.yCenter | AnchorMasks.wScale | AnchorMasks.xLeft | AnchorMasks.hScale),
    SCALE_CENTERED(AnchorMasks.yCenter | AnchorMasks.wScale | AnchorMasks.xCenter | AnchorMasks.hScale),
    SCALE_CENTER_RIGHT(AnchorMasks.yCenter | AnchorMasks.wScale | AnchorMasks.xRight | AnchorMasks.hScale),

    SCALE_BOTTOM_LEFT(AnchorMasks.yBottom | AnchorMasks.wScale | AnchorMasks.xLeft | AnchorMasks.hScale),
    SCALE_BOTTOM_CENTER(AnchorMasks.yBottom | AnchorMasks.wScale | AnchorMasks.xCenter | AnchorMasks.hScale),
    SCALE_BOTTOM_RIGHT(AnchorMasks.yBottom | AnchorMasks.wScale | AnchorMasks.xRight | AnchorMasks.hScale)
    ;

    private static class AnchorMasks {
        private static final int xMask = 0x000F, yMask = 0x0F00,
                wScaleMask = 0x00F0, hScaleMask = 0xF000, xScaleMask = 0x0F0000, yScaleMask = 0xF00000;
        private static final int yTop = 0x0100, yCenter = 0x0200, yBottom = 0x0300;
        private static final int xLeft = 0x0001, xCenter = 0x0002, xRight = 0x0003;
        private static final int wScale = 0x1000, hScale = 0x0010;
        private static final int xScale = 0x010000, yScale = 0x100000;
    }
    private int code;
    Anchors(int code){
        this.code = code;
        if(Win4JUI.SDK_IS_DEBUG){
            Logger4JUI.info("Anchors.%{0}: %{1}", name(), code);
        }
    }
    public Anchors scale(boolean x, boolean y){
        if(x) code = code | AnchorMasks.xScale;
        if(y) code = code | AnchorMasks.yScale;
        return this;
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
    public boolean isWidthScaled() {
        return (getCode() & AnchorMasks.wScaleMask) == AnchorMasks.hScale;
    }
    public boolean isXScaled() {
        return (getCode() & AnchorMasks.xScaleMask) == AnchorMasks.xScale;
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
    public boolean isHeightScaled() {
        return (getCode() & AnchorMasks.hScaleMask) == AnchorMasks.wScale;
    }
    public boolean isYScaled() {
        return (getCode() & AnchorMasks.yScaleMask) == AnchorMasks.yScale;
    }

}
