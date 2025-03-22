package com.dokko.win4jui.api.ui.design.impl;

import com.dokko.win4jui.api.ui.design.Decoration;
import com.dokko.win4jui.api.ui.design.Design4JUI;
import com.formdev.flatlaf.FlatLightLaf;

public class DefaultBrightDesign extends Design4JUI {
    public DefaultBrightDesign() {
        super("Default Bright", false, Decoration.BEVEL, new FlatLightLaf());
    }
}
