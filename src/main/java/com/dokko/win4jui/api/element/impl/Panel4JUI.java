package com.dokko.win4jui.api.element.impl;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.Colors4JUI;
import com.dokko.win4jui.api.design.Decoration;
import com.dokko.win4jui.api.element.Anchors;
import com.dokko.win4jui.api.element.Element4JUI;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * A simple panel implementation for Win4JUI that supports rendering and input processing.
 */
@Getter
@Setter
public class Panel4JUI extends Element4JUI {
    private Decoration decoration = Decoration.BEVEL;

    /**
     * Constructs a Panel4JUI instance with specified dimensions and anchor settings.
     *
     * @param xDistance The x offset from the parent container.
     * @param yDistance The y offset from the parent container.
     * @param width The width of the panel.
     * @param height The height of the panel.
     * @param anchors The anchoring behavior of the panel.
     */
    public Panel4JUI(float xDistance, float yDistance, float width, float height, Anchors anchors) {
        super(xDistance, yDistance, width, height, anchors);
    }

    /**
     * Renders the panel, applying background color and optional decorations.
     *
     * @param graphics The graphics context.
     * @param x The x position of the panel.
     * @param y The y position of the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     * @param scalingX The horizontal scaling factor.
     * @param scalingY The vertical scaling factor.
     */
    @Override
    protected void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
        // Set background color or default to gray
        graphics.setColor(getBackground() != null ? getBackground() : Color.GRAY);

        int ix = (int) x;
        int iy = (int) y;
        int iw = (int) width;
        int ih = (int) height;

        graphics.fillRect(ix, iy, iw, ih);

        if (!Win4JUI.getDesign().isDecorated())return;
        Decoration util = decoration;
        if(decoration == Decoration.DEFAULT) util = Win4JUI.getDesign().getDecoration();
        // Draw panel decorations if enabled
        switch (decoration){
            case BEVEL: {
                float factor = scalingX;
                if(width > 40) factor = 1;
                float factor2 = scalingY;
                if(height > 40) factor2 = 1;
                graphics.setColor(Colors4JUI.darken(graphics.getColor(), 1.5f));
                graphics.fillRect(ix, iy, (int) (10 * factor), ih);
                graphics.fillRect(ix, iy + ih - (int) (10 * factor2), iw, (int) (10 * factor2));

                graphics.setColor(Colors4JUI.brighten(graphics.getColor(), 1.5f));
                graphics.fillRect(ix + iw - (int) (10 * factor), iy, (int) (10 * factor), ih - (int) (10 * factor2));
                graphics.fillRect(ix + (int) (10 * factor), iy, iw - (int) (10 * factor), (int) (10 * factor2));
                break;
            }
            case OUTLINE: {
                float factor = scalingX;
                if(width > 20) factor = 1;
                float factor2 = scalingY;
                if(height > 20) factor2 = 1;
                graphics.setColor(Colors4JUI.darken(graphics.getColor(), 1.5f));
                graphics.fillRect(ix, iy, (int) (5 * factor), ih);
                graphics.fillRect(ix, iy + ih - (int) (5 * factor2), iw, (int) (5 * factor2));
                graphics.fillRect(ix + iw - (int) (5 * factor), iy, (int) (5 * factor), ih - (int) (5 * factor2));
                graphics.fillRect(ix + (int) (5 * factor), iy, iw - (int) (5 * factor), (int) (5 * factor2));
                break;
            }
        }
        drawDebugColliders();
    }
}
