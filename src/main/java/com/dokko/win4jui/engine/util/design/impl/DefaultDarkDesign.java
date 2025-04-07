package com.dokko.win4jui.engine.util.design.impl;

import com.dokko.win4jui.core.window.element.impl.basic.Button;
import com.dokko.win4jui.core.window.element.impl.basic.Panel;
import com.dokko.win4jui.engine.render.Renderer;
import com.dokko.win4jui.engine.util.design.Design4JUI;
import com.dokko.win4jui.engine.render.RenderOverride;

public class DefaultDarkDesign extends Design4JUI {
    public DefaultDarkDesign() {
        super("Default Dark", true);
    }

    @Override
    public void processOverrides(RenderOverride renderOverride) {
        renderOverride.setBackground(renderOverride.getBackground().invert());
        if(!renderOverride.isPost()){
            return;
        }
        if(renderOverride.is(Panel.class)){
            overridePanel(renderOverride);
        }else if(renderOverride.is(Button.class)){
            overrideButton(renderOverride);
        }
    }

    private void overridePanel(RenderOverride renderOverride) {
        int iw = (int) renderOverride.getElement().getAnchoredWidth();
        int ih = (int) renderOverride.getElement().getAnchoredHeight();

        int borderSize = 6;

        float factor = (float) iw / (4 * borderSize);
        if(iw > 4 * borderSize) factor = 1;
        float factor2 = (float) ih / (4 * borderSize);
        if(ih > 4 * borderSize) factor2 = 1;
        Renderer.color(renderOverride.getElement().getBackground().darker(1));
        Renderer.rect(0, 0, borderSize * factor, ih);
        Renderer.rect(0, ih - borderSize * factor2, iw, borderSize * factor2);

        Renderer.color(renderOverride.getElement().getBackground().brighter(1));
        Renderer.rect(iw - borderSize * factor, 0, borderSize * factor, ih - borderSize * factor2);
        Renderer.rect(borderSize * factor, 0, iw - borderSize*factor, borderSize*factor2);
    }
    private void overrideButton(RenderOverride renderOverride){
        int iw = (int) renderOverride.getElement().getAnchoredWidth();
        int ih = (int) renderOverride.getElement().getAnchoredHeight();
        int borderSize = 3;
        float factor = (float) iw / (4 * borderSize);
        if(iw > 4 * borderSize) factor = 1;
        float factor2 = (float) ih / (4 * borderSize);
        if(ih > 4 * borderSize) factor2 = 1;
        Renderer.color(renderOverride.getElement().getBackground().darker(1));
        Renderer.rect(0, 0, borderSize * factor, ih);
        Renderer.rect(0, ih - borderSize * factor2, iw, borderSize * factor2);
        Renderer.rect(iw - borderSize * factor, 0, borderSize * factor, ih - borderSize * factor2);
        Renderer.rect(borderSize * factor, 0, iw - borderSize * factor, borderSize * factor2);
    }

}
