package com.cpz.processingtemplate.main;

import com.cpz.processingtemplate.config.Config;
import com.cpz.processingtemplate.logging.Log;
import com.cpz.processingtemplate.logging.MensajeLog;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * @author CPZ
 */
public class Launcher {

    public static final Log LOG = new Log(Launcher.class.getName());
    public static final Properties PROPS = new Properties();

    static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("en-US"));
        // shutdown hook para cerrar los handlers cuando el programa termine
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (var handler : LOG.getHandlers()) handler.close();
        }));
        // properties
        String rutaProperties = "data" + File.separator + "config.properties";
        try (FileInputStream fis = new FileInputStream(rutaProperties)) {
            PROPS.load(fis);
        } catch (IOException e) {
            LOG.severe(MensajeLog.mensajeErrorArchivo(rutaProperties));
            System.exit(1);
        }
        // se prepara el sketch principal
        Sketch sketch = Config.configuracionVentanaSketch();
        Config.inicializarSketch(sketch);
        // ejecución del programa principal
        new Thread(() -> PApplet.runSketch(new String[]{"com.cpz.processingtemplate.main.Sketch"}, sketch)).start();
    }

}
