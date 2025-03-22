package com.dokko.win4jui.api.ui.design.impl;

import com.dokko.win4jui.api.ui.design.Decoration;
import com.dokko.win4jui.api.ui.design.Design4JUI;
import com.formdev.flatlaf.FlatDarculaLaf;

public class DefaultDarkDesign extends Design4JUI { //TODO: methods for decorations
    public DefaultDarkDesign() {
        super("Default Dark", true, Decoration.BEVEL, new FlatDarculaLaf());
    }
}
