package com.dokko.win4jui.core.window.element.impl.basic;

import com.dokko.win4jui.core.window.Anchors;
import com.dokko.win4jui.core.window.element.Element;
import com.dokko.win4jui.engine.Color4;
import com.dokko.win4jui.engine.render.Renderer;
import com.dokko.win4jui.engine.render.text.BFont;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lwjgl.opengl.GL11;

import java.awt.*;


@Accessors(chain = true)
@Getter @Setter
public class Text extends Element {
    /**
     * The text to render
     */
    private String text;
    /**
     * The bitmap font to use
     */
    private BFont font;
    private static final float shadowOffset = 1.7f;
    /**
     * The point at which the text will be wrapped to a new line or stopped from rendering
     */
    private float maxWidth, maxHeight;
    /**
     * Whether the text has a shadow or not
     */
    private boolean shadow;

    public Text(String text, float x, float y, float maxWidth, float maxHeight, Anchors anchors) {
        super(x, y, 0, 0, anchors.removeScaling());
        this.text = text;
        if(maxWidth == 0) maxWidth = Float.MAX_VALUE;
        if(maxHeight == 0) maxHeight = Float.MAX_VALUE;
        setMaxWidth(maxWidth);
        setMaxHeight(maxHeight);
        setFont(BFont.loadFont("Mono", Font.BOLD, 19));
        setForeground(Color4.WHITE);
        setBackground(Color4.NONE);
        if(getFont().getFontOffset() == 0)getFont().setFontOffset(4);
    }

    @Override
    protected void doRender(float scalingFactorW, float scalingFactorH) {
        setWidth(Math.min(maxWidth, getFont().getWidth(text)));
        setHeight(Math.min(maxHeight, getFont().getHeight(text)));
        renderBackground();
        font.darken = true;
        font.darkenLayers = 3;
        renderTextShadow();
        font.darken = false;
        renderText();
    }
    private void renderBackground() {
        if (text == null || text.isEmpty()) return;
        if(getBackground().equals(Color4.NONE))return;
        Renderer.color(getBackground());
        Renderer.rect(0, 0, getAnchoredWidth(), getAnchoredHeight());
    }

    private void renderTextShadow() {
        if(!shadow)return;
        if (text == null || text.isEmpty()) return;
        Renderer.color(getForeground().darker(3)); // Semi-transparent black shadow
        GL11.glPushMatrix();
        GL11.glTranslatef(-shadowOffset, shadowOffset, 0);
        getFont().drawString(text, 0, 0, maxWidth, maxHeight);
        GL11.glPopMatrix();
    }

    private void renderText() {
        if (text == null || text.isEmpty()) return;
        Renderer.color(getForeground());
        getFont().drawString(text, 0, 0, maxWidth, maxHeight);
    }

    @Override
    public void destroy() {
        font.dispose();
    }
}
