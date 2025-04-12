package com.dokko.neatfx.core.window;

import lombok.Getter;

/**
 * Simple class that represents anchors for the elements. To select an anchor keep in mind this format:
 * <pre>{@code Y_X (no scaling)
 * SCALE_Y_X (scales height)
 * Y_SCALE_X (scales width)
 * Y_X_SCALE (scales both)}</pre>
 * It also includes X and Y scaling, that can be modified through {@link Anchors#xy(boolean, boolean)} and an alignment, to modify how the element is rendered
 * (when it is 0 the element's top left is drawn at the anchor, so for centered elements it should be -0.5f)
 */
@Getter
public enum Anchors {
    // No Scaling
    TOP_LEFT(AnchorMasks.TOP | AnchorMasks.LEFT, 0f, 0f),
    TOP_CENTER(AnchorMasks.TOP | AnchorMasks.CENTER_X, -0.5f, 0f),
    TOP_RIGHT(AnchorMasks.TOP | AnchorMasks.RIGHT, -1f, 0f),

    CENTER_LEFT(AnchorMasks.CENTER_Y | AnchorMasks.LEFT, 0f, -0.5f),
    CENTERED(AnchorMasks.CENTER_Y | AnchorMasks.CENTER_X, -0.5f, -0.5f),
    CENTER_RIGHT(AnchorMasks.CENTER_Y | AnchorMasks.RIGHT, -1f, -0.5f),

    BOTTOM_LEFT(AnchorMasks.BOTTOM | AnchorMasks.LEFT, 0f, -1f),
    BOTTOM_CENTER(AnchorMasks.BOTTOM | AnchorMasks.CENTER_X, -0.5f, -1f),
    BOTTOM_RIGHT(AnchorMasks.BOTTOM | AnchorMasks.RIGHT, -1f, -1f),

    // Scale Height Only
    SCALE_TOP_LEFT(AnchorMasks.TOP | AnchorMasks.LEFT | AnchorMasks.SCALE_HEIGHT, 0f, 0f),
    SCALE_TOP_CENTER(AnchorMasks.TOP | AnchorMasks.CENTER_X | AnchorMasks.SCALE_HEIGHT, -0.5f, 0f),
    SCALE_TOP_RIGHT(AnchorMasks.TOP | AnchorMasks.RIGHT | AnchorMasks.SCALE_HEIGHT, -1f, 0f),

    SCALE_CENTER_LEFT(AnchorMasks.CENTER_Y | AnchorMasks.LEFT | AnchorMasks.SCALE_HEIGHT, 0f, -0.5f),
    SCALE_CENTERED(AnchorMasks.CENTER_Y | AnchorMasks.CENTER_X | AnchorMasks.SCALE_HEIGHT, -0.5f, -0.5f),
    SCALE_CENTER_RIGHT(AnchorMasks.CENTER_Y | AnchorMasks.RIGHT | AnchorMasks.SCALE_HEIGHT, -1f, -0.5f),

    SCALE_BOTTOM_LEFT(AnchorMasks.BOTTOM | AnchorMasks.LEFT | AnchorMasks.SCALE_HEIGHT, 0f, -1f),
    SCALE_BOTTOM_CENTER(AnchorMasks.BOTTOM | AnchorMasks.CENTER_X | AnchorMasks.SCALE_HEIGHT, -0.5f, -1f),
    SCALE_BOTTOM_RIGHT(AnchorMasks.BOTTOM | AnchorMasks.RIGHT | AnchorMasks.SCALE_HEIGHT, -1f, -1f),

    // Scale Width Only
    TOP_SCALE_LEFT(AnchorMasks.TOP | AnchorMasks.LEFT | AnchorMasks.SCALE_WIDTH, 0f, 0f),
    TOP_SCALE_CENTER(AnchorMasks.TOP | AnchorMasks.CENTER_X | AnchorMasks.SCALE_WIDTH, -0.5f, 0f),
    TOP_SCALE_RIGHT(AnchorMasks.TOP | AnchorMasks.RIGHT | AnchorMasks.SCALE_WIDTH, -1f, 0f),

    CENTER_SCALE_LEFT(AnchorMasks.CENTER_Y | AnchorMasks.LEFT | AnchorMasks.SCALE_WIDTH, 0f, -0.5f),
    CENTER_SCALE_CENTER(AnchorMasks.CENTER_Y | AnchorMasks.CENTER_X | AnchorMasks.SCALE_WIDTH, -0.5f, -0.5f),
    CENTER_SCALE_RIGHT(AnchorMasks.CENTER_Y | AnchorMasks.RIGHT | AnchorMasks.SCALE_WIDTH, -1f, -0.5f),

