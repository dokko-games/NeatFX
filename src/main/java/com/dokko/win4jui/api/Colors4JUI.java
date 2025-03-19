package com.dokko.win4jui.api;

import java.awt.*;

public class Colors4JUI {
    public static Color brighten(Color c, float factor){
        factor *= 5f;
        if(c.getRed() > 0 && c.getGreen() > 0 && c.getBlue() > 0){
            return c.brighter();
        }
        Color cd = c;
        if(cd.getRed() == 0)cd = new Color(10, cd.getGreen(), cd.getBlue());
        if(cd.getGreen() == 0)cd = new Color(cd.getRed(), 10, cd.getBlue());
        if(cd.getBlue() == 0)cd = new Color(cd.getRed(), cd.getGreen(), 10);
        int r = (int)(cd.getRed()*factor);
        int g = (int)(cd.getGreen()*factor);
        int b = (int)(cd.getBlue()*factor);
        r = Math.min(255, r);
        g = Math.min(255, g);
        b = Math.min(255, b);
        return new Color(r, g, b);
    }
    public static Color darken(Color c, float factor){
        int r = (int)(c.getRed()/factor);
        int g = (int)(c.getGreen()/factor);
        int b = (int)(c.getBlue()/factor);
        r = Math.min(255, r);
        g = Math.min(255, g);
        b = Math.min(255, b);
        return new Color(r, g, b);
    }
}
