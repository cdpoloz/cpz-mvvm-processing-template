package com.cpz.processingtemplate.config;

import com.cpz.processingtemplate.main.Launcher;
import com.cpz.processingtemplate.main.Sketch;
import org.jetbrains.annotations.NotNull;
import processing.opengl.PJOGL;

import java.awt.*;
import java.io.File;

import static com.cpz.processingtemplate.main.Launcher.PROPS;

/**
 * @author CPZ
 */
public class Config {

    @NotNull
    public static Sketch configuracionVentanaSketch() {
        // icono de la ventana
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + PROPS.getProperty("iconoVentana"));
        // sketch principal
        Sketch sketch = new Sketch();
        // dimensiones de la ventana
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice pantalla = ge.getDefaultScreenDevice();
        sketch.setAnchoPantalla(pantalla.getDisplayMode().getWidth());
        sketch.setAltoPantalla(pantalla.getDisplayMode().getHeight());
        // factor de proporción de la pantalla
        sketch.setFactorHorizontalPantalla(Float.parseFloat(Launcher.PROPS.getProperty("factorHorizontalPantalla")));
        sketch.setFactorVerticalPantalla(Float.parseFloat(Launcher.PROPS.getProperty("factorVerticalPantalla")));
        // suavizado y cuadros por segundo
        sketch.setSuavizado(Integer.parseInt(PROPS.getProperty("suavizado")));
        sketch.setFps(Integer.parseInt(PROPS.getProperty("fps")));
        // título de la ventana
        sketch.setTituloVentana(PROPS.getProperty("tituloVentana"));
        return sketch;
    }

    public static void inicializarSketch(@NotNull Sketch sketch) {
        sketch.inicializarSketch(Integer.parseInt(Launcher.PROPS.getProperty("periodoTimer")));
    }
}
