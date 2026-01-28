package com.cpz.processingtemplate.logging;

import org.jetbrains.annotations.NotNull;

/**
 * Clase utilitaria que genera mensajes de log estandarizados para errores y operaciones fallidas.
 * <p>
 * Proporciona métodos estáticos para construir mensajes de error o aviso relacionados con archivos,
 * consultas de datos, actualizaciones de datos y carga de imágenes.
 * </p>
 *
 * Ejemplo de uso:
 * <pre>
 *     String mensaje = MensajeLog.mensajeErrorArchivo("/ruta/al/archivo.txt");
 *     logger.severe(mensaje);
 * </pre>
 *
 * @author CPZ
 */
public class MensajeLog {

    /**
     * Genera un mensaje indicando que no se pudo cargar un archivo.
     *
     * @param ruta la ruta del archivo que no se pudo cargar
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeErrorArchivo(String ruta) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo cargar el archivo: ").append(ruta);
        return sb.toString();
    }

    /**
     * Genera un mensaje indicando que no se pudo descargar un dato específico.
     *
     * @param codigo el código del dato que se intentó descargar
     * @param mensajeException mensaje de excepción o detalle del error
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeConsultaDato(String codigo, String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo descargar el dato seleccionado: ").append(codigo).append(" - ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Genera un mensaje indicando que no se pudieron descargar múltiples datos.
     *
     * @param mensajeException mensaje de excepción o detalle del error
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeConsultaDatos(String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudieron descargar los datos: ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Genera un mensaje indicando que no se pudo actualizar un dato específico.
     *
     * @param codigo el código del dato que se intentó actualizar
     * @param mensajeException mensaje de excepción o detalle del error
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeUpdateDato(String codigo, String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo actualizar el dato seleccionado: ").append(codigo).append(" - ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Genera un mensaje indicando que no se pudo finalizar la configuración.
     *
     * @param mensajeException mensaje de excepción o detalle del error
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeFinSetup(String mensajeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo finalizar la configuración: ").append(mensajeException);
        return sb.toString();
    }

    /**
     * Genera un mensaje indicando que no se pudo cargar una imagen específica.
     *
     * @param nombreArchivo el nombre del archivo de imagen
     * @return mensaje de error formateado
     */
    @NotNull
    public static String mensajeErrorCargaImagen(String nombreArchivo) {
        StringBuilder sb = new StringBuilder();
        sb.append("No se pudo cargar la imagen: '").append(nombreArchivo).append("'");
        return sb.toString();
    }
}
