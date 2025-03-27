package com.dokko.win4jui.api.ui.design;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.swing.*;
import java.awt.*;

@Getter
@Accessors(chain = true)
@Setter
@AllArgsConstructor
public abstract class Design4JUI {
    private String name;
    private LookAndFeel lookAndFeel;

    public abstract void decoratePanel(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY, Color color);
    public abstract void decorateButton(Graphics2D graphics, float x, float y, float width, float height, float scalingX, float scalingY, Color color);
    public abstract void drawTextShadow(Graphics2D graphics, String text, float x, float y, Color color);
}
