package com.cpz.processingtemplate.logging;

import org.jetbrains.annotations.NotNull;

/**
 * Utilidad de infraestructura de logging (paquete {@code logging}) que proporciona mensajes
 * de error estandarizados para el bootstrap de la aplicación y operaciones de IO.
 * <p>
 * Es una utilidad sin state y no contiene lógica de negocio.
 * </p>
 *
 * @author CPZ
 */
public class MensajeLog {

    /**
     * Construye un mensaje indicando que no se pudo cargar un archivo.
     *
     * @param ruta ruta del archivo que no se pudo cargar
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeErrorArchivo(String ruta) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo cargar el archivo: ").append(ruta);
        return sb.toString();
    }

    /**
     * Construye un mensaje indicando que no se pudo recuperar un dato específico.
     *
     * @param codigo identificador del dato solicitado
     * @param mensajeException detalle de la excepción o descripción del fallo
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeConsultaDato(String codigo, String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo descargar el dato seleccionado: ").append(codigo).append(" - ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Construye un mensaje indicando que no se pudieron recuperar múltiples datos.
     *
     * @param mensajeException detalle de la excepción o descripción del fallo
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeConsultaDatos(String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudieron descargar los datos: ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Construye un mensaje indicando que no se pudo actualizar un dato específico.
     *
     * @param codigo identificador del dato solicitado
     * @param mensajeException detalle de la excepción o descripción del fallo
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeUpdateDato(String codigo, String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo actualizar el dato seleccionado: ").append(codigo).append(" - ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Construye un mensaje indicando que no se pudo finalizar el setup.
     *
     * @param mensajeException detalle de la excepción o descripción del fallo
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeFinSetup(String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo finalizar la configuración: ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Construye un mensaje indicando que no se pudo cargar una imagen específica.
     *
     * @param nombreArchivo nombre del archivo de imagen que no se pudo cargar
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeErrorCargaImagen(String nombreArchivo) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo cargar la imagen: '").append(nombreArchivo).append("'");
        return sb.toString();
    }
}
