package com.cpz.processing.template.main;

import com.cpz.processing.controls.controls.button.Button;
import com.cpz.processing.controls.controls.label.Label;
import com.cpz.processing.controls.controls.label.config.LabelStyleConfig;
import com.cpz.processing.controls.controls.label.style.DefaultLabelStyle;
import com.cpz.processing.controls.controls.label.style.HorizontalAlign;
import com.cpz.processing.controls.controls.label.style.VerticalAlign;
import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.PointerEvent;
import com.cpz.processing.controls.core.util.Colors;
import com.cpz.processing.template.config.Config;
import com.cpz.processing.template.input.MainInputLayer;
import processing.core.PApplet;

import java.io.File;

import static com.cpz.processing.template.main.Launcher.LOG;

/**
 * @author CPZ
 */
public class Sketch extends PApplet {

    // controls
    private InputManager inputManager;
    private Button button;
    private Label label;
    // sketch variables
    private int clickCount;

    public void settings() {
        Config.settings(this);
    }

    public void setup() {
        Config.setup(this);
        LOG.info("Starting final setup");
        // button
        float x = 300f;
        float y = 125f;
        float w = 200f;
        float h = 60f;
        button = new Button(this, "btnTest", "Simple Button", x, y, w, h);
        button.setClickListener(this::btnTestClicked);
        // label
        x = 120f;
        y = 150f;
        w = 360f;
        h = 100f;
        label = new Label(this, "lblTest", "Click count = 0", x, y, w, h);
        // label style
        LabelStyleConfig lsc = new LabelStyleConfig();
        lsc.textSize = 24.0f;
        lsc.font = createFont("data" + File.separator + "font" + File.separator + "abel-regular.ttf", 24);
        lsc.textColor = Colors.rgb(210, 228, 255);
        lsc.lineSpacingMultiplier = 1.2f;
        lsc.alignX = HorizontalAlign.CENTER;
        lsc.alignY = VerticalAlign.CENTER;
        lsc.disabledAlpha = 80;
        label.setStyle(new DefaultLabelStyle(lsc));
        // inputManager
        inputManager = new InputManager();
        MainInputLayer mainInputLayer = new MainInputLayer(0).addPointerTarget(button::handlePointerEvent);
        inputManager.registerLayer(mainInputLayer);

        LOG.info("Finished final setup");
    }

    public void draw() {
        background(28);
        button.draw();
        label.draw();
    }

    // <editor-fold defaultstate="collapsed" desc="*** binding ***">
    private void btnTestClicked() {
        clickCount++;
        label.setText("Click count = " + clickCount);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** mouse events ***">
    public void mouseMoved() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.MOVE, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mouseDragged() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.DRAG, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mousePressed() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.PRESS, (float) mouseX, (float) mouseY, mouseButton));
    }

    public void mouseReleased() {
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.RELEASE, (float) mouseX, (float) mouseY, mouseButton));
    }
    // </editor-fold>
}
