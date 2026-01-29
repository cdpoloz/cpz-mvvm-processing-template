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
 * Punto de entrada de bootstrap (paquete {@code main}) responsable del inicio de la aplicación.
 * <p>
 * Carga la configuración, prepara el {@link Sketch} principal y lanza el tiempo de ejecución de Processing.
 * Esta clase no contiene lógica MVVM.
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
     * Punto de entrada de la aplicación.
     * <p>
     * Carga properties, configura el cierre de logging, crea el Sketch, lo inicializa
     * y ejecuta Processing en un hilo dedicado.
     * </p>
     *
     * @param args argumentos de línea de comandos (no usados)
     */
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
