package com.dokko.neatfx.core.window.element;

import com.dokko.neatfx.core.Input;
import com.dokko.neatfx.core.error.Error;
import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.engine.Color4;
import com.dokko.neatfx.engine.render.RenderOverride;
import com.dokko.neatfx.engine.render.Renderer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * A class that represents any object that will be drawn on screen
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class Element {

    /**
     * The identifier of the element. Can be used to distinguish it from others when removing it from a parent element
     */
    private String ID;
    /**
     * The X position of the element, relative to its anchor
     */
    private float x;
    /**
     * The Y position of the element, relative to its anchor
     */
    private float y;
    /**
     * The width of the element, when it is not being scaled
     */
    private float width;
    /**
     * The height of the element, when it is not being scaled
     */
    private float height;
    /**
     * The default width of the element, when it is not being scaled
     */
    private float defaultWidth;
    /**
     * The default height of the element, when it is not being scaled
     */
    private float defaultHeight;
    /**
     * The anchored X position of the element, relative to the top left of the screen
     */
    private float anchoredX;
    /**
     * The anchored Y position of the element, relative to the top left of the screen
     */
    private float anchoredY;
    /**
     * The anchored width of the element
     */
    private float anchoredWidth;
    /**
     * The anchored height of the element
     */
    private float anchoredHeight;
    /**
     * The anchors of the element. They represent whether the object is placed on the top, center or bottom of the screen vertically
     * and on the left, center or right horizontally;
     */
    private Anchors anchors;
    /**
     * The parent element of this element
     */
    private Element parent;

    @Getter(value=AccessLevel.NONE)
    @Setter(value= AccessLevel.NONE)
    private float scaleFactorW;
    @Getter(value=AccessLevel.NONE)
    @Setter(value= AccessLevel.NONE)
    private float scaleFactorH;
    /**
     * The X multiplier for scaling. Width scaling is calculated as:
     * <pre>{@code (scalingFactorX - 1) * scalingXMultiplier + 1}</pre>
     */
    private float scalingXMultiplier = 1;
    /**
     * The Y multiplier for scaling. Height scaling is calculated as:
     * <pre>{@code (scalingFactorY - 1) * scalingYMultiplier + 1}</pre>
     */
    private float scalingYMultiplier = 1;
    /**
     * The children elements that will be drawn behind this element
     */
    private ArrayList<Element> backgroundChildren;
    /**
     * The children elements that will be drawn behind this element
     */
    private ArrayList<Element> foregroundChildren;

    @Getter(value=AccessLevel.NONE)
    @Setter(value= AccessLevel.NONE)
    private boolean processedInput;

    /**
     * the color of any foreground-rendered graphic in this element, like the foreground of a {@link com.dokko.neatfx.core.window.element.impl.basic.Text}
     */
    private Color4 foreground = Color4.GRAY;
    /**
     * the color of any background-rendered graphic in this element, like the background of a {@link com.dokko.neatfx.core.window.element.impl.basic.Panel}
     */
    private Color4 background = Color4.GRAY;

    /**
     * Creates an element
     * @param x its X position
     * @param y its Y position
     * @param width its width
     * @param height its height
     * @param anchors its anchors
     */
    public Element(float x, float y, float width, float height, Anchors anchors) {
        setX(x);
        setY(y);
        setWidth(width);
        setDefaultWidth(width);
        setHeight(height);
        setDefaultHeight(height);
        setAnchors(anchors);
        backgroundChildren = new ArrayList<>();
        foregroundChildren = new ArrayList<>();
    }

    /**
     * Renders the element and its children
     * @param x the x position of the element
     * @param y the y position of the element
     * @param width the width of the element
     * @param height the height of the element
     * @param scalingFactorW the horizontal scaling factor, related to the window's size
     * @param scalingFactorH the vertical scaling factor, related to the window's size
     */
    public void render(float x, float y, float width, float height, float scalingFactorW, float scalingFactorH) {
        scalingFactorW = (scalingFactorW - 1) * scalingXMultiplier + 1;
        scalingFactorH = (scalingFactorH - 1) * scalingYMultiplier + 1;
        GL11.glPushMatrix();
        this.scaleFactorW = scalingFactorW;
        this.scaleFactorH = scalingFactorH;

        float[] fixedDimensions = fixAnchors(x, y, width, height);
        setAnchoredX(fixedDimensions[0]);
        setAnchoredY(fixedDimensions[1]);
        setAnchoredWidth(fixedDimensions[2]);
        setAnchoredHeight(fixedDimensions[3]);

        // Render background children
        for (Element element : getBackgroundChildren()) {
            element.render(element.getX(), element.getY(), element.getWidth(), element.getHeight(), scalingFactorW, scalingFactorH);
        }
        RenderOverride renderOverride = new RenderOverride(this);
        renderOverride.call();
        setBackground(renderOverride.getBackground());
        setForeground(renderOverride.getForeground());
        if(!renderOverride.isCancelled()){
            doRender(scalingFactorW, scalingFactorH); //don't pass x or y, as it is being translated
        }
        renderOverride.setBackground(renderOverride.getPreBackground());
        renderOverride.setForeground(renderOverride.getPreForeground());
        renderOverride.setCancelled(false);
        renderOverride.setPost(true);
        renderOverride.call();
        setBackground(renderOverride.getPreBackground());
        setForeground(renderOverride.getPreForeground());

        // Render foreground children
        for (Element element : getForegroundChildren()) {
            element.render(element.getX(), element.getY(), element.getWidth(), element.getHeight(), scalingFactorW, scalingFactorH);
        }
        GL11.glPopMatrix();
    }

    /**
     * Processes the input for the element and its children
     */
    public void input() {
        processedInput = false;
        // process background children
        for (Element element : getBackgroundChildren()) {
            element.input();
        }
        preProcessInput(getAnchoredX(), getAnchoredY(), getAnchoredWidth(), getAnchoredHeight(), scaleFactorW, scaleFactorH);

        if(!processedInput) {
            throw new Error("Input Error", "Input was not processed for "+this, "processInput or preProcessInput").getException();
        }
        // Render foreground children
        for (Element element : getForegroundChildren()) {
            element.input();
        }
    }

    /**
     * Renders the element. Keep in mind that graphics are being translated, so no need to use the X and Y positions
     * @param scalingFactorW the horizontal scaling factor, related to the window's size
     * @param scalingFactorH the vertical scaling factor, related to the window's size
     */
    protected abstract void doRender(float scalingFactorW, float scalingFactorH);

    /**
     * Converts anchor-space coordinates to window-space coordinates. Also translates graphics to the element's position
     * @param x the X position to convert
     * @param y the Y position to convert
     * @param width the width to convert
     * @param height the height to convert
     * @return an array with all 4 values: X, Y, width and height
     */
    private float[] fixAnchors(float x, float y, float width, float height) {
        float parentW = (parent != null) ? parent.getAnchoredWidth() : Renderer.getWindowWidth();
        float parentH = (parent != null) ? parent.getAnchoredHeight() : Renderer.getWindowHeight();
        if(anchors.isXScaled()) {
            x *= scaleFactorW;
        }
        if(anchors.isYScaled()) {
            y *= scaleFactorH;
        }
        if(anchors.isWidthScaled()){
            width *= scaleFactorW;
        }
        if(anchors.isHeightScaled()){
            height *= scaleFactorH;
        }
        // Adjust based on Y anchors
        //nothing to do if it is TOP, that is just absolute
        if (anchors.isCenterY()) {
            y = (parentH / 2) + y;
        } else if (anchors.isBottom()) {
            y = -y;
            y = parentH + y;
        }
        // Adjust based on X anchors
        //nothing to do if it is LEFT, that is just absolute
        if (anchors.isCenterX()) {
            x = x + (parentW / 2);
        } else if (anchors.isRight()) {
            x = -x;
            x = parentW + x;
        }

        GL11.glTranslatef(x, y, 0);
        GL11.glTranslatef(width * anchors.getAlignX(), height * anchors.getAlignY(), 0);
        return new float[]{x, y, width, height};
    }

    /**
     * Returns whether a point (which is untranslated) is inside the bounds of X, Y, X+Width and Y+Height. Override if your element uses custom bounds
     * @param pointX the X position of the point
     * @param pointY the Y position of the point
     * @param x the X position of the bounds (left)
     * @param y the Y position of the bounds (top)
     * @param width the width of the bounds (right)
     * @param height the height of the bounds (bottom)
     * @return true if the point is bounded
     */
    public boolean isBounded(float pointX, float pointY, float x, float y, float width, float height) {
        float fixedMouseX = pointX - getAnchoredX() - getAnchoredWidth() * anchors.getAlignX();
        float fixedMouseY = pointY - getAnchoredY() - getAnchoredHeight() * anchors.getAlignY();
        if(getParent() != null){
            fixedMouseX -= parent.getAnchoredX() + parent.getAnchoredWidth() * parent.anchors.getAlignX();
            fixedMouseY -= parent.getAnchoredY() + parent.getAnchoredHeight() * parent.anchors.getAlignY();
        }
        return fixedMouseX >= x && fixedMouseY >= y && fixedMouseX < x+width && fixedMouseY < y+height;
    }
    /**
     * Returns whether a point (which is untranslated) is inside the element's bounds. Override if your element uses custom bounds
     */
    public boolean isBounded(float mouseX, float mouseY) {
        return isBounded(mouseX, mouseY, 0, 0, getAnchoredWidth(), getAnchoredHeight());
    }

    /**
     * Returns whether your mouse is hovering the element
     * @return true if the mouse is hovering the element
     */
    public boolean isHovered() {
        return isBounded(Input.getMouseX(), Input.getMouseY());
    }
    /**
     * Runs before processInput. Used by subclasses to modify the input data before sending it to custom instances.
     * @param x the anchored x position of the element
     * @param y the anchored y position of the element
     * @param w the anchored width of the element
     * @param h the anchored height of the element
     * @param sw the width scaling factor
     * @param sh the height scaling factor
     */
    protected void preProcessInput(float x, float y, float w, float h, float sw, float sh) {
        processInput(x, y, w, h, sw, sh);
    }
    /**
     * Handles input processing. Override if your element uses input processing
     */
    public void processInput(float x, float y, float w, float h, float sw, float sh) {
        markInputAsProcessed();
    }

    /**
     * Marks the input as processed to debug input errors.
     */
    public final void markInputAsProcessed(){
        processedInput = true;
    }

    /**
     * Adds an element to the foreground layer (children that will be drawn on top of this element).
     * @param element the element to add
     * @return this
     */
    public Element addForeground(Element element) {
        element.setParent(this);
        foregroundChildren.add(element);
        return this;
    }

    /**
     * Adds an element to the background layer (children that will be drawn below this element).
     * @param element the element to add
     * @return this
     */
    public Element addBackground(Element element) {
        element.setParent(this);
        backgroundChildren.add(element);
        return this;
    }

    /**
     * Removes an element from the foreground layer (children that will be drawn on top of this element).
     * @param id the identifier of the element
     * @return the element that was removed, or null if no element was removed
     */
    public Element removeForeground(String id){
        for(Element e : getForegroundChildren()){
            if(e.getID().equalsIgnoreCase(id)){
                getForegroundChildren().remove(e);
                e.setParent(null);
                return e;
            }
        }
        return null;
    }
    /**
     * Removes an element from the background layer (children that will be drawn below this element).
     * @param id the identifier of the element
     * @return the element that was removed, or null if no element was removed
     */
    public Element removeBackground(String id){
        for(Element e : getBackgroundChildren()){
            if(e.getID().equalsIgnoreCase(id)){
                getBackgroundChildren().remove(e);
                e.setParent(null);
                return e;
            }
        }
        return null;
    }

    /**
     * Checks if the element has a parent
     * @return true if it has a parent
     */
    public boolean hasParent(){
        return getParent() != null;
    }

    /**
     * Ran while cleaning up
     */
    public void destroy(){

    }

    public void cleanUp() {
        for(Element e : backgroundChildren){
            e.cleanUp();
        }
        for(Element e : foregroundChildren){
            e.cleanUp();
        }
        destroy();
    }
}
