package com.dokko.win4jui.api.ui.element.impl;

import com.dokko.win4jui.Win4JUI;

import com.dokko.win4jui.api.render.Renderer2D;
import com.dokko.win4jui.api.ui.element.Anchors;
import com.dokko.win4jui.api.ui.element.Element4JUI;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * A simple panel implementation for Win4JUI that supports rendering and input processing.
 */
@Getter
@Setter
public class Panel4JUI extends Element4JUI {

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
        setBackground(Color.GRAY);
    }

    /**
     * Renders the panel, applying background color and optional decorations.
     *
     * @param renderer2D The graphics context.
     * @param x The x position of the panel.
     * @param y The y position of the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     * @param scalingX The horizontal scaling factor.
     * @param scalingY The vertical scaling factor.
     */
    @Override
    protected void doRender(Renderer2D renderer2D, float x, float y, float width, float height, float scalingX, float scalingY) {
        // Set background color or default to gray
        renderer2D.color(getBackground());

        renderer2D.drawRect(x, y, width, height);

        Win4JUI.getDesign().decoratePanel(renderer2D.getJavaGraphics(), x, y, width, height, scalingX, scalingY, getBackground());
    }
}
