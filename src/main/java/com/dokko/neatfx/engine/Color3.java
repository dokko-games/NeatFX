package com.dokko.neatfx.engine;

/**
 * Simple RGB color class that extends {@link Color4}
 */
public class Color3 extends Color4 {

    public Color3(int red, int green, int blue){
        super(red, green, blue, 255);
    }
    public Color3(float red, float green, float blue){
        super(red, green, blue, 1f);
    }
    public Color4 color4() {
        return new Color4(getRed(), getGreen(), getBlue(), 255);
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(255);
    }

    @Override
    public void setAlphaFloat(float alpha) {
        super.setAlphaFloat(1f);
    }

}
