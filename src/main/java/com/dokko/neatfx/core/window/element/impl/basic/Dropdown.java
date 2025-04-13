package com.dokko.neatfx.core.window.element.impl.basic;

import com.dokko.neatfx.core.Input;
import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.element.Element;
import com.dokko.neatfx.engine.Color4;
import com.dokko.neatfx.engine.render.Renderer;
import com.dokko.neatfx.engine.render.text.BFont;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Dropdown extends Element {
    private List<String> items;
    private int selectedIndex = 0;
    private boolean expanded = false;
    @Getter
    @Setter
    private Consumer<String> onSelect;
    @Getter
    @Setter
    private BFont font;
    public Dropdown(float x, float y, float width, float height, Anchors anchors, List<String> items, Consumer<String> onSelect) {
        super(x, y, width, height, anchors);
        this.items = items;
        this.onSelect = onSelect;
        font = new BFont( 12, Font.BOLD);
        setForeground(Color4.WHITE);
    }

    public static Dropdown from(float x, float y, float width, float height, Anchors anchors, String current, String... values) {
        List<String> vals = new ArrayList<>(Arrays.asList(values));
        if(!vals.contains(current))vals.addFirst(current);
        int index = vals.indexOf(current);
        Dropdown d = new Dropdown(x, y, width, height, anchors, vals, null);
        d.selectedIndex = index;
        return d;
    }

    @Override
    protected void doRender(float scaleW, float scaleH) {
        if (getDefaultWidth() == 0) {
            float maxWidth = 0;
            for (String item : items) {
                float itemWidth = font.getWidth(item, getAnchors().isWidthScaled());
                if (itemWidth > maxWidth) {
                    maxWidth = itemWidth;
                }
            }
            setWidth(maxWidth + 50); // Add some padding for the arrow and spacing
        }

        if (getDefaultHeight() == 0) {
            float maxHeight = 0;
            for (String item : items) {
                float itemHeight = font.getHeight(item, getAnchors().isHeightScaled());
                if (itemHeight > maxHeight) {
                    maxHeight = itemHeight;
                }
            }
            setHeight(maxHeight + 30); // Add some vertical padding
        }


        // Background of dropdown
        Color4 bg = isHovered() ? Color4.LIGHT_GRAY : getBackground();
        bg.bind();
        Renderer.rect(0, 0, getAnchoredWidth(), getAnchoredHeight());

        // Render selected item text
        // Ideally replace with proper text rendering system
        Renderer.color(getForeground());
        font.drawCenteredString(items.get(selectedIndex), 0, 0, getAnchoredWidth(), getAnchoredHeight(),
                getAnchors().isWidthScaled(), getAnchors().isHeightScaled());
        // Here we just simulate: drawText(items.get(selectedIndex), ...)

        // Render dropdown arrow icon
        // (again, just conceptually—ideally this would be an image or a triangle)

        if (expanded) {
            for (int i = 0; i < items.size(); i++) {
                float itemHeight = font.getHeight(items.get(i), getAnchors().isHeightScaled()) + 20;
                float yOffset = getAnchoredHeight() + i * itemHeight;
                Color4 itemBg = isItemHovered(i) ? Color4.DARK_GRAY : Color4.GRAY;
                float w = Math.max(font.getWidth(items.get(i), getAnchors().isWidthScaled()) + 15, getAnchoredWidth());
                itemBg.bind();
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2f(0, yOffset);
                GL11.glVertex2f(w, yOffset);
                GL11.glVertex2f(w, yOffset + itemHeight);
                GL11.glVertex2f(0, yOffset + itemHeight);
                GL11.glEnd();
                Renderer.color(Renderer.getColor().darker(3));
                Renderer.rect(0, yOffset, 2, itemHeight);
                Renderer.rect(w-2, yOffset, 2, itemHeight);
                Renderer.rect(0, yOffset, w, 2);
                Renderer.rect(0, yOffset+itemHeight-2, w, 2);

                // drawText(items.get(i), 5, yOffset + 5)
                Renderer.color(getForeground());
                font.drawCenteredString(items.get(i), 0, yOffset, w, itemHeight, getAnchors().isWidthScaled(), getAnchors().isHeightScaled());
            }
        }
    }
    @Override
    public void processInput(float x, float y, float w, float h, float sw, float sh) {
        float mouseX = Input.getMouseX();
        float mouseY = Input.getMouseY();

        if (isHovered()) {
            if (Input.wasButtonJustPressed(0)) {
                expanded = !expanded;
                markInputAsProcessed();
                return;
            }
        }

        if (expanded) {
            for (int i = 0; i < items.size(); i++) {
                float itemHeight = font.getHeight(items.get(i), getAnchors().isHeightScaled()) + 20;
                float itemY = getAnchoredY() + getAnchoredHeight() + i * itemHeight;
                if (isBounded(mouseX, mouseY, 0, getAnchoredHeight() + i * itemHeight, getAnchoredWidth(), itemHeight)) {
                    if (Input.wasButtonJustPressed(0)) {
                        selectedIndex = i;
                        expanded = false;
                        if (onSelect != null) {
                            onSelect.accept(items.get(i));
                        }
                        markInputAsProcessed();
                        return;
                    }
                }
            }
        }
        markInputAsProcessed(); // Always call this to avoid error unless logic branches above catch everything
    }

    private boolean isItemHovered(int index) {
        float itemHeight = font.getHeight(items.get(index), getAnchors().isHeightScaled()) + 20;
        float mouseX = Input.getMouseX();
        float mouseY = Input.getMouseY();
        return isBounded(mouseX, mouseY, 0, getAnchoredHeight() + index * itemHeight, getAnchoredWidth(), itemHeight);
    }

    public String getSelectedItem() {
        return items.get(selectedIndex);
    }

    public void setItems(List<String> items) {
        this.items = items;
        if (selectedIndex >= items.size()) {
            selectedIndex = 0;
        }
    }
}
