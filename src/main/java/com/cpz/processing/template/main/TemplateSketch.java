package com.cpz.processing.template.main;

import com.cpz.processing.controls.controls.Control;
import com.cpz.processing.controls.controls.button.Button;
import com.cpz.processing.controls.controls.checkbox.Checkbox;
import com.cpz.processing.controls.controls.label.Label;
import com.cpz.processing.controls.controls.radiogroup.RadioGroup;
import com.cpz.processing.controls.controls.slider.Slider;
import com.cpz.processing.controls.controls.textfield.TextField;
import com.cpz.processing.controls.controls.textfield.input.TextFieldInputLayer;
import com.cpz.processing.controls.controls.toggle.Toggle;
import com.cpz.processing.controls.core.input.InputManager;
import com.cpz.processing.controls.core.input.PointerEvent;
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

    private Map<String, Control> controles;
    private Map<String, Button> botones;
    private Map<String, Checkbox> checkboxes;
    private Map<String, Label> labels;
    private Map<String, RadioGroup> radiogroups;
    private Map<String, Slider> sliders;
    private Map<String, TextField> textfields;
    private Map<String, Toggle> toggles;
    private InputManager inputManager;
    private KeyboardState keyboardState;
    private ProcessingKeyboardAdapter processingKeyboardAdapter;

    public void settings() {
        TemplateSketchConfig.settings(this);
    }

    public void setup() {
        TemplateSketchConfig.initialSetup(this);
        // controles
        controles = TemplateSketchConfig.configurarControles(this);
        botones = TemplateSketchConfig.filtrarBotones(controles);
        labels = TemplateSketchConfig.filtrarLabels(controles);
        radiogroups = TemplateSketchConfig.filtrarRadiogroups(controles);
        sliders = TemplateSketchConfig.filtrarSliders(controles);
        textfields = TemplateSketchConfig.filtrarTextfields(controles);
        toggles = TemplateSketchConfig.filtrarToggles(controles);
        checkboxes = TemplateSketchConfig.filtrarCheckboxes(controles);
        // input manager
        inputManager = new InputManager();
        MainInputLayer mainInputLayer = new MainInputLayer(0);
        botones.values().forEach(btn -> mainInputLayer.addPointerTarget(btn::handlePointerEvent));
        checkboxes.values().forEach(chk -> mainInputLayer.addPointerTarget(chk::handlePointerEvent));
        radiogroups.values().forEach(rg -> mainInputLayer.addPointerTarget(rg::handlePointerEvent));
        sliders.values().forEach(sld -> mainInputLayer.addPointerTarget(sld::handlePointerEvent));
        textfields.values().forEach(tf -> mainInputLayer.addPointerTarget(tf::handlePointerEvent));
        textfields.values().forEach(tf -> mainInputLayer.addKeyboardTarget(tf::handleKeyboardEvent));
        toggles.values().forEach(tgl -> mainInputLayer.addPointerTarget(tgl::handlePointerEvent));
        inputManager.registerLayer(mainInputLayer);
        keyboardState = new KeyboardState();
        processingKeyboardAdapter = new ProcessingKeyboardAdapter(keyboardState, inputManager);
    }

    // CONTINUAR AQUÍ **************************************************************************************************
    // AGREGAR CONTROLES DE EJEMPLO FALTANTES:
    // - TextField
    // - NumericField
    // - DropDown
    // *****************************************************************************************************************

    public void draw() {
        background(32);
        controles.values().forEach(Control::draw);
    }

    // <editor-fold defaultstate="collapsed" desc="*** control events ***">
    public void btnClicked(String codigo) {
        if (codigo == null) return;
        labels.get("lblTemplate").setText("btnClicked: " + codigo);
    }

    public void sldChanged(String codigo, BigDecimal valor) {
        if (codigo == null || valor == null) return;
        labels.get("lblTemplate").setText("sldChanged: " + codigo + " = " + valor);
    }

    public void tglClicked(String codigo, int estado) {
        if (codigo == null) return;
        labels.get("lblTemplate").setText("tglClicked: " + codigo + ".state = " + estado);
    }

    public void chkClicked(String codigo, boolean estado) {
        if (codigo == null) return;
        labels.get("lblTemplate").setText("chkClicked: " + codigo + ".state = " + (estado ? "1" : "0"));
    }

    public void rgClicked(RadioGroup rg) {
        if (rg == null) return;
        labels.get("lblTemplate").setText("rgClicked: " + rg.getCode() + ".selection = " + rg.getSelectedOption());
    }

    public void tfChanged(String code, String text) {
        if (code == null) return;
        //labels.get("lblTemplate").setText("rgClicked: " + rg.getCode() + ".selection = " + rg.getSelectedOption());
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
