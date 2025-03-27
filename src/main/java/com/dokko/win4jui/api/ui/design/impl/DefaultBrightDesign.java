package com.dokko.win4jui.api.ui.design.impl;

import com.dokko.win4jui.api.Colors4JUI;
import com.dokko.win4jui.api.ui.design.Design4JUI;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
public class DefaultBrightDesign extends Design4JUI {
    public DefaultBrightDesign() {
        super("Default Bright", new FlatLightLaf());
    }

    @Override
    public void decoratePanel(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY, Color color) {
        int ix = (int) x;
        int iy = (int) y;
        int iw = (int) width;
        int ih = (int) height;

        float factor = width / 40;
        if(width > 40) factor = 1;
        float factor2 = height / 40;
        if(height > 40) factor2 = 1;
        graphics.setColor(Colors4JUI.darken(color, 1.5f));
        graphics.fillRect(ix, iy, (int) (10 * factor), ih);
        graphics.fillRect(ix, iy + ih - (int) (10 * factor2), iw, (int) (10 * factor2));

        graphics.setColor(Colors4JUI.brighten(color, 1.1f));
        graphics.fillRect(ix + iw - (int) (10 * factor), iy, (int) (10 * factor), ih - (int) (10 * factor2));
        graphics.fillRect(ix + (int) (10 * factor), iy, iw - (int) (10 * factor), (int) (10 * factor2));
    }

    @Override
    public void decorateButton(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY, Color color) {
        int ix = (int) x;
        int iy = (int) y;
        int iw = (int) width;
        int ih = (int) height;

        float factor = width / 20;
        if(width > 20) factor = 1;
        float factor2 = height / 20;
        if(height > 20) factor2 = 1;
        graphics.setColor(Colors4JUI.darken(color, 1.5f));
        graphics.fillRect(ix, iy, (int) (5 * factor), ih);
        graphics.fillRect(ix, iy + ih - (int) (5 * factor2), iw, (int) (5 * factor2));
        graphics.fillRect(ix + iw - (int) (5 * factor), iy, (int) (5 * factor), ih - (int) (5 * factor2));
        graphics.fillRect(ix + (int) (5 * factor), iy, iw - (int) (5 * factor), (int) (5 * factor2));
    }

    @Override
    public void drawTextShadow(Graphics2D graphics, String text, float x, float y, Color color) {
        graphics.setColor(color.darker().darker().darker()); // Set shadow color (darkened version)
        graphics.drawString(text, x + 2, y + 2);  // Draw the shadow with slight offset
    }
}
