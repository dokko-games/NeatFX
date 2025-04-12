package com.dokko.neatfx.core;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class that keeps track of the currently pressed keys, the just pressed ones and the just released ones.
 * It also contains the position of the mouse and its buttons' states
 */
public class Input {
    @Getter
    private static final Set<Integer> currentPressedKeys = new HashSet<>();
    @Getter
    private static final Set<Integer> justPressedKeys = new HashSet<>();
    @Getter
    private static final Set<Integer> justReleasedKeys = new HashSet<>();
    @Getter @Setter
    private static float mouseX, mouseY;
    @Getter
    private static final Set<Integer> currentPressedButtons = new HashSet<>();
    @Getter
    private static final Set<Integer> justPressedButtons = new HashSet<>();
    @Getter
    private static final Set<Integer> justReleasedButtons = new HashSet<>();

    public static void update() {
        justPressedKeys.clear();
        justReleasedKeys.clear();
        justPressedButtons.clear();
        justReleasedButtons.clear();
    }

    public static boolean isKeyPressed(int key) {
        return currentPressedKeys.contains(key);
    }
    public static boolean wasKeyJustPressed(int key) {
        return justPressedKeys.contains(key);
    }
    public static boolean wasKeyJustReleased(int key) {
        return justReleasedKeys.contains(key);
    }

    public static boolean isButtonPressed(int button) {
        return currentPressedButtons.contains(button);
    }
    public static boolean wasButtonJustPressed(int button) {
        return justPressedButtons.contains(button);
    }
    public static boolean wasButtonJustReleased(int button) {
        return justReleasedButtons.contains(button);
    }
}
