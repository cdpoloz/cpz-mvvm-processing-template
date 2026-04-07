package com.cpz.processingtemplate.input.keyboard;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Pure Java keyboard state holder used to track modifiers and pressed keys.
 */
public final class KeyboardState {

    private final Set<Integer> pressedKeys = new HashSet<>();
    private boolean shiftPressed;
    private boolean ctrlPressed;
    private boolean altPressed;

    public void setKeyPressed(int keyCode, char key) {
        pressedKeys.add(keyCode);
        updateModifiers(keyCode, true);
    }

    public void setKeyReleased(int keyCode, char key) {
        pressedKeys.remove(keyCode);
        updateModifiers(keyCode, false);
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }

    public boolean isCtrlPressed() {
        return ctrlPressed;
    }

    public boolean isAltPressed() {
        return altPressed;
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    private void updateModifiers(int keyCode, boolean pressed) {
        if (keyCode == KeyEvent.VK_SHIFT) {
            shiftPressed = pressed;
            return;
        }
        if (keyCode == KeyEvent.VK_CONTROL) {
            ctrlPressed = pressed;
            return;
        }
        if (keyCode == KeyEvent.VK_ALT) {
            altPressed = pressed;
        }
    }
}
