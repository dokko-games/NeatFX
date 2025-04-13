package com.dokko.neatfx.engine.render.text;

import com.dokko.neatfx.NeatFX;
import com.dokko.neatfx.NeatLogger;
import com.dokko.neatfx.engine.util.NeatFiles;
import com.dokko.neatfx.engine.util.NeatNumbers;
import com.dokko.neatfx.core.error.Error;
import com.dokko.neatfx.engine.Color4;
import com.dokko.neatfx.engine.render.Renderer;
import com.dokko.neatfx.engine.render.texture.Texture;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Font.*;

/**
 * A class that represents a bitmap font, which allows for changing text color while rendering
 */
public class BFont {
    public static boolean STORE_FONTS = false;
    private static final HashMap<String, BFont> fonts = new HashMap<>();
    /**
     * The name of the font. Used for font caching
     */
    @Getter @Setter
    private String fontName;
    /**
     * The style of the font. Uses Java's Font.Style
     */
    @Getter @Setter
    private int fontStyle;
    /**
     * The size of the font
     */
    @Getter @Setter
    private int fontSize;
    /**
     * If true, the font will have some smooth borders around characters
     */
    @Getter @Setter
    private boolean antialiased;
    /**
     * The glyphs in this font. Used for character rendering
     */
    @Getter @Setter
    private Map<Character, Glyph> glyphs;
    /**
     * The height of the font
     */
    @Getter @Setter
    private int fontHeight;
    /**
     * The offset between each line
     */
    @Getter @Setter
    private int fontOffset;
    /**
     * The metadata of the font
     */
    @Getter @Setter
    private FontMeta fontMeta;

    private final File fontFile;
    private final Texture texture;

    /**
     * Constructs a new antialiased font from a java font
     * @param name the name of the AWT font
     * @param size the size of the font
     */
    public BFont(String name, int size) {
        this(new Font(name, PLAIN, size), true);
    }

    /**
     * Constructs a new antialiased monospace font with size 16
     * @see BFont#BFont(boolean)  BFont(antiAlias)
     */
    public BFont() {
        this(new Font(MONOSPACED, PLAIN, 16), true);
    }
    /**
     * Constructs a new monospace font with size 16
     * @param antiAlias whether the font is antialiased or not
     * @see BFont#antialiased
     * @see BFont#BFont(int)  BFont(size)
     */
    public BFont(boolean antiAlias) {
        this(new Font(MONOSPACED, PLAIN, 16), antiAlias);
    }

    /**
     * Constructs a new antialiased monospace font
     * @param size the size of the font
     * @see BFont#BFont(int, boolean)  BFont(size, antiAlias)
     */
    public BFont(int size) {
        this(new Font(MONOSPACED, PLAIN, size), true);
    }

    /**
     * Constructs a new monospace font
     * @param size the size of the font
     * @param antiAlias if the font is antialiased
     */
    public BFont(int size, boolean antiAlias) {
        this(new Font(MONOSPACED, PLAIN, size), antiAlias);
    }
    /**
     * Constructs a new antialiased monospace font
     * @param size the size of the font
     * @param style the style of the font
     */
    public BFont(int size, int style){
        this(new Font(MONOSPACED, style, size), true);
    }

    /**
     * Constructs a new antialiased font
     * @param in the input stream for the AWT font
     * @param size the size of the font
     * @throws FontFormatException if the InputStream has a wrong format
     * @throws IOException if the font stream cannot be read
     */
    public BFont(InputStream in, int size) throws FontFormatException, IOException {
        this(in, size, true);
    }

    /**
     * Creates a new font
     * @param in the input stream for the AWT font
     * @param size the size of the font
     * @param antiAlias if true, the font will have smooth borders
     * @throws FontFormatException if the InputStream has a wrong format
     * @throws IOException if the font stream cannot be read
     */
    public BFont(InputStream in, int size, boolean antiAlias) throws FontFormatException, IOException {
        this(createFont(TRUETYPE_FONT, in).deriveFont(PLAIN, size), antiAlias);
    }
    /**
     * Creates a new antialiased font from an AWT one
     * @param font the AWT font
     */
    public BFont(Font font) {
        this(font, true);
    }
    /**
     * Creates a new font from an AWT font
     * @param font the AWT font
     * @param antiAlias if true, the font will have smooth borders
     */
    public BFont(Font font, boolean antiAlias) {
        glyphs = new HashMap<>();
        setFontName(font.getFontName().replace(" ", ""));
        if(getFontName().contains(".")){
            setFontName(getFontName().split("\\.")[0]);
        }
        if(getFontName().equalsIgnoreCase("monospaced")){
            setFontName("Mono");
        }
        setFontSize(font.getSize());
        setFontStyle(font.getStyle());
        fontFile = new File(NeatFX.getFilePath("fonts", getFontName()+"_"+str(font.getStyle())+"_"+font.getSize(), "fontmeta"));
        texture = createFontTexture(font, antiAlias);
        if (!checkFontExists()) {
            this.fontMeta = new FontMeta(FontMeta.META_VERSION, font.getFontName().replace(" ",""),
                    fontSize, fontStyle, glyphs, fontHeight, fontOffset, antialiased, texture.getData(), texture.getWidth(), texture.getHeight());
            saveMetaToFile();
        }
    }

