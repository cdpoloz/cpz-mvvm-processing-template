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
import com.cpz.processing.template.ui.MovilRadial;
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

    private NoiseSource perlin;
    private List<MovilRadial> lstMovilesRadiales;
    private float radioMaxIni, radioMinIni;
    private PVector pIni;

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
        perlin = new PerlinNoise(1234L);
        pIni = new PVector(width * 0.5f, height * 0.5f);
        lstMovilesRadiales = new ArrayList<>();
        radioMinIni = 0;
        radioMaxIni = 50;
        float velocidad = 0.05f;
        float dRadioMin = 0.5f;
        float dRadioMax = 2f;
        float da = 4f;
        int cantidad = (int) (360 / da);
        for (int i = 0; i < cantidad; i++) {
            MovilRadial mr = new MovilRadial();
            mr.setPosicionInicial(pIni.x, pIni.y);
            mr.setAngulo((float) Math.toRadians(i * da));
            mr.setNoise(new NoiseValue(perlin, random(1000), velocidad));
            mr.setRadioMinIni(radioMinIni);
            mr.setRadioMin(radioMinIni);
            mr.setRadioMaxIni(radioMaxIni);
            mr.setRadioMax(radioMaxIni);
            mr.setDeltaRadioMin(dRadioMin);
            mr.setDeltaRadioMax(dRadioMax);
            mr.setUpdateRangoRadio(checkboxes.get("chkUpdateRangoRadio").isChecked());
            lstMovilesRadiales.add(mr);
        }
        sliders.get("sldRadioMaxIni").setValue(new BigDecimal(radioMaxIni));
        sliders.get("sldDeltaAngulo").setValue(new BigDecimal(da));
        labels.get("lblRadioMaxIni").setText("RadioMaxIni\n" + String.format("%.0f", radioMaxIni));
        labels.get("lblVelocidad").setText("Velocidad\n" + String.format("%.3f", velocidad));
        labels.get("lblDeltaRadioMin").setText("dRadioMin\n" + String.format("%.2f", dRadioMin));
        labels.get("lblDeltaRadioMax").setText("dRadioMax\n" + String.format("%.2f", dRadioMax));
        labels.get("lblDeltaAngulo").setText("dAngulo\n" + String.format("%.1f", da));
    }

    public void draw() {
        background(32);
        lstMovilesRadiales.forEach(MovilRadial::update);
        int cantidadFuera = 0;
        boolean todosFuera = true;
        for (MovilRadial mr : lstMovilesRadiales) {
            todosFuera = todosFuera && mr.isFueraRadioMaxIni();
            if (mr.isFueraRadioMaxIni()) cantidadFuera++;
        }
        if (todosFuera) lstMovilesRadiales.forEach(MovilRadial::reset);
        float f = map(cantidadFuera, 0, lstMovilesRadiales.size(), 1, 0);
        int c = color(255, 0, 255, f * 255);
        stroke(c);
        strokeWeight(0.5f);
        for (int i = 1; i < lstMovilesRadiales.size(); i++) {
            PVector pp = lstMovilesRadiales.get(i - 1).getPosicion();
            PVector p = lstMovilesRadiales.get(i).getPosicion();
            line(pp.x, pp.y, p.x, p.y);
        }
        PVector pp = lstMovilesRadiales.getFirst().getPosicion();
        PVector p = lstMovilesRadiales.getLast().getPosicion();
        line(pp.x, pp.y, p.x, p.y);
        // controles
        controls.values().forEach(Control::draw);
        overlayManager.getActiveOverlays().forEach(entry -> entry.getRender().run());
    }

    // <editor-fold defaultstate="collapsed" desc="*** control events ***">
    public void btnClicked(String code) {
        if (code == null) return;
    }

    public void chkClicked(String code, boolean status) {
        if (code == null) return;
        if (code.equals("chkUpdateRangoRadio")) {
            lstMovilesRadiales.forEach(mr -> mr.setUpdateRangoRadio(status));
            if (!status) lstMovilesRadiales.forEach(MovilRadial::reset);
        }
    }

    public void ddChanged(DropDown dd) {
        if (dd == null) return;
    }

    public void nfChanged(NumericField nf) {
        if (nf == null) return;
    }

    public void rgClicked(RadioGroup rg) {
        if (rg == null) return;
    }

    public void sldChanged(String code, BigDecimal value) {
        if (code == null || value == null) return;
        switch (code) {
            case "sldRadioMaxIni" -> {
                lstMovilesRadiales.forEach(mr -> {
                    mr.setRadioMaxIni(value.floatValue());
                    mr.reset();
                });
                labels.get(code.replace("sld", "lbl")).setText("RadioMaxIni\n" + String.format("%.0f", value));
            }
            case "sldVelocidad" -> {
                labels.get(code.replace("sld", "lbl")).setText("Velocidad\n" + String.format("%.3f", value));
                lstMovilesRadiales.forEach(mr -> mr.setNoise(new NoiseValue(perlin, random(1000), value.floatValue())));
            }
            case "sldDeltaRadioMin" -> {
                labels.get(code.replace("sld", "lbl")).setText("dRadioMin\n" + String.format("%.2f", value));
                lstMovilesRadiales.forEach(mr -> {
                    mr.setDeltaRadioMin(value.floatValue());
                    mr.reset();
                });
            }
            case "sldDeltaRadioMax" -> {
                labels.get(code.replace("sld", "lbl")).setText("dRadioMax\n" + String.format("%.2f", value));
                lstMovilesRadiales.forEach(mr -> {
                    mr.setDeltaRadioMax(value.floatValue());
                    mr.reset();
                });
            }
            case "sldDeltaAngulo" -> {
                labels.get(code.replace("sld", "lbl")).setText("dAngulo\n" + String.format("%.2f", value));
                lstMovilesRadiales.clear();
                float da = value.floatValue();
                float velocidad = sliders.get("sldVelocidad").getValue().floatValue();
                float dRadioMin = sliders.get("sldDeltaRadioMin").getValue().floatValue();
                float dRadioMax = sliders.get("sldDeltaRadioMax").getValue().floatValue();
                radioMaxIni = sliders.get("sldRadioMaxIni").getValue().floatValue();
                int cantidad = (int) (360 / da);
                for (int i = 0; i < cantidad; i++) {
                    MovilRadial mr = new MovilRadial();
                    mr.setPosicionInicial(pIni.x, pIni.y);
                    mr.setAngulo((float) Math.toRadians(i * da));
                    mr.setNoise(new NoiseValue(perlin, random(1000), velocidad));
                    mr.setRadioMinIni(radioMinIni);
                    mr.setRadioMin(radioMinIni);
                    mr.setRadioMaxIni(radioMaxIni);
                    mr.setRadioMax(radioMaxIni);
                    mr.setDeltaRadioMin(dRadioMin);
                    mr.setDeltaRadioMax(dRadioMax);
                    mr.setUpdateRangoRadio(checkboxes.get("chkUpdateRangoRadio").isChecked());
                    lstMovilesRadiales.add(mr);
                }
            }
        }
    }

    public void tfChanged(String code, String text) {
        if (code == null) return;
    }

    public void tglClicked(String code, int status) {
        if (code == null) return;
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
        else if (keyCode == 32) checkboxes.get("chkUpdateRangoRadio").setChecked(!checkboxes.get("chkUpdateRangoRadio").isChecked());
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
