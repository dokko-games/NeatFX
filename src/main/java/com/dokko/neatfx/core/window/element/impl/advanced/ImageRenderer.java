package com.dokko.neatfx.core.window.element.impl.advanced;

import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.element.Element;
import com.dokko.neatfx.engine.render.Renderer;
import com.dokko.neatfx.engine.render.texture.Texture;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * Renders an image
 */
@Getter @Setter
public class ImageRenderer extends Element {
    /**
     * The texture of the image. Get one by using {@link Texture#loadTexture(String)} or {@link Texture#createTexture(int, int, ByteBuffer)}
     */
    private Texture texture;
    /**
     * The U coordinate of the texture (first horizontal pixel)
     */
    private float u;
    /**
     * The V coordinate of the texture (first vertical pixel)
     */
    private float v;
    /**
     * The final U coordinate of the texture (last horizontal pixel)
     */
    private float u2;
    /**
     * The final V coordinate of the texture (last vertical pixel)
     */
    private float v2;
    /**
     * The width of the texture. If it is half of the element's width, then it will tile 2x
     */
    private float tWidth;
    /**
     * The height of the texture. If it is half of the element's height, then it will tile 2x
     */
    private float tHeight;

    /**
     * Constructs a new renderer
     * @param texture the texture
     * @param x the X position
     * @param y the Y position
     * @param width the width of the renderer
     * @param height the height of the renderer
     * @param anchors where the element is anchored to
     * @see ImageRenderer#ImageRenderer(Texture, float, float, float, float, float, float, float, float, float, float, Anchors)  ImageRenderer(TffffffffffA);
     */
    public ImageRenderer(Texture texture, float x, float y, float width, float height, Anchors anchors) {
        this(texture, x, y, width, height, 0, 0, 0, 0, 0, 0, anchors);
    }

    /**
     * Constructs a new renderer
     * @param texture the texture
     * @param x the X position
     * @param y the Y position
     * @param width the width of the renderer
     * @param height the height of the renderer
     * @param u The U coordinate of the texture (first horizontal pixel)
     * @param v The V coordinate of the texture (first vertical pixel)
     * @param u2 The final U coordinate of the texture (last horizontal pixel)
     * @param v2 The final V coordinate of the texture (last vertical pixel)
     * @param tWidth The width of the texture. If it is half of the element's width, then it will tile 2x
     * @param tHeight The height of the texture. If it is half of the element's height, then it will tile 2x
     * @param anchors where the element is anchored to
     * @see ImageRenderer#ImageRenderer(Texture, float, float, float, float, Anchors)  ImageRenderer(TffffA)
     */
    public ImageRenderer(Texture texture, float x, float y, float width, float height,
                         float u, float v, float u2, float v2, float tWidth, float tHeight, Anchors anchors) {
        super(x, y, width, height, anchors);
        setTexture(texture);
        this.u = u;
        this.v = v;
        if(u2 == 0)u2 = texture.getWidth();
        if(v2 == 0)v2 = texture.getHeight();
        this.u2 = u2;
        this.v2 = v2;
        if(tWidth == 0)tWidth = width;
        if(tHeight == 0)tHeight = height;
        this.tWidth = tWidth;
        this.tHeight = tHeight;
    }

    @Override
    protected void doRender(float scalingFactorW, float scalingFactorH) {
        texture.bind();
        Renderer.drawTexturedRect(0, 0, getAnchoredWidth(), getAnchoredHeight(), u, v, u2, v2, tWidth, tHeight, texture);
        texture.unbind();
    }

    @Override
    public void destroy() {
        texture.delete();
    }
}