    BOTTOM_SCALE_LEFT(AnchorMasks.BOTTOM | AnchorMasks.LEFT | AnchorMasks.SCALE_WIDTH, 0f, -1f),
    BOTTOM_SCALE_CENTER(AnchorMasks.BOTTOM | AnchorMasks.CENTER_X | AnchorMasks.SCALE_WIDTH, -0.5f, -1f),
    BOTTOM_SCALE_RIGHT(AnchorMasks.BOTTOM | AnchorMasks.RIGHT | AnchorMasks.SCALE_WIDTH, -1f, -1f),

    // Scale Both Width & Height
    TOP_LEFT_SCALE(AnchorMasks.TOP | AnchorMasks.LEFT | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, 0f, 0f),
    TOP_CENTER_SCALE(AnchorMasks.TOP | AnchorMasks.CENTER_X | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, -0.5f, 0f),
    TOP_RIGHT_SCALE(AnchorMasks.TOP | AnchorMasks.RIGHT | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, -1f, 0f),

    CENTER_LEFT_SCALE(AnchorMasks.CENTER_Y | AnchorMasks.LEFT | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, 0f, -0.5f),
    CENTERED_SCALE(AnchorMasks.CENTER_Y | AnchorMasks.CENTER_X | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, -0.5f, -0.5f),
    CENTER_RIGHT_SCALE(AnchorMasks.CENTER_Y | AnchorMasks.RIGHT | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, -1f, -0.5f),

    BOTTOM_LEFT_SCALE(AnchorMasks.BOTTOM | AnchorMasks.LEFT | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, 0f, -1f),
    BOTTOM_CENTER_SCALE(AnchorMasks.BOTTOM | AnchorMasks.CENTER_X | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, -0.5f, -1f),
    BOTTOM_RIGHT_SCALE(AnchorMasks.BOTTOM | AnchorMasks.RIGHT | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT, -1f, -1f);

    private int code;
    private float alignX, alignY;

    Anchors(int code, float alignX, float alignY) {
        this.code = code;
        this.alignX = alignX;
        this.alignY = alignY;
    }

    public Anchors xy(boolean x, boolean y) {
        if(x)code |= AnchorMasks.SCALE_X;
        if(y)code |= AnchorMasks.SCALE_Y;
        return this;
    }

    public boolean isLeft() { return (code & AnchorMasks.X_MASK) == AnchorMasks.LEFT; }
    public boolean isRight() { return (code & AnchorMasks.X_MASK) == AnchorMasks.RIGHT; }
    public boolean isCenterX() { return (code & AnchorMasks.X_MASK) == AnchorMasks.CENTER_X; }

    public boolean isTop() { return (code & AnchorMasks.Y_MASK) == AnchorMasks.TOP; }
    public boolean isBottom() { return (code & AnchorMasks.Y_MASK) == AnchorMasks.BOTTOM; }
    public boolean isCenterY() { return (code & AnchorMasks.Y_MASK) == AnchorMasks.CENTER_Y; }

    public boolean isWidthScaled() { return (code & AnchorMasks.SCALE_WIDTH) != 0; }
    public boolean isHeightScaled() { return (code & AnchorMasks.SCALE_HEIGHT) != 0; }
    public boolean isXScaled() { return (code & AnchorMasks.SCALE_X) != 0; }
    public boolean isYScaled() { return (code & AnchorMasks.SCALE_Y) != 0; }

    public Anchors align(float x, float y) {
        alignX = x;
        alignY = y;
        return this;
    }

    public Anchors removeScaling() {
        // Remove all scaling flags from the code
        int updatedCode = code & ~(AnchorMasks.SCALE_X | AnchorMasks.SCALE_Y | AnchorMasks.SCALE_WIDTH | AnchorMasks.SCALE_HEIGHT);
        return Anchors.from(updatedCode);  // Return the corresponding anchor with updated code
    }


    private static Anchors from(int code) {
        for(Anchors anchors : values()){
            if(anchors.getCode() == code){
                return anchors;
            }
        }
        return null;
    }
}

/**
 * Anchor Masks - Bitmask Constants for Anchors
 */
class AnchorMasks {
    static final int X_MASK = 0x000F, Y_MASK = 0x0F00;
    static final int SCALE_WIDTH = 0x00F0, SCALE_HEIGHT = 0xF000, SCALE_X = 0x0F0000, SCALE_Y = 0xF00000;

    static final int LEFT = 0x0001, CENTER_X = 0x0002, RIGHT = 0x0003;
    static final int TOP = 0x0100, CENTER_Y = 0x0200, BOTTOM = 0x0300;
}