package com.dokko.win4jui.engine.util.design;

import com.dokko.win4jui.engine.render.RenderOverride;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A design class that modifies how elements render
 */
@Getter
@Accessors(chain = true)
@Setter
@AllArgsConstructor
public abstract class Design4JUI {
    /**
     * The name of the design
     */
    private String name;
    /**
     * If true then the design is dark-theme-based
     */
    private boolean dark;

    /**
     * Processes a render override to modify an element's rendering
     * @param renderOverride the override to process
     */
    public abstract void processOverrides(RenderOverride renderOverride);
}
