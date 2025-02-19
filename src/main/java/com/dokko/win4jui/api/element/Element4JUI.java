package com.dokko.win4jui.api.element;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
@Accessors(chain = true)
public abstract class Element4JUI {
    private float x, y, width, height;
    private Color foreground, background;
    public Element4JUI(float x, float y, float width, float height) {
        backgroundChildren = new ArrayList<>();
        foregroundChildren = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private final ArrayList<Element4JUI> backgroundChildren, foregroundChildren;

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
