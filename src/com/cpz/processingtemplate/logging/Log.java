package com.cpz.processingtemplate.logging;

import com.cpz.processingtemplate.config.ConfigLog;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Componente de infraestructura de logging (paquete {@code logging}) que configura los handlers
 * definidos en {@link ConfigLog} y expone un {@link Logger} preconfigurado.
 * <p>
 * Esta clase no participa en la lógica MVVM; centraliza únicamente la configuración de logging.
 * </p>
 *
 * @author CPZ
 */
public class Log extends Logger {

    /**
     * Crea un logger con los handlers de consola y archivo configurados.
     *
     * @param name nombre del logger
     */
    public Log(String name) {
        super(name, null);
        addHandler(ConfigLog.obtenerLogConsoleHandler());
        addHandler(ConfigLog.obtenerLogFileHandler());
        setLevel(Level.ALL);
    }

}
