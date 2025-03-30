package com.dokko.win4jui.api.ui.element.impl;

import com.dokko.win4jui.api.image.Image4JUI;
import com.dokko.win4jui.api.image.ImageFilter4JUI;
import com.dokko.win4jui.api.render.Renderer2D;
import com.dokko.win4jui.api.ui.element.Anchors;
import com.dokko.win4jui.api.ui.element.Element4JUI;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.image.BufferedImage;

@Getter
@Setter
@Accessors(chain = true)
public class ImageRenderer4JUI extends Element4JUI {
    private Image4JUI image;
    private float u, v, textureWidth, textureHeight;
    private ImageFilter4JUI filter;

    /**
     * Constructs a new UI Image Renderer element with the given properties.
     *
     * @param xDistance X position relative to the parent container and anchors.
     * @param yDistance Y position relative to the parent container and anchors.
     * @param width     Width of the element.
     * @param height    Height of the element.
     * @param anchors   Anchor properties for positioning.
     */
    public ImageRenderer4JUI(Image4JUI image, float xDistance, float yDistance, float width, float height, float u, float v, float textureWidth, float textureHeight, Anchors anchors) {
        super(xDistance, yDistance, width, height, anchors);
        setImage(image);
        setU(u); setV(v); setTextureWidth(textureWidth); setTextureHeight(textureHeight);
    }

    @Override
    protected void doRender(Renderer2D renderer, float x, float y, float width, float height, float scalingX, float scalingY) {
        if (image == null) {
            return;
        }
        Image4JUI renderedImage = image.copy();
        if(getFilter() != null) {
            getFilter().process(renderedImage);
        }

        BufferedImage bufferedImage = renderedImage.toBuffer();
        if (bufferedImage == null) {
            return;
        }

        int imgWidth = bufferedImage.getWidth();
        int imgHeight = bufferedImage.getHeight();

        int srcX = (int) (u * imgWidth);
        int srcY = (int) (v * imgHeight);
        int srcWidth = (int) (textureWidth * imgWidth);
        int srcHeight = (int) (textureHeight * imgHeight);

        renderer.drawImage(renderedImage, x, y, width, height, srcX, srcY, srcX + srcWidth, srcY + srcHeight);
    }
}