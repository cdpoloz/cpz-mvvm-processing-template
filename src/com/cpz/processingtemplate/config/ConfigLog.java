package com.cpz.processingtemplate.config;

import com.cpz.processingtemplate.logging.LogFormatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * Clase de configuración de logging para la aplicación.
 * <p>
 * Proporciona métodos para obtener handlers de logging tanto para archivos como
 * para la consola, con formatos y niveles predefinidos.
 * </p>
 * Los logs de archivo se almacenan en la carpeta "log" con nombres que incluyen la fecha actual.
 *
 * @author CPZ
 */
public class ConfigLog {

    /**
     * Crea y devuelve un {@link FileHandler} para registrar mensajes en un archivo.
     * <p>
     * Si la carpeta "log" no existe, se crea automáticamente. El nombre del archivo
     * incluye la fecha actual con formato "ExeraCSP_YYYY-MM-DD.log".
     * El handler utiliza {@link LogFormatter} para formatear los mensajes y su nivel
     * de log está configurado en {@link Level#INFO}.
     *
     * @return un {@link FileHandler} configurado, o {@code null} si ocurre un error de IO.
     */
    @Nullable
    public static FileHandler obtenerLogFileHandler() {
        try {
            Path carpeta = Paths.get("log");
            if (Files.notExists(carpeta)) {
                Files.createDirectories(carpeta);
            }
            LocalDate fechaHoy = LocalDate.now();
            String s
                    = "ProcessingTemplate_"
                    + fechaHoy.getYear()
                    + "-"
                    + String.format("%02d", fechaHoy.getMonthValue())
                    + "-"
                    + String.format("%02d", fechaHoy.getDayOfMonth())
                    + ".log";
            FileHandler fileHandler = new FileHandler(carpeta + File.separator + s, true);
            fileHandler.setFormatter(new LogFormatter());
            fileHandler.setLevel(Level.INFO); //INFO
            return fileHandler;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Crea y devuelve un {@link ConsoleHandler} para registrar mensajes en la consola.
     * <p>
     * El handler utiliza {@link LogFormatter} para formatear los mensajes y su nivel
     * de log está configurado en {@link Level#CONFIG}.
     *
     * @return un {@link ConsoleHandler} configurado.
     */
    @NotNull
    public static ConsoleHandler obtenerLogConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.CONFIG); //CONFIG
        return consoleHandler;
    }
}
