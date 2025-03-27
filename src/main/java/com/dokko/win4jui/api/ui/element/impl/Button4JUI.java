package com.dokko.win4jui.api.ui.element.impl;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.ui.element.Anchors;
import com.dokko.win4jui.api.ui.element.Element4JUI;

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
        panel = new Panel4JUI(xDistance, yDistance, width, height, anchors) {
            @Override
            protected void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
                // Set background color or default to gray
                graphics.setColor(getBackground());

                int ix = (int) x;
                int iy = (int) y;
                int iw = (int) width;
                int ih = (int) height;

                graphics.fillRect(ix, iy, iw, ih);

                Win4JUI.getDesign().decorateButton(graphics, x, y, width, height, scalingX, scalingY, getBackground());
            }

            @Override
            protected void preProcessInput(float x, float y, float w, float h, float sw, float sh) {
                markInputAsProcessed();
                onInput(x, y, w, h, sw, sh);
            }
        };
        label = new Text4JUI(text, 0, 0, Anchors.SCALE_CENTERED).setScaleText(anchors.isWidthScaled());
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

    @Override
    public boolean isHovered() {
        return panel.isHovered();
    }

    @Override
    public Color getBackground() {
        return panel.getBackground();
    }

    @Override
    public Color getForeground() {
        return label.getForeground();
    }

    public void onInput(float x, float y, float width, float height, float scaleWidth, float scaleHeight) {}

}
