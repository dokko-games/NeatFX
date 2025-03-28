package com.dokko.win4jui.api.image;

import com.dokko.win4jui.api.Logger4JUI;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

@Getter
@AllArgsConstructor
public class Image4JUI {
    private final byte[] imageData;
    private final int width, height;

    public static Image4JUI fromFile(File path){
        try {
            BufferedImage img = ImageIO.read(path);
            return fromBuffer(img);
        } catch (Exception e) {
            Logger4JUI.error("Image error: %{0}", e.getMessage());
            return null; //TODO: return gmod missing texture
        }
    }

    public static Image4JUI fromFile(String path) {
        return fromFile(new File(path));
    }

    public static Image4JUI fromData(int width, int height, int... data) {
        byte[] imageData = new byte[data.length * 4];

        for (int i = 0; i < data.length; i++) {
            int pixel = data[i];
            imageData[i * 4] = (byte) ((pixel >> 24) & 0xFF);
            imageData[i * 4 + 1] = (byte) ((pixel >> 16) & 0xFF);
            imageData[i * 4 + 2] = (byte) ((pixel >> 8) & 0xFF);
            imageData[i * 4 + 3] = (byte) (pixel & 0xFF);
        }
        return new Image4JUI(imageData, width, height);
    }

    public BufferedImage toBuffer() {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

            for (int i = 0; i < pixels.length && i * 4 + 3 < imageData.length; i++) {
                int a = imageData[i * 4] & 0xFF;
                int r = imageData[i * 4 + 1] & 0xFF;
                int g = imageData[i * 4 + 2] & 0xFF;
                int b = imageData[i * 4 + 3] & 0xFF;
                pixels[i] = (a << 24) | (r << 16) | (g << 8) | b;
            }
            return image;
        } catch (Exception e) {
            Logger4JUI.error("Failed to convert Image4JUI to BufferedImage: %{0}", e.getMessage());
            return null;
        }
    }

    public static Image4JUI fromBuffer(BufferedImage image) {
        try {
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
            byte[] imageData = new byte[pixels.length * 4];

            for (int i = 0; i < pixels.length; i++) {
                int pixel = pixels[i];
                imageData[i * 4] = (byte) ((pixel >> 24) & 0xFF);
                imageData[i * 4 + 1] = (byte) ((pixel >> 16) & 0xFF);
                imageData[i * 4 + 2] = (byte) ((pixel >> 8) & 0xFF);
                imageData[i * 4 + 3] = (byte) (pixel & 0xFF);
            }

            return new Image4JUI(imageData, width, height);
        } catch (Exception e) {
            Logger4JUI.error("Failed to create Image4JUI from BufferedImage: %{0}", e.getMessage());
            return null;
        }
    }
}