    /**
     * Loads a font
     * @param name the name of the font
     * @param size the size of the font
     * @return the loaded font
     */
    public static @NonNull BFont loadFont(String name, int size) {
        return loadFont(name, PLAIN, size);
    }

    /**
     * Loasd a font
     * @param name the name of the font
     * @param style the style of the font (Font.PLAIN, Font.BOLD...)
     * @param size the size of the font
     * @return the loaded or created font
     */
    public static @NonNull BFont loadFont(String name, int style, int size) {
        if(name.equalsIgnoreCase("mono")){
            return loadFont(new BFont(size, style));
        }
        if(fonts.isEmpty()){
            loadFont(new BFont());
        }
        if(fonts.containsKey(name.replace(" ", "")+"_"+str(style)+"_"+size)){
            return fonts.get(name);
        }
        BFont b = new BFont(new Font(name, style, size));
        return loadFont(b);
    }

    /**
     * Registers a font and loads it
     * @param font the bitmap font
     * @return the loaded font
     */
    public static @NonNull BFont loadFont(BFont font){
        fonts.put(font.getFontName()+"_"+str(font.getFontStyle())+"_"+font.getFontSize(), font);
        return font;
    }

    private static String str(int style) {
        return switch (style){
            case PLAIN -> "PLAIN";
            case BOLD | ITALIC -> "BITALIC";
            case BOLD -> "BOLD";
            case ITALIC -> "ITALIC";
            default -> "UNKNOWN";
        };
    }

