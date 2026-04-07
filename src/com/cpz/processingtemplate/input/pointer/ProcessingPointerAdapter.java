package com.cpz.processingtemplate.input.pointer;

import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.PointerEvent;
import com.cpz.processingtemplate.input.keyboard.KeyboardState;

/**
 * Bridge from Processing pointer callbacks to {@link PointerState} and {@link InputManager}.
 */
public final class ProcessingPointerAdapter {

    private final PointerState pointerState;
    private final KeyboardState keyboardState;
    private final InputManager inputManager;

    public ProcessingPointerAdapter(PointerState pointerState, KeyboardState keyboardState, InputManager inputManager) {
        this.pointerState = pointerState;
        this.keyboardState = keyboardState;
        this.inputManager = inputManager;
    }

    public void mouseMoved(float x, float y) {
        pointerState.setPosition(x, y);
        dispatch(PointerEvent.Type.MOVE);
    }

    public void mouseDragged(float x, float y) {
        pointerState.setPosition(x, y);
        dispatch(PointerEvent.Type.DRAG);
    }

    public void mousePressed(float x, float y, int button) {
        pointerState.setPosition(x, y);
        pointerState.setPressed(true, button);
        dispatch(PointerEvent.Type.PRESS);
    }

    public void mouseReleased(float x, float y, int button) {
        pointerState.setPosition(x, y);
        pointerState.setPressed(false, button);
        dispatch(PointerEvent.Type.RELEASE);
    }

    public void mouseWheel(float delta) {
        pointerState.setWheel(delta);
        dispatch(PointerEvent.Type.WHEEL);
    }

    private void dispatch(PointerEvent.Type type) {
        inputManager.dispatchPointer(new PointerEvent(
                type,
                pointerState.getX(),
                pointerState.getY(),
                pointerState.isPressed(),
                pointerState.getButton(),
                type == PointerEvent.Type.WHEEL ? pointerState.getWheelDelta() : 0.0f,
                keyboardState.isShiftPressed(),
                keyboardState.isCtrlPressed(),
                keyboardState.isAltPressed()
        ));
    }
}
