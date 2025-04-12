package com.dokko.neatfx.core.window.element.impl.basic;

import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.element.Element;
import com.dokko.neatfx.engine.render.Renderer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A simple class that renders a panel, in the shape of a rectangle.
 * @see Element
 */
@Accessors(chain = true)
@Getter
@Setter
public class Panel extends Element {

    public Panel(float x, float y, float width, float height, Anchors anchors) {
        super(x, y, width, height, anchors);
    }

    @Override
    protected void doRender(float scalingFactorW, float scalingFactorH) {
        Renderer.color(getBackground());

        Renderer.rect(0, 0, getAnchoredWidth(), getAnchoredHeight());
    }
}
