package com.dokko.win4jui.api.ui;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Input4JUI {
    public static final int KEY_SPACE = KeyEvent.VK_SPACE;
    public static final int MOUSE_BUTTON_LEFT = MouseEvent.BUTTON1;
    public static final int MOUSE_BUTTON_MIDDLE = MouseEvent.BUTTON2;
    public static final int MOUSE_BUTTON_RIGHT = MouseEvent.BUTTON3;

    private static final HashMap<Integer, Boolean> mouseButtons = new HashMap<>();
    private static final HashMap<Integer, Boolean> keys = new HashMap<>();
    private static final HashMap<Integer, Boolean> prevMouseButtons = new HashMap<>();
    private static final HashMap<Integer, Boolean> prevKeys = new HashMap<>();

    @Getter
    @Setter
    private static int mouseX, mouseY;

    public static void update() {
        prevMouseButtons.clear();
        prevMouseButtons.putAll(mouseButtons);
        prevKeys.clear();
        prevKeys.putAll(keys);
    }

    public static void setButtonDown(int button, boolean state) {
        mouseButtons.put(button, state);
    }

    public static boolean isButtonDown(int button) {
        return mouseButtons.getOrDefault(button, false);
    }

    public static boolean isButtonJustPressed(int button) {
        return isButtonDown(button) && !prevMouseButtons.getOrDefault(button, false);
    }

    public static boolean isButtonJustReleased(int button) {
        return !isButtonDown(button) && prevMouseButtons.getOrDefault(button, false);
    }

    public static void setPosition(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public static void setKeyDown(int keyCode, boolean state) {
        keys.put(keyCode, state);
    }

    public static boolean isKeyDown(int keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    public static boolean isKeyJustPressed(int keyCode) {
        return isKeyDown(keyCode) && !prevKeys.getOrDefault(keyCode, false);
    }

    public static boolean isKeyJustReleased(int keyCode) {
        return !isKeyDown(keyCode) && prevKeys.getOrDefault(keyCode, false);
    }
}
