package com.dokko.win4jui.api.element.impl;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.element.Element4JUI;
import com.dokko.win4jui.api.font.Font4JUI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.awt.geom.AffineTransform;

@Setter
@Getter
@Accessors(chain = true)
public class Text4JUI extends Element4JUI {
    private String text;
    private Font4JUI font;
    private boolean scaleText = true;
    public Text4JUI(String text, float x, float y) {
        super(x, y, 0, 0);
        this.text = text;
        this.font = new Font4JUI(Win4JUI.getDefaultFont(), Font4JUI.Style.BOLD, 18);
    }

    @Override
    protected void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
        int adjustedX = (int)x, adjustedY = (int)y;
        AffineTransform originalTransform = null;
        if(scaleText){
            // Save the current transform
            originalTransform = graphics.getTransform();

            // Apply scaling
            graphics.scale(scalingX, scalingY);

            // Adjust coordinates to maintain the original start position
            adjustedX = (int) (x / scalingX);
            adjustedY = (int) (y / scalingY);
        }
        // Draw background
        if (getBackground() != null) {
            graphics.setColor(getBackground());
            graphics.fillRect(adjustedX, adjustedY - graphics.getFontMetrics(getFont().getSwingFont()).getAscent() - 2,
                    graphics.getFontMetrics(getFont().getSwingFont()).stringWidth(text),
                    graphics.getFontMetrics(getFont().getSwingFont()).getHeight() + 3);
        }
        // Draw the text at the adjusted position
        graphics.setColor(getForeground());
        graphics.setFont(getFont().getSwingFont());
        graphics.drawString(text, adjustedX, adjustedY);

        if(scaleText){
            // Restore the original transform to prevent side effects
            graphics.setTransform(originalTransform);
        }
    }

}
