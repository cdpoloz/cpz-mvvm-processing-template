package com.cpz.processingtemplate.config;

import com.cpz.processingtemplate.main.Launcher;
import com.cpz.processingtemplate.main.Sketch;
import org.jetbrains.annotations.NotNull;
import processing.opengl.PJOGL;

import java.awt.*;
import java.io.File;

import static com.cpz.processingtemplate.main.Launcher.PROPS;

/**
 * Bootstrap configuration ({@code config} package) that prepares the Processing {@link Sketch}
 * from the application properties.
 * <p>
 * This class wires window parameters and runtime settings. It contains no business logic and does
 * not access ViewModel or Model state.
 * </p>
 *
 * @author CPZ
 */
public class Config {

    /**
     * Builds and configures the main {@link Sketch} instance using {@link Launcher#PROPS}.
     * <p>
     * Sets the window icon, screen dimensions, scale factors, antialiasing, fps, and window title.
     * </p>
     *
     * @return a configured {@link Sketch} ready to be initialized
     */
    @NotNull
    public static Sketch createWindowSketchConfiguration() {
        // Window icon.
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + PROPS.getProperty("windowIcon"));
        // Main sketch.
        Sketch sketch = new Sketch();
        // Window dimensions.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = ge.getDefaultScreenDevice();
        sketch.setScreenWidth(screen.getDisplayMode().getWidth());
        sketch.setScreenHeight(screen.getDisplayMode().getHeight());
        // Screen scale factors.
        sketch.setHorizontalScreenScaleFactor(Float.parseFloat(Launcher.PROPS.getProperty("horizontalScreenScaleFactor")));
        sketch.setVerticalScreenScaleFactor(Float.parseFloat(Launcher.PROPS.getProperty("verticalScreenScaleFactor")));
        // Antialiasing and frames per second.
        sketch.setSmoothing(Integer.parseInt(PROPS.getProperty("smoothing")));
        sketch.setFps(Integer.parseInt(PROPS.getProperty("fps")));
        // Window title.
        sketch.setWindowTitle(PROPS.getProperty("windowTitle"));
        return sketch;
    }

    /**
     * Initializes the given sketch with timer settings from the properties file.
     *
     * @param sketch sketch instance to initialize
     */
    public static void initializeSketch(@NotNull Sketch sketch) {
        sketch.initializeSketch(Integer.parseInt(Launcher.PROPS.getProperty("timerInterval")));
    }
}
