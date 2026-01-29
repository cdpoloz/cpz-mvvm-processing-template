package com.cpz.processingtemplate.logging;

import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Formateador de infraestructura de logging (paquete {@code logging}) usado por los handlers configurados.
 * <p>
 * Extiende {@link SimpleFormatter} para proporcionar un formato de línea determinista y legible
 * que incluye timestamp, nivel de log y origen (clase y método).
 * </p>
 *
 * <p>Formato de línea:</p>
 * <pre>
 * yyyy-MM-dd HH:mm:ss.SSS LEVEL Class.Method > Mensaje
 * </pre>
 *
 * @author CPZ
 */
public class LogFormatter extends SimpleFormatter {

    /**
     * Formatea un {@link LogRecord} según el patrón de línea configurado.
     *
     * @param record registro de log a formatear
     * @return línea formateada para la salida de log
     */
    @Override
    public String format(@NotNull LogRecord record) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .withZone(ZoneId.systemDefault());
        String methodName = record.getSourceMethodName() != null ? record.getSourceMethodName() : "";
        String className = record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf('.') + 1);
        String origin = className + "." + methodName.replace("lambda$", "").split("\\$")[0];
        return String.format("%1$s %2$s %3$s > %4$s%n",
                dateTimeFormatter.format(record.getInstant()),
                record.getLevel(),
                origin,
                record.getMessage());
    }
}
