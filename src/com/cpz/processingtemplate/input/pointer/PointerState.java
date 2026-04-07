package com.cpz.processingtemplate.input.pointer;

/**
 * Pure Java pointer state holder used to track position, button, press state, and wheel delta.
 */
public final class PointerState {

    private float x;
    private float y;
    private boolean pressed;
    private int button;
    private float wheelDelta;

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPressed(boolean pressed, int button) {
        this.pressed = pressed;
        this.button = button;
    }

    public void setWheel(float delta) {
        this.wheelDelta = delta;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isPressed() {
        return pressed;
    }

    public int getButton() {
        return button;
    }

    public float getWheelDelta() {
        return wheelDelta;
    }
}
