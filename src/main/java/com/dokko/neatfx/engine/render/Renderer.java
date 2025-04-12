package com.dokko.neatfx.engine.render;

import com.dokko.neatfx.engine.Color4;
import com.dokko.neatfx.engine.render.texture.Texture;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;


/**
 * Helper class for OpenGL rendering
 */
public class Renderer {
    @Getter
    private static Color4 color;
    @Getter
    @Setter
    private static int windowWidth, windowHeight;
    /**
     * Draws a rectangle
     * @param x the X position (left)
     * @param y the Y position (top)
     * @param width the width of the rectangle (right)
     * @param height the height of the rectangle (bottom)
     */
    public static void rect(float x, float y, float width, float height) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);  // top-left
        GL11.glVertex2f(x+width, y);  // top-right
        GL11.glVertex2f(x+width, y+height);  // bottom-right
        GL11.glVertex2f(x, y+height);  // bottom-left
        GL11.glEnd();
    }
    /**
     * Draws a rounded rectangle
     * @param x the X position (left)
     * @param y the Y position (top)
     * @param width the width of the rectangle (right)
     * @param height the height of the rectangle (bottom)
     * @param radius the radius of the borders
     */
    public static void roundedRect(float x, float y, float width, float height, float radius) {
        int segments = 10; // Adjust for smoother curves

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        // Center point to ensure proper triangulation
        GL11.glVertex2f(x + width / 2, y + height / 2);

        // Top-left corner
        for (int i = 0; i <= segments; i++) {
            float angle = (float) (Math.PI / 2 * i / segments);
            GL11.glVertex2f(x + radius - (float) Math.cos(angle) * radius, y + radius - (float) Math.sin(angle) * radius);
        }

        // Top edge
        GL11.glVertex2f(x + radius, y);
        GL11.glVertex2f(x + width - radius, y);

        // Top-right corner
        for (int i = 0; i <= segments; i++) {
            float angle = (float) (Math.PI / 2 * i / segments);
            GL11.glVertex2f(x + width - radius + (float) Math.sin(angle) * radius, y + radius - (float) Math.cos(angle) * radius);
        }

        // Right edge
        GL11.glVertex2f(x + width, y + radius);
        GL11.glVertex2f(x + width, y + height - radius);

        // Bottom-right corner
        for (int i = 0; i <= segments; i++) {
            float angle = (float) (Math.PI / 2 * i / segments);
            GL11.glVertex2f(x + width - radius + (float) Math.cos(angle) * radius, y + height - radius + (float) Math.sin(angle) * radius);
        }

        // Bottom edge
        GL11.glVertex2f(x + width - radius, y + height);
        GL11.glVertex2f(x + radius, y + height);

        // Bottom-left corner
        for (int i = 0; i <= segments; i++) {
            float angle = (float) (Math.PI / 2 * i / segments);
            GL11.glVertex2f(x + radius - (float) Math.sin(angle) * radius, y + height - radius + (float) Math.cos(angle) * radius);
        }

        // Left edge
        GL11.glVertex2f(x, y + height - radius);
        GL11.glVertex2f(x, y + radius);

        GL11.glEnd();
    }
    /**
     * Draws a circle
     * @param x the X position
     * @param y the Y position
     * @param radius both the width and the height of the circle
     * @param vertices the amount of vertices in the circle
     * @see Renderer#circle(float, float, float, float, int)
     */
    public static void circle(float x, float y, float radius, int vertices){
        circle(x, y, radius, radius, vertices);
    }
    /**
     * Draws a circle
     * @param x the X position
     * @param y the Y position
     * @param width the width of the circle
     * @param height the height of the circle
     * @param vertices the amount of vertices in the circle
     */
    public static void circle(float x, float y, float width, float height, int vertices) {
        x += width / 2;
        width /= 2;
        y += height / 2;
        height /= 2;

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        // Center of the circle
        GL11.glVertex2f(x, y);

        for (int i = 0; i <= vertices; i++) { // Loop goes to vertices + 1 for a full circle
            float current = (float) i / vertices * 2 * (float) Math.PI;
            GL11.glVertex2f((float) (x + Math.cos(current) * width), (float) (y + Math.sin(current) * height));
        }

        GL11.glEnd();
    }


    /**
     * Sets the color of the renderer
     * @param color the color to use, in RGBA space. if you want to use RGB space, use {@link Color4#color3()}
     */
    public static void color(Color4 color) {
        GL11.glColor4f(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), color.getAlphaFloat());
        Renderer.color = color;
    }

    /**
     * Draws a rect with a texture. <b>Make sure to bind the texture before running this and unbind it afterwards</b>
     * @param x the X position of the texture rectangle
     * @param y the Y position of the texture rectangle
     * @param width the width of the texture rectangle
     * @param height the height of the texture rectangle
     * @param u the U position of the texture
     * @param v the V position of the texture
     * @param u2 the last U position of the texture
     * @param v2 the last V position of the texture
     * @param textureWidth the width of the texture. If less than width the texture will tile
     * @param textureHeight the height of the texture. If less than height the texture will tile
     * @param texture the texture to render
     */
    public static void drawTexturedRect(
            float x, float y, float width, float height,       // Quad position and size
            float u, float v, float u2, float v2,               // Texture region to sample (pixels)
            float textureWidth, float textureHeight,            // Virtual tiling size
            Texture texture                                     // The OpenGL texture (provides getWidth/getHeight)
    ) {

        // Get the actual texture dimensions (these are usually power-of-two sizes)
        float texImageWidth  = texture.getWidth();  // the actual image width
        float texImageHeight = texture.getHeight(); // the actual image height

        // Compute size of the UV region in pixels
        float regionWidth  = u2 - u;
        float regionHeight = v2 - v;

        // Determine how many times to tile across the quad
        float tileX = width  / textureWidth;
        float tileY = height / textureHeight;

        // Normalize texture coordinates
        float uNorm  = u / texImageWidth;
        float vNorm  = v / texImageHeight;
        float uEnd   = uNorm + tileX * (regionWidth / texImageWidth);
        float vEnd   = vNorm + tileY * (regionHeight / texImageHeight);

        // Ensure wrapping mode is set to repeat (if needed)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // Render the quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(uNorm, vNorm); GL11.glVertex2f(x,         y);          // Top-left
        GL11.glTexCoord2f(uEnd,  vNorm); GL11.glVertex2f(x + width, y);          // Top-right
        GL11.glTexCoord2f(uEnd,  vEnd);  GL11.glVertex2f(x + width, y + height); // Bottom-right
        GL11.glTexCoord2f(uNorm, vEnd);  GL11.glVertex2f(x,         y + height); // Bottom-left
        GL11.glEnd();
    }



}
