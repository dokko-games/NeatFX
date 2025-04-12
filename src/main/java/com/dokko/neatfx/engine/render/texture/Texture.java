package com.dokko.neatfx.engine.render.texture;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.system.MemoryStack;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * This class represents a texture. Source: <a href="https://github.com/SilverTiger/lwjgl3-tutorial/blob/master/src/silvertiger/tutorial/lwjgl/graphic/Texture.java#L39">...</a>
 *
 * @author Heiko Brumme
 */
public class Texture {

    /**
     * Stores the handle of the texture.
     */
    private final int id;

    /**
     * Width of the texture.
     *  Gets the texture width.
     *
     * @return Texture width

     */
    @Getter
    private int width;
    /**
     * Height of the texture.
     *  Gets the texture height.
     *
     * @return Texture height

     */
    @Getter
    private int height;
    @Getter @Setter
    private byte[] data;

    /** Creates a texture. */
    public Texture() {
        id = glGenTextures();
    }

    /**
     * Binds the texture.
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    /**
     * Unbinds the texture.
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Sets a parameter of the texture.
     *
     * @param name  Name of the parameter
     * @param value Value to set
     */
    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }

    /**
     * Uploads image data with specified width and height.
     *
     * @param width  Width of the image
     * @param height Height of the image
     * @param data   Pixel data of the image
     */
    public void uploadData(int width, int height, ByteBuffer data) {
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }

    /**
     * Uploads image data with specified internal format, width, height and
     * image format.
     *
     * @param internalFormat Internal format of the image data
     * @param width          Width of the image
     * @param height         Height of the image
     * @param format         Format of the image data
     * @param data           Pixel data of the image
     */
    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        //data.rewind();
        byte[] d = new byte[data.remaining()];
        data.get(d);
        setData(d);
        data.rewind();
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

    /**
     * Delete the texture.
     */
    public void delete() {
        glDeleteTextures(id);
    }

    /**
     * Sets the texture width.
     *
     * @param width The width to set
     */
    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    /**
     * Sets the texture height.
     *
     * @param height The height to set
     */
    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

    /**
     * Creates a texture with specified width, height and data.
     *
     * @param width  Width of the texture
     * @param height Height of the texture
     * @param data   Picture Data in RGBA format
     *
     * @return Texture from the specified data
     */
    public static Texture createTexture(int width, int height, ByteBuffer data) {
        if (data == null || data.remaining() < width * height * 4) {
            throw new IllegalArgumentException("Texture data is incomplete or missing!");
        }
        Texture texture = new Texture();
        texture.setWidth(width);
        texture.setHeight(height);

        texture.bind();

        texture.setParameter(GL_TEXTURE_WRAP_S, GL_REPEAT);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_REPEAT);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return texture;
    }
    /**
     * Creates a texture from an array of ARGB integers.
     *
     * @param width  Width of the texture
     * @param height Height of the texture
     * @param data   Pixel data in 0xAARRGGBB format
     * @return Texture from the specified integer data
     */
    public static Texture createTexture(int width, int height, int... data) {
        if (data == null || data.length != width * height) {
            throw new IllegalArgumentException("Invalid texture data size.");
        }

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);

        for (int argb : data) {
            // Extract components
            int a = (argb >> 24) & 0xFF;
            int r = (argb >> 16) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int b = argb & 0xFF;

            // OpenGL expects RGBA ordering
            buffer.put((byte) r);
            buffer.put((byte) g);
            buffer.put((byte) b);
            buffer.put((byte) a);
        }

        buffer.flip(); // Prepare the buffer for reading

        return createTexture(width, height, buffer);
    }

    /**
     * Load texture from file.
     *
     * @param path File path of the texture
     *
     * @return Texture from specified file
     */
    public static Texture loadTexture(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
            //stbi_set_flip_vertically_on_load(true);
            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load a texture file!"
                        + System.lineSeparator() + stbi_failure_reason());
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }

        return createTexture(width, height, image);
    }

    /**
     * Saves the texture to a file
     * @param filePath the path of the target file
     */
    public void saveToFile(String filePath) {
        File f = new File(filePath);
        f.getParentFile().mkdirs();
        int width = getWidth();
        int height = getHeight();

        // Allocate a ByteBuffer to hold pixel data
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4); // RGBA = 4 bytes

        // Bind the texture
        bind();

        // Read pixels from texture into buffer
        glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);


        // Write the image to file using STB
        boolean success = STBImageWrite.stbi_write_png(filePath, width, height, 4, buffer, width * 4);

        if (!success) {
            throw new RuntimeException("Failed to write texture to file: " + filePath);
        }
    }

}