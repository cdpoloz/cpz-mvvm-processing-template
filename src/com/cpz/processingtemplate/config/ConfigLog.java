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
 * Configuración de bootstrap (paquete {@code config}) para la infraestructura de logging.
 * <p>
 * Proporciona handlers preconfigurados para salida a archivo y consola. Esta clase no
 * contiene lógica de negocio y es usada por {@link com.cpz.processingtemplate.logging.Log}.
 * </p>
 *
 * @author CPZ
 */
public class ConfigLog {

    /**
     * Crea un {@link FileHandler} que escribe en la carpeta {@code log} usando
     * la fecha actual en el nombre del archivo.
     * <p>
     * Usa {@link LogFormatter} y establece el nivel del handler en {@link Level#INFO}.
     * </p>
     *
     * @return {@link FileHandler} configurado, o {@code null} si ocurre un error de IO
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
     * Crea un {@link ConsoleHandler} para logging en stdout.
     * <p>
     * Usa {@link LogFormatter} y establece el nivel del handler en {@link Level#CONFIG}.
     * </p>
     *
     * @return {@link ConsoleHandler} configurado
     */
    @NotNull
    public static ConsoleHandler obtenerLogConsoleHandler() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.CONFIG); //CONFIG
        return consoleHandler;
    }
}
