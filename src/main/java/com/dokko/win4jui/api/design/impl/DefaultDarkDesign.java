package com.dokko.win4jui.api.design.impl;

import com.dokko.win4jui.api.design.Decoration;
import com.dokko.win4jui.api.design.Design4JUI;
import com.formdev.flatlaf.FlatDarculaLaf;

public class DefaultDarkDesign extends Design4JUI { //TODO: methods for decorations
    public DefaultDarkDesign() {
        super("Default Dark", true, Decoration.BEVEL, new FlatDarculaLaf());
    }
}
