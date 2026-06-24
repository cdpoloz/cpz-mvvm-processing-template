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
import com.cpz.processing.controls.input.KeyboardState;
import com.cpz.processing.controls.input.ProcessingKeyboardAdapter;
import com.cpz.processing.template.config.TemplateSketchConfig;
import com.cpz.processing.template.input.MainInputLayer;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author CPZ
 */
public class TemplateSketch extends PApplet {

    private InputManager inputManager;
    private OverlayManager overlayManager;
    private ProcessingKeyboardAdapter processingKeyboardAdapter;
    private Map<String, Control> controls;
    private Map<String, Button> botones;
    private Map<String, Checkbox> checkboxes;
    private Map<String, DropDown> dropdowns;
    private Map<String, Label> labels;
    private Map<String, NumericField> numericfields;
    private Map<String, RadioGroup> radiogroups;
    private Map<String, Slider> sliders;
    private Map<String, TextField> textfields;
    private Map<String, Toggle> toggles;


    public void settings() {
        TemplateSketchConfig.settings(this);
    }

    public void setup() {
        TemplateSketchConfig.initialSetup(this);
        // input manager
        inputManager = new InputManager();
        overlayManager = new OverlayManager();
        // controles
        controls = TemplateSketchConfig.setupControls(this, overlayManager, inputManager);
        botones = TemplateSketchConfig.filterButtons(controls);
        checkboxes = TemplateSketchConfig.filterCheckboxes(controls);
        dropdowns = TemplateSketchConfig.filterDropdowns(controls);
        labels = TemplateSketchConfig.filterLabels(controls);
        numericfields = TemplateSketchConfig.filterNumericfields(controls);
        radiogroups = TemplateSketchConfig.filterRadiogroups(controls);
        sliders = TemplateSketchConfig.filterSliders(controls);
        textfields = TemplateSketchConfig.filterTextfields(controls);
        toggles = TemplateSketchConfig.filterToggles(controls);
        // main input layer
        MainInputLayer mainInputLayer = new MainInputLayer(0);
        botones.values().forEach(btn -> mainInputLayer.addPointerTarget(btn::handlePointerEvent));
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
        KeyboardState keyboardState = new KeyboardState();
        processingKeyboardAdapter = new ProcessingKeyboardAdapter(keyboardState, inputManager);
    }

    public void draw() {
        background(32);
        controls.values().forEach(Control::draw);
        /*
         ***** the final object to be drawn is the OverlayManager so the
         ***** DropdDown objects could be seen on top of everything else
         */
        overlayManager.getActiveOverlays().forEach(entry -> entry.getRender().run());
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
}
