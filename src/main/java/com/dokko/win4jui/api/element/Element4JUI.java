package com.dokko.win4jui.api.element;

import com.dokko.win4jui.api.Input4JUI;
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
    private String ID;
    private float xDistance, yDistance, width, height;
    private Color foreground, background;
    private boolean processedInput = false;
    private Anchors anchors;
    private float scaleFactorW, scaleFactorH;
    private final ArrayList<Element4JUI> backgroundChildren = new ArrayList<>();
    private final ArrayList<Element4JUI> foregroundChildren = new ArrayList<>();
    public Graphics2D graphics;
    private Element4JUI parent;
    public float windowWidth, windowHeight;
    public float windowTargetWidth, windowTargetHeight;
    private boolean updateSize;

    public float getAnchoredXDistance() {
        return fixAnchored(getXDistance(), getYDistance(), getWidth(), getHeight())[0];
    }
    public float getAnchoredYDistance() {
        return fixAnchored(getXDistance(), getYDistance(), getWidth(), getHeight())[1];
    }
    public float getAnchoredWidth() {
        return fixAnchored(getXDistance(), getYDistance(), getWidth(), getHeight())[2];
    }
    public float getAnchoredHeight() {
        return fixAnchored(getXDistance(), getYDistance(), getWidth(), getHeight())[3];
    }

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
            element.render(graphics, element.getXDistance(), element.getYDistance(), element.getWidth(), element.getHeight(), scalingX, scalingY);
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
            element.render(graphics, element.getXDistance(), element.getYDistance(), element.getWidth(), element.getHeight(), scalingX, scalingY);
        }
    }
    /**
     * Adjusts position and size based on anchor settings.
     */
    private float[] fixAnchored(float x, float y, float width, float height) {
        float parentW = (parent != null) ? parent.getAnchoredWidth() : windowWidth;
        float parentH = (parent != null) ? parent.getAnchoredHeight() : windowHeight;

        // Adjust based on Y anchors
        if (anchors.isYCenter()) {
            if(updateSize){
                height *= scaleFactorH;
            }
            y = (parentH / 2) + y - height / 2;
        } else if (anchors.isYBottom()) {
            if(updateSize){
                height *= scaleFactorH;
            }
            y = parentH - (y + height);
        } else if (anchors.isYFix()) {
            height *= scaleFactorH;
        } else if (anchors.isYScaled()) {
            y *= scaleFactorH;
            height *= scaleFactorH;
        }

        // Adjust based on X anchors
        if (anchors.isXCenter()) {
            if(updateSize){
                width *= scaleFactorW;
            }
            x = (parentW / 2) + x - width / 2;
        } else if (anchors.isXRight()) {
            width *= scaleFactorW;
            x = parentW - (x + width);
        } else if (anchors.isXFix()) {
            width *= scaleFactorW;
        } else if (anchors.isXScaled()) {
            x *= scaleFactorW;
            width *= scaleFactorW;
        }
        // Ensure element stays within parent's bounds
        if (parent != null) {
            x += parent.getAnchoredXDistance();
            y += parent.getAnchoredYDistance();
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
    public Element4JUI addForeground(Element4JUI element) {
        element.setParent(this);
        foregroundChildren.add(element);
        return this;
    }
    /**
     * Adds an element to the background layer (children that will be drawn below this element).
     */
    public Element4JUI addBackground(Element4JUI element) {
        element.setParent(this);
        backgroundChildren.add(element);
        return this;
    }
    public Element4JUI removeForeground(String id){
        for(Element4JUI e : getForegroundChildren()){
            if(e.getID().equalsIgnoreCase(id)){
                getForegroundChildren().remove(e);
                e.setParent(null);
                return e;
            }
        }
        return null;
    }
    public Element4JUI removeBackground(String id){
        for(Element4JUI e : getBackgroundChildren()){
            if(e.getID().equalsIgnoreCase(id)){
                getBackgroundChildren().remove(e);
                e.setParent(null);
                return e;
            }
        }
        return null;
    }

    /**
     * Handles input processing. Override if your element uses input processing
     */
    public void processInput(float x, float y, float w, float h, float sw, float sh) {
        markInputAsProcessed();
    }
    /**
     * Determines whether a point is within the given bounds. Override if your element uses custom bounds
     */
    public boolean isBounded(float mouseX, float mouseY, float x, float y, float width, float height) {
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

    public boolean isHovered() {
        graphics.setColor(Color.green);
        return isBounded(Input4JUI.getMouseX(), Input4JUI.getMouseY(), getAnchoredXDistance(), getAnchoredYDistance(), getAnchoredWidth(), getAnchoredHeight());
    }

    protected void drawDebugColliders(){
        graphics.setColor(Color.green);
        graphics.drawRect((int) getAnchoredXDistance(), (int) getAnchoredYDistance(), (int) getAnchoredWidth(), (int) getAnchoredHeight());
    }
}
