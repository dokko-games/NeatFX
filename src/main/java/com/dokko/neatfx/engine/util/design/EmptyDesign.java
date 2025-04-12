package com.dokko.neatfx.engine.util.design;

import com.dokko.neatfx.engine.render.RenderOverride;

public class EmptyDesign extends NeatDesign {

    public EmptyDesign() {
        super("Empty", false);
    }

    @Override
    public void processOverrides(RenderOverride renderOverride) {

    }
}
