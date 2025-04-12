package com.dokko.neatfx.engine.render.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class FontMeta implements Serializable {
    public static final int META_VERSION = 3;
    int fontVersion;
    String fontName;
    int fontSize, fontStyle;
    Map<Character, Glyph> glyphs;
    int fontHeight;
    int fontOffset;
    boolean antialiased;
    byte[] textureData;
    int textureWidth, textureHeight;
}

