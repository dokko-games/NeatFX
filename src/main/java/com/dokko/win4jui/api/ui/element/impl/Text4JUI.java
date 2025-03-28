package com.dokko.win4jui.api.ui.element.impl;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.render.Renderer2D;
import com.dokko.win4jui.api.ui.element.Anchors;
import com.dokko.win4jui.api.ui.element.Element4JUI;
import com.dokko.win4jui.api.ui.font.Font4JUI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;

/**
 * Represents a text element that can be rendered with specific font styles and options like shadowing.
 * This class is part of the UI framework for rendering text with customizable properties.
 */
@Setter
@Getter
@Accessors(chain = true)
public class Text4JUI extends Element4JUI {
    private String text;
    private boolean shadow;
    private Font4JUI font;
    private boolean scaleText = true;
    private int textHeight;
    /**
     * Constructor for creating a Text4JUI instance with specified text, position, and anchor.
     *
     * @param text The text to display.
     * @param x The x-coordinate for the text's position.
     * @param y The y-coordinate for the text's position.
     * @param anchors The anchor that defines the alignment of the text.
     */
    public Text4JUI(String text, float x, float y, Anchors anchors) {
        super(x, y, 0, 0, anchors);
        this.text = text;
        this.font = new Font4JUI(Win4JUI.getDefaultFont(), Font4JUI.Style.BOLD, 18);
        setForeground(Color.white);
    }
    /**
     * Renders the text to the provided Graphics2D context.
     *
     * @param renderer The Graphics2D context where the text will be drawn.
     * @param x The x-coordinate where the text will be drawn.
     * @param y The y-coordinate where the text will be drawn.
     * @param width The width of the area where the text will be rendered.
     * @param height The height of the area where the text will be rendered.
     * @param scalingX The scaling factor for the x-axis.
     * @param scalingY The scaling factor for the y-axis.
     */
    @Override
    protected void doRender(Renderer2D renderer, float x, float y, float width, float height, float scalingX, float scalingY) {
        // Set up the font using Font4JUI settings
        Font swingFont = font.getSwingFont();
        renderer.getJavaGraphics().setFont(swingFont);

        // Apply scaling to the font if scaling is enabled
        if (scaleText) {
            float scaledSize = swingFont.getSize() * scalingX; // Scale font size based on x scaling
            swingFont = swingFont.deriveFont(scaledSize);
            renderer.getJavaGraphics().setFont(swingFont);
        }

        // Get font metrics to determine text dimensions
        FontMetrics fontMetrics = renderer.getJavaGraphics().getFontMetrics();
        setWidth(fontMetrics.stringWidth(text) / scalingX); // Set the width based on text length
        setHeight(fontMetrics.getHeight() / scalingY); // Set the height based on font height
        textHeight = fontMetrics.getHeight(); // Store the height of the text
        y += textHeight;

        // Render background if it is set
        if (getBackground() != null) {
            renderer.color(getBackground());
            renderer.drawRect(x, y - getHeight(), getWidth() * scalingX, getHeight());
        }

        // Set the text color
        renderer.color(getForeground());

        // Draw shadow if enabled
        if (shadow) {
            Win4JUI.getDesign().drawTextShadow(renderer.getJavaGraphics(), getText(), x, y, getForeground());
        }

        // Draw the main text
        renderer.color(getForeground()); // Ensure the original color is restored
        renderer.getJavaGraphics().drawString(text, x, y); // Draw the main text

        //Draw debug colliders
        drawDebugColliders();
    }


    public Text4JUI setFontSize(int size) {
        getFont().setFontSize(size);
        return this;
    }
}
