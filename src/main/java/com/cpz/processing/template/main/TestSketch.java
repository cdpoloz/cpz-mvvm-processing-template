package com.cpz.processing.template.main;

import com.cpz.processing.controls.controls.Control;
import com.cpz.processing.controls.controls.button.Button;
import com.cpz.processing.controls.controls.checkbox.Checkbox;
import com.cpz.processing.controls.controls.dropdown.DropDown;
import com.cpz.processing.controls.controls.label.Label;
import com.cpz.processing.controls.controls.numericfield.NumericField;
import com.cpz.processing.controls.controls.radiogroup.RadioGroup;
import com.cpz.processing.controls.controls.slider.Slider;
import com.cpz.processing.controls.controls.textfield.TextField;
import com.cpz.processing.controls.controls.toggle.Toggle;
import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.PointerEvent;
import com.cpz.processing.controls.core.overlay.OverlayManager;
import com.cpz.processing.controls.core.overlay.tooltip.input.TooltipInputLayer;
import com.cpz.processing.controls.core.overlay.tooltip.util.TooltipOverlayController;
import com.cpz.processing.controls.input.KeyboardState;
import com.cpz.processing.controls.input.ProcessingKeyboardAdapter;
import com.cpz.processing.template.config.TestSketchConfig;
import com.cpz.processing.template.input.MainInputLayer;
import com.cpz.utils.noise.NoiseSource;
import com.cpz.utils.noise.NoiseValue;
import com.cpz.utils.noise.PerlinNoise;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CPZ
 */
public class TestSketch extends PApplet {

    private InputManager inputManager;
    private OverlayManager overlayManager;
    private TooltipOverlayController tooltips;
    private ProcessingKeyboardAdapter processingKeyboardAdapter;
    private Map<String, Control> controls;
    private Map<String, Button> buttons;
    private Map<String, Checkbox> checkboxes;
    private Map<String, DropDown> dropdowns;
    private Map<String, Label> labels;
    private Map<String, NumericField> numericfields;
    private Map<String, RadioGroup> radiogroups;
    private Map<String, Slider> sliders;
    private Map<String, TextField> textfields;
    private Map<String, Toggle> toggles;

    private NoiseValue noise;
    private List<Float> lstF;
    private float da, r, rMax, dr;
    private List<PVector> lstP;

    public void settings() {
        TestSketchConfig.settings(this);
    }

    public void setup() {
        TestSketchConfig.initialSetup(this);
        // input manager
        inputManager = new InputManager();
        overlayManager = new OverlayManager();
        // controles
        controls = TestSketchConfig.setupControls(this, overlayManager, inputManager);
        buttons = TestSketchConfig.filterButtons(controls);
        checkboxes = TestSketchConfig.filterCheckboxes(controls);
        dropdowns = TestSketchConfig.filterDropdowns(controls);
        labels = TestSketchConfig.filterLabels(controls);
        numericfields = TestSketchConfig.filterNumericfields(controls);
        radiogroups = TestSketchConfig.filterRadiogroups(controls);
        sliders = TestSketchConfig.filterSliders(controls);
        textfields = TestSketchConfig.filterTextfields(controls);
        toggles = TestSketchConfig.filterToggles(controls);
        // tooltips
        tooltips = TestSketchConfig.setupTooltips(this, overlayManager, controls);
        // main input layer
        MainInputLayer mainInputLayer = new MainInputLayer(0);
        buttons.values().forEach(btn -> mainInputLayer.addPointerTarget(btn::handlePointerEvent));
        checkboxes.values().forEach(chk -> mainInputLayer.addPointerTarget(chk::handlePointerEvent));
        dropdowns.values().forEach(dd -> mainInputLayer.addPointerTarget(dd::handlePointerEvent));
        numericfields.values().forEach(nf -> mainInputLayer.addPointerTarget(nf::handlePointerEvent));
        numericfields.values().forEach(nf -> mainInputLayer.addKeyboardTarget(nf::handleKeyboardEvent));
        radiogroups.values().forEach(rg -> mainInputLayer.addPointerTarget(rg::handlePointerEvent));
        radiogroups.values().forEach(rg -> mainInputLayer.addKeyboardTarget(rg::handleKeyboardEvent));
        sliders.values().forEach(sld -> mainInputLayer.addPointerTarget(sld::handlePointerEvent));
        textfields.values().forEach(tf -> mainInputLayer.addPointerTarget(tf::handlePointerEvent));
        textfields.values().forEach(tf -> mainInputLayer.addKeyboardTarget(tf::handleKeyboardEvent));
        toggles.values().forEach(tgl -> mainInputLayer.addPointerTarget(tgl::handlePointerEvent));
        inputManager.registerLayer(mainInputLayer);
        inputManager.registerLayer(new TooltipInputLayer(1000, tooltips));
        KeyboardState keyboardState = new KeyboardState();
        processingKeyboardAdapter = new ProcessingKeyboardAdapter(keyboardState, inputManager);
        // test
        NoiseSource perlin = new PerlinNoise(1234L);
        noise = new NoiseValue(perlin, random(1000), 0.001f);
        da = 1;
        rMax = 200;
        dr = 1;
        lstF = new ArrayList<>();
        int n = (int) (360 / da);
        for (int i = 0; i < n; i++) {
            noise.update();
            lstF.add(noise.get());
        }
        System.out.println("lstF.size() = " + lstF.size());
        lstP = new ArrayList<>();
    }

