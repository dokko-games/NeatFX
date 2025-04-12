package com.dokko.neatfx.engine.render.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Glyph implements Serializable {
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final float advance;

}
