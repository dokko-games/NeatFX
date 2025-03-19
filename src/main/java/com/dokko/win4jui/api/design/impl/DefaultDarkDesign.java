package com.dokko.win4jui.api.design.impl;

import com.dokko.win4jui.api.design.Design4JUI;
import com.formdev.flatlaf.FlatDarculaLaf;

public class DefaultDarkDesign extends Design4JUI {
    public DefaultDarkDesign() {
        super("Default Dark", true, true, new FlatDarculaLaf());
    }
}
