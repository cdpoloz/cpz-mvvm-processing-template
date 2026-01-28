package com.cpz.processingtemplate.logging;

import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Formateador personalizado para registros de logging.
 * <p>
 * Extiende {@link SimpleFormatter} para definir un formato de salida de logs
 * más legible y detallado, incluyendo fecha, hora, nivel de log y el origen
 * (clase y método) del mensaje.
 * </p>
 *
 * <p>Formato de cada línea de log:</p>
 * <pre>
 * yyyy-MM-dd HH:mm:ss.SSS NIVEL Clase.Metodo > Mensaje
 * </pre>
 * <p>
 * Ejemplo:
 * <pre>
 * 2025-11-21 10:12:34.123 INFO MiClase.miMetodo > Ejemplo de mensaje
 * </pre>
 *
 * @author CPZ
 */
public class LogFormatter extends SimpleFormatter {

    /**
     * Formatea un registro de log {@link LogRecord} según el patrón definido.
     * Incluye fecha y hora, nivel de log, clase y método de origen, y el mensaje.
     *
     * @param record el registro de log a formatear
     * @return una cadena con el registro de log formateado
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
