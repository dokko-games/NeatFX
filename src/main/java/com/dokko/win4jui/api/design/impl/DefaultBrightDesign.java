package com.dokko.win4jui.api.design.impl;

import com.dokko.win4jui.api.design.Design4JUI;
import com.formdev.flatlaf.FlatLightLaf;

public class DefaultBrightDesign extends Design4JUI {
    public DefaultBrightDesign() {
        super("Default Bright", false, true, new FlatLightLaf());
    }
}
