package com.dokko.win4jui.api.font;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
@Getter
@Accessors(chain = true)
@Setter
//TODO: Add font instancing from sprite sheet - do in future commit
public class Font4JUI {
    public enum Style {
        REGULAR(Font.PLAIN), BOLD(Font.BOLD), ITALIC(Font.ITALIC), BOLD_ITALIC(Font.BOLD | Font.ITALIC);
        private final int iVal;
        Style(int style) {
            this.iVal = style;
        }

        public int iVal() {
            return iVal;
        }
    }

    private boolean fontInstalled;
    private String fontName;
    private int fontStyle;
    private int fontSize;
    public Font4JUI(String fontName) {
        this(fontName, 18);
    }
    public Font4JUI(String fontName, int size) {
        this(fontName, Style.REGULAR, size);
    }
    public Font4JUI(String fontName, Font4JUI.Style style, int size) {
        this.fontName = fontName;
        this.fontStyle = style.iVal();
        this.fontSize = size;
        this.fontInstalled = checkIsFontAvailable();
        if(!fontInstalled){
            this.fontName = "Serif";
            fontStyle = Style.REGULAR.iVal();
        }
    }

    private boolean checkIsFontAvailable(){
        ArrayList<String> fonts = new ArrayList<>();
        Collections.addAll(fonts, GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        return fonts.contains(fontName);
    }
    public Font getSwingFont() {
        return new Font(fontName, fontStyle, fontSize);
    }
}
