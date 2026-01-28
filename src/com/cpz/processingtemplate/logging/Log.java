package com.cpz.processingtemplate.logging;

import com.cpz.processingtemplate.config.ConfigLog;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que extiende {@link Logger} para configurar un sistema de logging personalizado.
 * <p>
 * Esta clase añade automáticamente los handlers definidos en {@link ConfigLog} para
 * consola y archivo, y establece el nivel de logging a {@link Level#ALL}.
 * </p>
 *
 * <p>Uso típico:</p>
 * <pre>
 *     Log log = new Log("MiLogger");
 *     log.info("Mensaje de información");
 * </pre>
 *
 * @author CPZ
 */
public class Log extends Logger {

    /**
     * Construye un logger con el nombre especificado, agregando los handlers de
     * consola y archivo definidos en {@link ConfigLog}, y estableciendo el nivel
     * de logging a {@link Level#ALL}.
     *
     * @param name el nombre del logger
     */
    public Log(String name) {
        super(name, null);
        addHandler(ConfigLog.obtenerLogConsoleHandler());
        addHandler(ConfigLog.obtenerLogFileHandler());
        setLevel(Level.ALL);
    }

}
