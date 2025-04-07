package com.dokko.win4jui.engine.render;

import static org.lwjgl.opengl.GL11.*;

/**
 * Helper class for OpenGL functionality
 */
public class GraphicsManager {

    /**
     * Renders the lines (edges) of models.
     * @see GraphicsManager#renderMode(int)
     */
    public static final int RENDER_WIREFRAME = 0x00;
    /**
     * Renders the full model.
     * @see GraphicsManager#renderMode(int)
     */
    public static final int RENDER_FILL = 0x01;
    /**
     * Renders the points (vertices) of models.
     * @see GraphicsManager#renderMode(int)
     */
    public static final int RENDER_VERTEX = 0x02;

    /**
     * Changes the rendering mode.
     * @param mode the mode for rendering.
     * @see GraphicsManager#RENDER_FILL
     * @see GraphicsManager#RENDER_WIREFRAME
     * @see GraphicsManager#RENDER_VERTEX
     */
    public static void renderMode(int mode) {
        if(mode == RENDER_WIREFRAME) {
            GL_POLY_MODE(GL_FRONT_AND_BACK, GL_LINE);
            GL_POLY_MODE(GL_LEFT, GL_LINE);
            GL_POLY_MODE(GL_RIGHT, GL_LINE);
        } else if (mode == RENDER_FILL) {
            GL_POLY_MODE(GL_FRONT_AND_BACK, GL_FILL);
            GL_POLY_MODE(GL_LEFT, GL_FILL);
            GL_POLY_MODE(GL_RIGHT, GL_FILL);
        } else if (mode == RENDER_VERTEX) {
            GL_POLY_MODE(GL_FRONT_AND_BACK, GL_POINT);
            GL_POLY_MODE(GL_LEFT, GL_POINT);
            GL_POLY_MODE(GL_RIGHT, GL_POINT);
        }
    }

    /**
     * Calls glPolygonMode
     * @param face the face to modify
     * @param mode the mode to use
     */
    public static void GL_POLY_MODE(int face, int mode) {
        glPolygonMode(face, mode);
    }

}
