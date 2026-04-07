package com.cpz.processingtemplate.main;

import com.cpz.processingtemplate.config.Config;
import com.cpz.processingtemplate.logging.Log;
import com.cpz.processingtemplate.logging.LogMessage;
import processing.core.PApplet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * Bootstrap entry point ({@code main} package) responsible for starting the application.
 * <p>
 * Loads configuration, prepares the main {@link Sketch}, and launches the Processing runtime.
 * This class contains no MVVM logic.
 * </p>
 *
 * @author CPZ
 */
public class Launcher {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    public static final Log LOG = new Log(Launcher.class.getName());
    public static final Properties PROPS = new Properties();
    // </editor-fold>

    /**
     * Application entry point.
     * <p>
     * Loads properties, configures logging shutdown, creates the {@link Sketch}, initializes it,
     * and runs Processing on a dedicated thread.
     * </p>
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("en-US"));
        // Shutdown hook to close handlers when the program exits.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (var handler : LOG.getHandlers()) handler.close();
        }));
        // Properties.
        String propertiesPath = "data" + File.separator + "config.properties";
        try (FileInputStream fis = new FileInputStream(propertiesPath)) {
            PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(LogMessage.fileLoadError(propertiesPath));
            System.exit(1);
        }
        // Prepare the main sketch.
        Sketch sketch = Config.createWindowSketchConfiguration();
        Config.initializeSketch(sketch);
        // Run the main program.
        new Thread(() -> PApplet.runSketch(new String[]{"com.cpz.processingtemplate.main.Sketch"}, sketch)).start();
    }

}