    private void saveMetaToFile() {
        fontFile.getParentFile().mkdirs();
        if(STORE_FONTS){
            texture.saveToFile(fontFile.getPath().replace(".fontmeta", ".png"));
        }
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fontFile))){
            out.writeObject(fontMeta);
            out.writeInt(texture.getWidth());
            out.writeInt(texture.getHeight());

            byte[] data = texture.getData();

            out.writeInt(data.length); // Always store length
            out.write(data);
        }catch (Exception e){
            throw new Error(e, "unknown").getException();
        }
    }
    private Texture loadMetaFromFile() {
        long size = NeatFiles.size(fontFile.getParentFile().toPath());
        if(size > 4000000000L){
            int dataI = (int)(size / 10000000L);
            float dataF = (float)dataI / 100;
            NeatLogger.warn("More than 4GB of fonts found (%{0}GB). Clearing font data", dataF);
            NeatFiles.delete(fontFile.getParentFile());
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fontFile))) {
            this.fontMeta = (FontMeta) in.readObject();
            int w = in.readInt();
            int h = in.readInt();
            int dataLength = in.readInt(); // matches saved length
            int expectedLength = 4 * w * h;
            if (dataLength != expectedLength) {
                NeatLogger.error("Data length mismatch! Expected %{0}, but got %{1}", expectedLength, dataLength);
                return null;  // Bail out to avoid passing corrupt data to OpenGL
            }
            byte[] data = new byte[dataLength];
            in.readFully(data);
            ByteBuffer directBuffer = ByteBuffer.allocateDirect(data.length);
            directBuffer.put(data);
            directBuffer.flip();  // Very important!

            return Texture.createTexture(w, h, directBuffer);
        } catch (Exception e) {
            throw new Error(e, "unknown").getException();
        }
    }


    private Texture createFontTexture(Font font, boolean antiAlias) {
        if(checkFontExists()){
            Texture img = loadMetaFromFile();
            if(fontMeta != null)reProcessFontMeta();
            if(img != null){
                return img;
            }
            NeatLogger.warn("Recalculating font data for font %{0}.", font.getFontName());
        }
        int margin = 2;
        // First, determine how many characters can fit in one line
        int charsPerLine = NeatNumbers.nearestPerfectSquare(256, NeatNumbers.NPS_MODE_ABOVE); // Assuming we have 256 characters

        // Loop through characters to get charWidth and charHeight
        int imageWidth = 0, imageHeight;
        int maxHeight = 0, maxWidth = 0;
        int currentX = 0;
        int maxLine = 0;

        // Loop through all ASCII characters
        for (int i = 0; i < 256; i++) {
            if (i == 127) {
                continue; // Skip ASCII 127 (DEL control code)
            }
            if(currentX >= charsPerLine){
                currentX = 0;
                if(imageWidth > maxLine){
                    maxLine = imageWidth;
                }
                imageWidth = 0;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                continue;
            }

            // Determine the total width and maximum height required for the grid
            imageWidth += ch.getWidth();
            currentX++;
            maxHeight = Math.max(maxHeight, ch.getHeight());
            maxWidth = Math.max(maxWidth, ch.getWidth());
        }
        imageWidth = charsPerLine * (maxWidth + margin);
        imageHeight = charsPerLine * (maxHeight + margin);
        NeatLogger.warnDebug("Font %{0} %{1}: %{2}x%{3}",font.getFontName(), font.getSize(), imageWidth, imageHeight);

        // Create the image for the texture
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;
        int y = 0;
        currentX = 0;

        // Create image for standard characters and draw them in a grid layout
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue; // Skip ASCII 127
            }
            if(currentX >= charsPerLine) {
                currentX = 0;
                x = 0;
                y += maxHeight + margin;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                continue; // If char image is null, skip
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            // Create a glyph and draw the character on the image
            Glyph ch = new Glyph(charWidth, charHeight, x, y, 0f);
            g.drawImage(charImage, x, y, null);
            x += charWidth + margin;
            glyphs.put(c, ch);
            currentX++;
        }

        // Get pixel data from the image
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        // Put pixel data into a ByteBuffer
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i * width + j];
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
                buffer.put((byte) ((pixel >> 8) & 0xFF));  // Green
                buffer.put((byte) (pixel & 0xFF));         // Blue
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
            }
        }

        // Flip the buffer before using it
        buffer.flip();

        // Create the texture
        Texture fontTexture = Texture.createTexture(width, height, buffer);
        MemoryUtil.memFree(buffer);
        return fontTexture;
    }

    private void reProcessFontMeta() {
        if(fontMeta.fontVersion != FontMeta.META_VERSION){
            NeatLogger.error("Stored font metadata has a different version from the current: %{0} and %{1}", fontMeta.fontVersion, FontMeta.META_VERSION);
            NeatLogger.warn("Will try to read font. Expect crashes");
        }
        setFontName(fontMeta.fontName);
        setFontStyle(fontMeta.fontStyle);
        setFontSize(fontMeta.fontSize);
        setFontOffset(fontMeta.fontOffset);
        setGlyphs(fontMeta.glyphs);
        setFontHeight(fontMeta.fontHeight);
        setAntialiased(fontMeta.antialiased);
    }

    private boolean checkFontExists() {
        return fontFile.exists();
    }

    private BufferedImage createCharImage(Font font, char c, boolean antiAlias) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get char charWidth and charHeight */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        /* Check if charWidth is 0 */
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    /**
     * Gets the width of a text, ignoring color codes
     * @param text the text
     * @param scale whether the text is scaled
     * @return the width
     */
    public int getWidth(CharSequence text, boolean scale) {
        int width = 0;
        int lineWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (isColorCode(text, i)) {
                i += 8; // Skip over color marker
                continue;
            }

            if (c == '\n') {
                /* Line end, set width to maximum from line width and stored
                 * width */
                width = Math.max(width, lineWidth);
                lineWidth = 0;
                continue;
            }
            if (c == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(c);
            lineWidth += g.getWidth();
        }
        width = Math.max(width, lineWidth);
        return (int) (width * (scale ? Renderer.getScaleX() : 1));
    }
    /**
     * Gets the height of a text, ignoring color codes
     * @param text the text
     * @param scale whether the text is scaled
     * @return the height
     */
    public int getHeight(CharSequence text, boolean scale) {
        int height = 0;
        int lineHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isColorCode(text, i)) {
                i += 8; // Skip over color marker
                continue;
            }
            if (c == '\n') {
                /* Line end, add line height to stored height */
                height += lineHeight;
                lineHeight = 0;
                continue;
            }
            if (c == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(c);
            lineHeight = Math.max(lineHeight, g.getHeight());
        }
        height += lineHeight;
        return (int) (height * (scale ? Renderer.getScaleY() : 1));
    }

    /**
     * Ignore this, it is used by the library
     */
    public boolean darken;
    /**
     * Ignore this, it is used by the library
     */
    public int darkenLayers;

    /**
     * Draws a string
     * @param text the text to draw
     * @param x the X position of the string
     * @param y the Y position of the string
     * @param scaleX whether the text should be scaled horizontally
     * @param scaleY whether the text should be scaled vertically
     */
    public void drawString(CharSequence text, float x, float y, boolean scaleX, boolean scaleY) {
        drawString(text, x, y, Float.MAX_VALUE, Float.MAX_VALUE, scaleX, scaleY);
    }

    /**
     * Draws a string
     * @param text the text to draw
     * @param x the X position of the string
     * @param y the Y position of the string
     * @param maxWidth the maximum width of each line. Will wrap to a new line if exceeded. Put 0 for no limit
     * @param maxHeight the maximum height of the text. Will stop rendering if exceeded Put 0 for no limit
     * @param scaleX whether the text should be scaled horizontally
     * @param scaleY whether the text should be scaled vertically
     */
    public void drawString(CharSequence text, float x, float y, float maxWidth, float maxHeight, boolean scaleX, boolean scaleY) {
        fontHeight = getHeight("A", scaleY) - 10;
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        if(maxWidth == 0) maxWidth = Float.MAX_VALUE;
        if(maxHeight == 0) maxHeight = Float.MAX_VALUE;

        Color4 color4 = Renderer.getColor();

        float drawX = 0;
        float drawY = 0;
        float facX = Renderer.getScaleX();
        float facY = Renderer.getScaleY();
        if(!scaleX) facX = 1;
        if(!scaleY) facY = 1;

        texture.bind();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (isColorCode(text, i)) {
                String hex = text.subSequence(i + 2, i + 8).toString();
                float[] color = parseHexColor(hex);
                Color4 c = new Color4(color[0], color[1], color[2], color[3]);
                if(darken){
                    if(darkenLayers < 0){
                        Renderer.color(c.brighter(-darkenLayers));
                    }else {
                        Renderer.color(c.darker(darkenLayers));
                    }
                }else {
                    Renderer.color(c);
                }
                i += 8; // Skip #{RRGGBB}
                continue;
            }

            if (ch == '\n') {
                // Line feed, reset x and y for the next line
                drawY += fontHeight + fontOffset;
                drawX = 0;
                continue;
            }

            if (ch == '\r') {
                // Carriage return, just skip it
                continue;
            }

            Glyph g = glyphs.get(ch);

            // Check if the text exceeds the maximum width
            if (drawX + g.getWidth() > maxWidth) {
                drawX = 0;  // Move to the next line
                drawY += fontHeight + fontOffset;
            }

            // Check if we exceed the max height
            if (drawY + fontHeight > y + maxHeight) {
                // Clipping, stop rendering
                break;
            }

            // Draw the character
            Renderer.drawTexturedRect(drawX, drawY, g.getWidth() * facX, g.getHeight() * facY,
                    g.getX(), g.getY(), g.getX() + g.getWidth(), g.getY() + g.getHeight(), g.getWidth() * facX,
                    g.getHeight() * facY, texture);

            drawX += g.getWidth() * facX;
        }
        Renderer.color(color4);

        texture.unbind();
        GL11.glPopMatrix();
    }

    /**
     * Disposes the font.
     */
    public void dispose() {
        texture.delete();
    }

    private static boolean isColorCode(CharSequence text, int index) {
        if (text.length() >= index + 9 && text.charAt(index) == '#' && text.charAt(index + 1) == '{' && text.charAt(index + 8) == '}') {
            for (int i = 2; i < 8; i++) {
                char c = text.charAt(index + i);
                if (!Character.isDigit(c) && (c < 'A' || c > 'F') && (c < 'a' || c > 'f')) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    private static float[] parseHexColor(String hex) {
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new float[]{r / 255f, g / 255f, b / 255f, 1.0f};
    }

    /**
     * Draws a string in the center of a rectangle
     * @param text the text to draw
     * @param x the x position of the rectangle
     * @param y the y position of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param scaleX whether the text is scaled horizontally
     * @param scaleY whether the text is scaled vertically
     */
    public void drawCenteredString(CharSequence text, float x, float y, float width, float height, boolean scaleX, boolean scaleY) {
        drawString(text, x + width / 2 - (float) getWidth(text, scaleX) / 2, y + height / 2 - (float) getHeight(text, scaleY) / 2, scaleX, scaleY);
    }
}
