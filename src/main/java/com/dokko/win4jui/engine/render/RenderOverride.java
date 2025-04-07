package com.dokko.win4jui.engine.render;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.core.error.Error;
import com.dokko.win4jui.core.window.element.Element;
import com.dokko.win4jui.engine.Color4;
import lombok.Getter;
import lombok.Setter;

/**
 * Made for designs. Allows for overriding element's default rendering system
 */
@Getter
@Setter
public class RenderOverride {
    /**
     * The element to modify
     */
    private Element element;
    /**
     * If true it will cancel the element's default rendering system
     */
    private boolean cancelled;
    /**
     * If true then it was called after the element's default rendering system, so you cannot cancel it. Useful for adding borders
     */
    private boolean post;
    /**
     * The colors of the element. Used to modify colors on dark themes
     */
    private Color4 background, foreground;
    /**
     * The default colors of the element. <b>Do not modify</b>
     */
    private final Color4 preBackground, preForeground;

    public RenderOverride(Element e){
        setElement(e);
        setCancelled(false);
        setPost(false);
        setBackground(e.getBackground());
        setForeground(e.getForeground());
        preBackground = e.getBackground();
        preForeground = e.getForeground();
    }

    /**
     * Checks if this element is an instance of the class <b>cls</b>
     * @param cls the class to override
     * @return true if it is an instance of the class
     */
    public boolean is(Class<? extends Element> cls){
        return cls.isAssignableFrom(element.getClass());
    }

    /**
     * Converts this element to an object of of type T
     * @param cls the class of the type T. Must extend Element
     * @return the converted element
     */
    @SuppressWarnings("all")
    public <T> T as(Class<T> cls) {
        if(!Element.class.isAssignableFrom(cls)) throw new Error("RenderOverride Error", "render override <as> does not extend Element", "").getException();
        if(!is((Class<? extends Element>) cls)) throw new Error("RenderOverride Error", "render override is not an instance of "+cls.getSimpleName()).getException();
        return (T) element;
    }

    /**
     * Calls the override
     */
    public void call() {
        Win4JUI.getDesign().processOverrides(this);
    }
}
