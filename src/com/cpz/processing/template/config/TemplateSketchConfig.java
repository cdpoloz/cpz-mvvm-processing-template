package com.cpz.processing.template.config;

import com.cpz.processing.controls.controls.Control;
import com.cpz.processing.controls.controls.button.Button;
import com.cpz.processing.controls.controls.config.ControlConfigLoader;
import com.cpz.processing.controls.controls.label.Label;
import com.cpz.processing.controls.controls.slider.Slider;
import com.cpz.processing.controls.controls.toggle.Toggle;
import com.cpz.processing.template.logging.LogMessage;
import com.cpz.processing.template.main.TemplateSketch;
import processing.opengl.PJOGL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import static com.cpz.processing.template.main.Launcher.LOG;
import static processing.core.PConstants.P2D;

/**
 * @author CPZ
 */
public class TemplateSketchConfig {

    public static final Properties TEMPLATE_PROPS = new Properties();

    public static void settings(TemplateSketch sk) {
        LOG.info("Starting settings");
        String propertiesPath = "data" + File.separator + "config" + File.separator + "template-sketch.properties";
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            TEMPLATE_PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(LogMessage.fileLoadError(propertiesPath));
            System.exit(1);
        }
        // icono de la ventana
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + TEMPLATE_PROPS.getProperty("window.icon"));
        // tamaño de ventana
        sk.size(Integer.parseInt(TEMPLATE_PROPS.getProperty("sketch.width")), Integer.parseInt(TEMPLATE_PROPS.getProperty("sketch.height")), P2D);
        // smoothing
        sk.smooth(Integer.parseInt(TEMPLATE_PROPS.getProperty("sketch.smoothing")));
        LOG.info("Finished settings");
    }

    public static void initialSetup(TemplateSketch sk) {
        if (sk == null) return;
        LOG.info("Starting initial setup");
        sk.frameRate(Integer.parseInt(TEMPLATE_PROPS.getProperty("sketch.fps")));
        sk.getSurface().setTitle(TEMPLATE_PROPS.getProperty("window.title"));
        LOG.info("Finished initial setup");
    }

    public static Map<String, Control> configurarControles(TemplateSketch sk) {
        if (sk == null) return null;
        // btnTemplate
        String templateConfigPath = "data" + File.separator + "config" + File.separator + "template-sketch.json";
        ControlConfigLoader loader = new ControlConfigLoader(sk);
        Map<String, Control> controles = loader.load(templateConfigPath);
        controles
                .values()
                .stream()
                .filter(c -> c instanceof Button)
                .forEach(btn -> ((Button) btn).setClickListener(() -> sk.btnClicked(btn.getCode())));
        controles
                .values()
                .stream()
                .filter(c -> c instanceof Slider)
                .forEach(sld -> ((Slider) sld).setChangeListener(valor -> sk.sldChanged(sld.getCode(), valor)));
        controles
                .values()
                .stream()
                .filter(c -> c instanceof Toggle)
                .forEach(tgl -> ((Toggle) tgl).setChangeListener(estado -> sk.tglClicked(tgl.getCode(), estado)));
        return controles;
    }

    public static Map<String, Button> filtrarBotones(Map<String, Control> controles) {
        if (controles == null) return null;
        Map<String, Button> botones = new HashMap<>();
        controles.values().stream().filter(c -> c instanceof Button).forEach(btn -> botones.put(btn.getCode(), (Button) btn));
        return botones;
    }

    public static Map<String, Label> filtrarLabels(Map<String, Control> controles) {
        if (controles == null) return null;
        Map<String, Label> labels = new HashMap<>();
        controles.values().stream().filter(c -> c instanceof Label).forEach(lbl -> labels.put(lbl.getCode(), (Label) lbl));
        return labels;
    }

    public static Map<String, Slider> filtrarSliders(Map<String, Control> controles) {
        if (controles == null) return null;
        Map<String, Slider> sliders = new HashMap<>();
        controles.values().stream().filter(c -> c instanceof Slider).forEach(sld -> sliders.put(sld.getCode(), (Slider) sld));
        return sliders;
    }

    public static Map<String, Toggle> filtrarToggles(Map<String, Control> controles) {
        if (controles == null) return null;
        Map<String, Toggle> toggles = new HashMap<>();
        controles.values().stream().filter(c -> c instanceof Toggle).forEach(tgl -> toggles.put(tgl.getCode(), (Toggle) tgl));
        return toggles;
    }
}
