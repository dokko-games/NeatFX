package com.dokko.neatfx.core.window.element.impl.basic;

import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.element.Element;
import com.dokko.neatfx.engine.render.Renderer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A simple class that renders a button, in the shape of a rectangle with a text.
 * @see Element
 */
@Accessors(chain = true)
@Getter
@Setter
public class Button extends Element {

    public Button(String text, float x, float y, float width, float height, Anchors anchors) {
        super(x, y, width, height, anchors);
        Text tex = new Text(text, 0, 0, 0, 0, Anchors.CENTERED).setShadow(true);
        addForeground(tex);
        if(width == 0){
            setWidth(tex.getFont().getWidth(text, anchors.isWidthScaled() && anchors.isHeightScaled()) + 15);
        }
        if(height == 0){
            setHeight(tex.getFont().getHeight(text, anchors.isWidthScaled() && anchors.isHeightScaled()) + 10);
        }
    }

    @Override
    protected void doRender(float scalingFactorW, float scalingFactorH) {
        Renderer.color(getBackground());

        Renderer.rect(0, 0, getAnchoredWidth(), getAnchoredHeight());
    }
    @Override
    public final void processInput(float x, float y, float w, float h, float sw, float sh) {
        super.processInput(x, y, w, h, sw, sh);
        if(!isHovered())return;
        onInput(x, y, w, h, sw, sh);
    }

    /**
     * The method used for input processing
     * @param x the anchored x position of this element
     * @param y the anchored y position of this element
     * @param w the anchored width of this element
     * @param h the anchored height of this element
     * @param sw the horizontal scaling factor of the window
     * @param sh the vertical scaling factor of the window
     */
    public void onInput(float x, float y, float w, float h, float sw, float sh) {
    }
}