    public void draw() {
        background(32);

        noise.update();
        lstF.remove(0);
        //lstF.removeFirst();
        lstF.add(noise.get());
        r += dr;
        if (r > rMax) r = 0;
        lstP.clear();
        for (int i = 0; i < lstF.size(); i++) {
            float a = radians(i * da);
            float mag = r * lstF.get(i);
            PVector p = new PVector(mag * cos(a) + width * 0.5f, mag * sin(a) + height * 0.5f);
            lstP.add(p);
        }
        stroke(255);
        for (int i = 1; i < lstP.size(); i++) {
            PVector prev = lstP.get(i - 1);
            PVector p = lstP.get(i);
            line(prev.x, prev.y, p.x, p.y);
        }
        PVector p1 = lstP.get(0);
        PVector p2 = lstP.get(lstP.size() - 1);
        line(p1.x, p1.y, p2.x, p2.y);

        /*
        drawCustomTooltipArea();
        controls.values().forEach(Control::draw);
        overlayManager.getActiveOverlays().forEach(entry -> entry.getRender().run());
        */
    }

    private void drawCustomTooltipArea() {
        pushStyle();
        rectMode(CENTER);
        stroke(86, 142, 203);
        strokeWeight(2.0f);
        fill(42, 54, 66);
        float x = 850;
        float y = 400;
        float w = 200;
        float h = 100;
        rect(x, y, w, h, 8.0f);
        popStyle();
    }

    // <editor-fold defaultstate="collapsed" desc="*** control events ***">
    public void btnClicked(String code) {
        if (code == null) return;
        labels.get("lblTemplate").setText("btnClicked: " + code);
    }

    public void chkClicked(String code, boolean status) {
        if (code == null) return;
        labels.get("lblTemplate").setText("chkClicked: " + code + ".state = " + (status ? "1" : "0"));
    }

    public void ddChanged(DropDown dd) {
        if (dd == null) return;
        labels.get("lblTemplate").setText("ddChanged: " + dd.getCode() + ".value = " + dd.getSelectedItem());
    }

    public void nfChanged(NumericField nf) {
        if (nf == null) return;
        labels.get("lblTemplate").setText("nfChanged: " + nf.getCode() + ".value = " + nf.getValue());
    }

    public void rgClicked(RadioGroup rg) {
        if (rg == null) return;
        String s = "rgClicked: " + rg.getCode() + ".selection = ";
        s += rg.getSelectedOption().isEmpty() ? rg.getSelectedIndex() : rg.getSelectedOption();
        labels.get("lblTemplate").setText(s);
    }

    public void sldChanged(String code, BigDecimal value) {
        if (code == null || value == null) return;
        labels.get("lblTemplate").setText("sldChanged: " + code + ".value = " + value);
    }

    public void tfChanged(String code, String text) {
        if (code == null) return;
        labels.get("lblTemplate").setText("tfChanged: " + code + " = " + text);
    }

    public void tglClicked(String code, int status) {
        if (code == null) return;
        labels.get("lblTemplate").setText("tglClicked: " + code + ".state = " + status);
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

    public void mouseWheel(MouseEvent event) {
        if (event == null) return;
        inputManager.dispatchPointer(new PointerEvent(PointerEvent.Type.WHEEL, (float) mouseX, (float) mouseY, mouseButton, (float) event.getCount(), event.isShiftDown(), event.isControlDown()));
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** keyboard events ***">
    public void keyPressed() {
        if (key == ESC) key = 0;
        processingKeyboardAdapter.keyPressed(key, keyCode);
    }

    public void keyReleased() {
        processingKeyboardAdapter.keyReleased(key, keyCode);
    }

    public void keyTyped() {
        processingKeyboardAdapter.keyTyped(key, keyCode);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** exit ***">
    public void exit() {
        if (dropdowns != null) dropdowns.values().forEach(DropDown::dispose);
        if (tooltips != null) tooltips.dispose();
        if (overlayManager != null) overlayManager.clearAll();
        super.exit();
    }
    // </editor-fold>
}
