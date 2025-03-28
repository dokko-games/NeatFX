package com.dokko.win4jui.api.render;

import com.dokko.win4jui.api.image.Image4JUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
@AllArgsConstructor
public class Renderer2D {

    private Graphics2D javaGraphics;

    public void color(Color color){
        javaGraphics.setColor(color);
    }
    public void color(int r, int g, int b) {
        color(new Color(r, g, b));
    }
    public void color(float r, float g, float b){
        color(new Color(r, g, b));
    }
    public void color(int code) {
        color(new Color(code));
    }

    public void drawRect(float x, float y, float width, float height) {
        javaGraphics.fillRect((int) x, (int) y, (int) width, (int) height);
    }
    public void drawRectOutline(float x, float y, float width, float height) {
        javaGraphics.drawRect((int) x, (int) y, (int) width, (int) height);
    }
    /**
     * Draws a portion of an image onto the screen.
     *
     * @param image The source BufferedImage to render.
     * @param destX The x-coordinate of the destination area on the screen.
     * @param destY The y-coordinate of the destination area on the screen.
     * @param destWidth The width of the destination area on the screen.
     * @param destHeight The height of the destination area on the screen.
     * @param srcX The x-coordinate of the top-left corner of the source region in the image.
     * @param srcY The y-coordinate of the top-left corner of the source region in the image.
     * @param srcWidth The width of the source region in the image.
     * @param srcHeight The height of the source region in the image.
     */
    public void drawImage(Image4JUI image, float destX, float destY, float destWidth, float destHeight, int srcX, int srcY, int srcWidth, int srcHeight) {
        javaGraphics.drawImage(image.toBuffer(), (int) destX, (int) destY, (int) (destX + destWidth), (int) (destY + destHeight), srcX, srcY, srcX + srcWidth, srcY + srcHeight, null);
    }
}
