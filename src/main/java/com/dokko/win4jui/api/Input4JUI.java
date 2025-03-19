package com.dokko.win4jui.api;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Input4JUI {
    public static final int KEY_SPACE = KeyEvent.VK_SPACE;
    private static final HashMap<Integer, Boolean> mouseButtons = new HashMap<>();
    private static final HashMap<Integer, Boolean> keys = new HashMap<>();
    @Getter
    @Setter
    private static int mouseX, mouseY;
    public static void setButtonDown(int button, boolean state) {
        mouseButtons.put(button, state);
    }
    public static boolean isButtonDown(int button){
        return mouseButtons.getOrDefault(button, false);
    }

    public static void setPosition(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public static void setKeyDown(int keyCode, boolean state) {
        keys.put(keyCode, state);
    }
    public static boolean isKeyDown(int keyCode){
        return keys.getOrDefault(keyCode, false);
    }
}
