package com.dokko.neatfx.engine;

import lombok.Getter;

import java.awt.*;

/**
 * Simple class that represents a color in RGBA space. To use a color in RGB space, use {@link Color3}
 */
@Getter
public class Color4 {

    private static final float FACTOR = 0.7f;

    public static final Color4 WHITE = new Color4(255, 255, 255, 255);
    public static final Color4 LIGHT_GRAY = new Color4(192, 192, 192, 255);
    public static final Color4 GRAY = new Color4(128, 128, 128, 255);
    public static final Color4 DARK_GRAY = new Color4(64, 64, 64, 255);
    public static final Color4 BLACK = new Color4(0, 0, 0, 255);
    public static final Color4 RED = new Color4(255, 0, 0, 255);
    public static final Color4 PINK = new Color4(255, 175, 175, 255);
    public static final Color4 ORANGE = new Color4(255, 200, 0, 255);
    public static final Color4 YELLOW = new Color4(255, 255, 0, 255);
    public static final Color4 GREEN = new Color4(0, 255, 0, 255);
    public static final Color4 MAGENTA = new Color4(255, 0, 255, 255);
    public static final Color4 CYAN = new Color4(0, 255, 255, 255);
    public static final Color4 BLUE = new Color4(0, 0, 255, 255);
    public static final Color4 NONE = new Color4(0, 0, 0, 0);

    /**
     * The components of the color, as integers between 0 and 255
     */
    private int red, green, blue, alpha;
    /**
     * The components of the color, as floats between 0 and 1
     */
    private float redFloat, greenFloat, blueFloat, alphaFloat;

    /**
     * Constructs a color in INTEGER mode
     * @param red the red component (0-255)
     * @param green the green component (0-255)
     * @param blue the blue component (0-255)
     * @param alpha the alpha (transparency) component (0-255)
     */
    public Color4(int red, int green, int blue, int alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }
    /**
     * Constructs a color in FLOAT mode
     * @param red the red component (0-1)
     * @param green the green component (0-1)
     * @param blue the blue component (0-1)
     * @param alpha the alpha (transparency) component (0-1)
     */
    public Color4(float red, float green, float blue, float alpha) {
        setRedFloat(red);
        setGreenFloat(green);
        setBlueFloat(blue);
        setAlphaFloat(alpha);
    }
    public void setRed(int red) {
        this.red = red;
        this.redFloat = (float) red / 255;
    }
    public void setGreen(int green) {
        this.green = green;
        this.greenFloat = (float) green / 255;
    }
    public void setBlue(int blue) {
        this.blue = blue;
        this.blueFloat = (float) blue / 255;
    }
    public void setRedFloat(float red) {
        this.redFloat = red;
        this.red = (int) (red * 255);
    }
    public void setGreenFloat(float green) {
        this.greenFloat = green;
        this.green = (int) (green * 255);
    }
    public void setBlueFloat(float blue) {
        this.blueFloat = blue;
        this.blue = (int) blue * 255;
    }
    public void setAlphaFloat(float alpha) {
        this.alphaFloat = alpha;
        this.alpha = (int) alpha * 255;
    }
    public void setAlpha(int alpha) {
        this.alpha = alpha;
        this.alphaFloat = (float) alpha / 255;
    }

    /**
     * Makes the highest channel of the color (RGB) to 255
     * @return the normalized color
     */
    public Color4 normalize() {
        int max = Math.max(red, Math.max(green, blue));

        if (max == 0) {
            return new Color4(0, 0, 0, 255); // Avoid division by zero, return black
        }

        float scale = 255.0f / max;

        int scaledRed = Math.round(red * scale);
        int scaledGreen = Math.round(green * scale);
        int scaledBlue = Math.round(blue * scale);

        return new Color4(scaledRed, scaledGreen, scaledBlue, alpha);
    }
    /**
     * Clamps all 4 channels from 0 to 255
     * @return the clamped color
     */
    public Color4 clamp() {
        int clampedRed = Math.max(0, Math.min(255, red));
        int clampedGreen = Math.max(0, Math.min(255, green));
        int clampedBlue = Math.max(0, Math.min(255, blue));
        int clampedAlpha = Math.max(0, Math.min(255, alpha));

        return new Color4(clampedRed, clampedGreen, clampedBlue, clampedAlpha);
    }

    /**
     * Returns the color as an RGB color
     * @return the color as an RGB color
     */
    public Color3 color3() {
        return new Color3(red, green, blue);
    }

    /**
     * Returns a color in which all three RGB channels are inverted, and the alpha stays the same
     * @return the inverted color
     */
    public Color4 invert() {
        return new Color4(255-red, 255-green, 255-blue, alpha);
    }

    /**
     * Transforms the color into a java's AWT one
     * @return the AWT color
     */
    public Color asAWT() {
        return new Color(getRed(), getGreen(), getBlue(), getAlpha());
    }

    public boolean equals(Color4 obj) {
        return getRed() == obj.getRed() && getGreen() == obj.getGreen() && getBlue() == obj.getBlue()
                && getAlpha() == obj.getAlpha();
    }
    public boolean equals(Color3 obj) {
        return getRed() == obj.getRed() && getGreen() == obj.getGreen() && getBlue() == obj.getBlue();
    }

    public Color4 darker(int stack) {
        Color4 c2 = copy();
        for(int i = 0; i < stack; i++){
            c2 = c2.darker();
        }
        return c2;
    }
    public Color4 brighter(int stack) {
        Color4 c2 = copy();
        for(int i = 0; i < stack; i++){
            c2 = c2.brighter();
        }
        return c2;
    }
    private Color4 brighter() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        int alpha = getAlpha();
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color4(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new Color4(Math.min((int)(r/FACTOR), 255),
                Math.min((int)(g/FACTOR), 255),
                Math.min((int)(b/FACTOR), 255),
                alpha);
    }
    private Color4 darker() {
        return new Color4(Math.max((int)(getRed() * FACTOR), 0),
                Math.max((int)(getGreen() * FACTOR), 0),
                Math.max((int)(getBlue() * FACTOR), 0),
                getAlpha());
    }
    private Color4 copy() {
        return new Color4(red, green, blue, alpha);
    }

    @Override
    public String toString() {
        return "Color4{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }
}
