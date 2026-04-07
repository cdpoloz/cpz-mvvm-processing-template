package com.cpz.processingtemplate.input.keyboard;

import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.KeyboardEvent;

/**
 * Bridge from Processing keyboard callbacks to {@link KeyboardState} and {@link InputManager}.
 */
public final class ProcessingKeyboardAdapter {

    private final KeyboardState keyboardState;
    private final InputManager inputManager;

    public ProcessingKeyboardAdapter(KeyboardState keyboardState, InputManager inputManager) {
        this.keyboardState = keyboardState;
        this.inputManager = inputManager;
    }

    public void keyPressed(char key, int keyCode) {
        keyboardState.setKeyPressed(keyCode, key);
        dispatch(KeyboardEvent.Type.PRESS, key, keyCode);
    }

    public void keyReleased(char key, int keyCode) {
        keyboardState.setKeyReleased(keyCode, key);
        dispatch(KeyboardEvent.Type.RELEASE, key, keyCode);
    }

    public void keyTyped(char key, int keyCode) {
        dispatch(KeyboardEvent.Type.TYPE, key, keyCode);
    }

    private void dispatch(KeyboardEvent.Type type, char key, int keyCode) {
        inputManager.dispatchKeyboard(new KeyboardEvent(
                type,
                key,
                keyCode,
                keyboardState.isShiftPressed(),
                keyboardState.isCtrlPressed(),
                keyboardState.isAltPressed()
        ));
    }
}
