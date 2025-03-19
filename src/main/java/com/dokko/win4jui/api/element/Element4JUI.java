package com.dokko.win4jui.api.element;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a UI element in the Win4JUI framework.
 * This abstract class provides basic properties and rendering capabilities for UI elements.
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class Element4JUI {
    private float xDistance, yDistance, width, height;
    private Color foreground, background;
    private boolean processedInput = false;
    private Anchors anchors;
    private float scaleFactorW, scaleFactorH;
    private final ArrayList<Element4JUI> backgroundChildren = new ArrayList<>();
    private final ArrayList<Element4JUI> foregroundChildren = new ArrayList<>();
    public Graphics2D graphics;
    public float frameWidth, frameHeight;
    public float frameTargetWidth, frameTargetHeight;

    /**
     * Marks the input as processed to prevent beginner errors.
     */
    public final void markInputAsProcessed(){
        processedInput = true;
    }
    /**
     * Constructs a new UI element with the given properties.
     *
     * @param xDistance X position relative to the parent container and anchors.
     * @param yDistance Y position relative to the parent container and anchors.
     * @param width     Width of the element.
     * @param height    Height of the element.
     * @param anchors   Anchor properties for positioning.
     */
    public Element4JUI(float xDistance, float yDistance, float width, float height, Anchors anchors) {
        this.xDistance = xDistance;
        this.yDistance = yDistance;
        this.width = width;
        this.height = height;
        this.anchors = anchors;
    }
    /**
     * Renders the UI element and its children.
     *
     * @param graphics  Graphics context for rendering.
     * @param x         X position (always use getXDistance).
     * @param y         Y position (always use getYDistance).
     * @param width     Width of the element.
     * @param height    Height of the element.
     * @param scalingX  X scaling factor.
     * @param scalingY  Y scaling factor.
     */
    public final void render(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY) {
        this.scaleFactorW = scalingX;
        this.scaleFactorH = scalingY;

        float[] fixedDimensions = fixAnchored(x, y, width, height);
        x = fixedDimensions[0];
        y = fixedDimensions[1];
        width = fixedDimensions[2];
        height = fixedDimensions[3];

        processedInput = false;

        // Render background children
        for (Element4JUI element : backgroundChildren) {
            element.render(graphics, x + element.getXDistance(), y + element.getYDistance(), element.getWidth(), element.getHeight(), scalingX, scalingY);
        }

        this.graphics = graphics;
        doRender(graphics, x, y, width, height, scalingX, scalingY);
        preProcessInput(x, y, width, height, scalingX, scalingY);

        if (!processedInput) {
            System.err.println("Input was not processed successfully for " + this + ". Possible causes:");
            System.err.println("1 - The overridden method preProcessInput() did not call processInput().");
            System.err.println("2 - The overridden method processInput() did not call super.markInputAsProcessed().");
            System.exit(-1);
        }

        // Render foreground children
        for (Element4JUI element : foregroundChildren) {
            element.render(graphics, x + element.getXDistance(), y + element.getYDistance(), element.getWidth(), element.getHeight(), scalingX, scalingY);
        }
    }
    /**
     * Adjusts position and size based on anchor settings.
     */
    private float[] fixAnchored(float x, float y, float width, float height) {
        // Adjust based on Y anchors
        if (anchors.isYCenter()) {
            height *= scaleFactorH;
            y = (frameHeight / 2) + y - height / 2;
        } else if (anchors.isYBottom()) {
            height *= scaleFactorH;
            y = frameHeight - (y + height);
        } else if (anchors.isYFix()) {
            y *= scaleFactorH;
        }  else if (anchors.isYScaled()) {
            y *= scaleFactorH;
            height *= scaleFactorH;
        }

        // Adjust based on X anchors
        if (anchors.isXCenter()) {
            width *= scaleFactorW;
            x = (frameWidth / 2) + x - width / 2;
        } else if (anchors.isXRight()) {
            width *= scaleFactorW;
            x = frameWidth - (x + width);
        } else if (anchors.isXFix()) {
            x *= scaleFactorW;
        } else if (anchors.isXScaled()) {
            x *= scaleFactorW;
            width *= scaleFactorW;
        }
        return new float[]{x, y, width, height};
    }
    /**
     * Abstract method for rendering. Must be implemented by subclasses.
     */
    protected abstract void doRender(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY);
    /**
     * Adds an element to the foreground layer (children that will be drawn on top of this element).
     */
    public Element4JUI addF(Element4JUI element) {
        foregroundChildren.add(element);
        return this;
    }
    /**
     * Adds an element to the background layer (children that will be drawn below this element).
     */
    public Element4JUI addB(Element4JUI element) {
        backgroundChildren.add(element);
        return this;
    }

    /**
     * Handles input processing. Override if your element uses input processing
     */
    public void processInput(float x, float y, float w, float h, float sw, float sh) {
        markInputAsProcessed();
    }
    /**
     * Determines whether the given coordinates are within the element's bounds.
     */
    public boolean isBounded(float x, float y) {
        return isBounded(x, y, getXDistance(), getYDistance(), getWidth(), getHeight());
    }
    /**
     * Determines whether a point is within the given bounds. Override if your element uses custom bounds
     */
    public boolean isBounded(float mouseX, float mouseY, float x, float y, float width, float height) {
        // Adjust for scaling
        float[] fixedDimensions = fixAnchored(x, y, width, height);
        x = fixedDimensions[0];
        y = fixedDimensions[1];
        width = fixedDimensions[2];
        height = fixedDimensions[3];
        return isBoundedRaw(mouseX, mouseY, x, y, width, height);
    }
    public boolean isBoundedRaw(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
    }

    /**
     * Runs before processInput. Used by subclasses to modify the input data before sending it to custom instances
     * @param x the x position of the element
     * @param y the y position of the element
     * @param w the width of the element
     * @param h the height of the element
     * @param sw the width scaling factor
     * @param sh the height scaling factor
     */
    protected void preProcessInput(float x, float y, float w, float h, float sw, float sh) {
        processInput(x, y, w, h, sw, sh);
    }
}
