package com.dokko.win4jui.api.element.impl;

import com.dokko.win4jui.api.element.Element4JUI;

import java.awt.*;

public class Panel4JUI extends Element4JUI {

    public Panel4JUI(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    protected void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
        if(getBackground() != null)graphics.setColor(getBackground());
        int ix = (int) x;
        int iy = (int) y;
        int iw = (int) width;
        int ih = (int) height;
        graphics.fillRect(ix, iy, iw, ih);
    }
}
