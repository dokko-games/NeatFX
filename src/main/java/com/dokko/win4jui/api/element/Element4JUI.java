package com.dokko.win4jui.api.element;

import java.awt.*;
import java.util.ArrayList;

public abstract class Element4JUI {
    private float x, y, width, height;

    @SuppressWarnings("unused")
    public float getX() {
        return x;
    }

    @SuppressWarnings("unused")
    public void setX(float x) {
        this.x = x;
    }

    @SuppressWarnings("unused")
    public float getY() {
        return y;
    }

    @SuppressWarnings("unused")
    public void setY(float y) {
        this.y = y;
    }

    @SuppressWarnings("unused")
    public float getWidth() {
        return width;
    }

    @SuppressWarnings("unused")
    public void setWidth(float width) {
        this.width = width;
    }

    @SuppressWarnings("unused")
    public float getHeight() {
        return height;
    }

    @SuppressWarnings("unused")
    public void setHeight(float height) {
        this.height = height;
    }

    public Element4JUI(float x, float y, float width, float height) {
        backgroundChildren = new ArrayList<>();
        foregroundChildren = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private final ArrayList<Element4JUI> backgroundChildren, foregroundChildren;

    public ArrayList<Element4JUI> getBackgroundChildren() {
        return backgroundChildren;
    }

    public ArrayList<Element4JUI> getForegroundChildren() {
        return foregroundChildren;
    }

    public final void render(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
        for(Element4JUI element4JUI : getBackgroundChildren()) {
            element4JUI.render(graphics, x + element4JUI.getX(), y + element4JUI.getY(), element4JUI.getWidth(), element4JUI.getHeight(), scalingX, scalingY);
        }
        doRender(graphics, x, y, width, height, scalingX, scalingY);
        for(Element4JUI element4JUI : getForegroundChildren()) {
            element4JUI.render(graphics, x + element4JUI.getX(), y + element4JUI.getY(), element4JUI.getWidth(), element4JUI.getHeight(), scalingX, scalingY);
        }
    }

    protected abstract void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY);
}
