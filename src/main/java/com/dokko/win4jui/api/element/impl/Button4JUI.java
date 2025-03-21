package com.dokko.win4jui.api.element.impl;

import com.dokko.win4jui.api.design.Decoration;
import com.dokko.win4jui.api.element.Anchors;
import com.dokko.win4jui.api.element.Element4JUI;

import java.awt.*;

public class Button4JUI extends Element4JUI {
    private final Panel4JUI panel;
    private final Text4JUI label;

    /**
     * Constructs a new button with the given properties.
     *
     * @param xDistance X position relative to the parent container and anchors.
     * @param yDistance Y position relative to the parent container and anchors.
     * @param width     Width of the element.
     * @param height    Height of the element.
     * @param anchors   Anchor properties for positioning.
     */
    public Button4JUI(String text, float xDistance, float yDistance, float width, float height, Anchors anchors) {
        super(xDistance, yDistance, width, height, anchors);
        panel = new Panel4JUI(xDistance, yDistance, width, height, anchors);
        panel.setDecoration(Decoration.OUTLINE);
        label = new Text4JUI(text, 0, 0, Anchors.CENTERED).setScaleText(false);
        panel.addForeground(label);
        addForeground(panel);
        label.setFontSize(14);
    }

    @Override
    protected void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {

    }

    @Override
    public Element4JUI setBackground(Color background) {
        panel.setBackground(background);
        return this;
    }

    @Override
    public Element4JUI setForeground(Color foreground) {
        label.setForeground(foreground);
        return this;
    }
}
