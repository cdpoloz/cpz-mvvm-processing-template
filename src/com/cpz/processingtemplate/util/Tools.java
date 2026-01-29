package com.cpz.processingtemplate.util;

import org.jetbrains.annotations.NotNull;

/**
 * Funciones utilitarias sin state (paquete {@code util}) sin lógica de negocio.
 * <p>
 * Proporciona utilidades de construcción de color usadas por constantes y render.
 * </p>
 *
 * @author CPZ
 */
public class Tools {

    /**
     * Construye un color ARGB en entero a partir de un string separado por dos puntos.
     * <p>
     * El formato esperado es {@code "r:g:b:a"} con valores enteros por canal.
     * </p>
     *
     * @param s string de color en formato {@code r:g:b:a}
     * @return color ARGB empaquetado como {@code int}
     */
    public static int construirColor(@NotNull String s) {
        String[] componentes = s.split(":");
        int r = Integer.parseInt(componentes[0]);
        int g = Integer.parseInt(componentes[1]);
        int b = Integer.parseInt(componentes[2]);
        int a = Integer.parseInt(componentes[3]);
        return construirColor(r, g, b, a);
    }

    /**
     * Empaqueta canales RGBA en un único entero ARGB.
     *
     * @param red canal rojo (0-255)
     * @param green canal verde (0-255)
     * @param blue canal azul (0-255)
     * @param alpha canal alfa (0-255)
     * @return color ARGB empaquetado como {@code int}
     */
    public static int construirColor(float red, float green, float blue, float alpha) {
        return ((int) alpha << 24) | ((int) red << 16) | ((int) green << 8) | (int) blue;
    }

    /**
     * Construye un color ARGB en escala de grises con opacidad completa.
     *
     * @param gray canal de gris (0-255)
     * @return color ARGB empaquetado como {@code int}
     */
    public static int construirColor(float gray) {
        return construirColor(gray, gray, gray, 255);
    }

    /**
     * Construye un color ARGB en escala de grises con alfa explícito.
     *
     * @param gray canal de gris (0-255)
     * @param alpha canal alfa (0-255)
     * @return color ARGB empaquetado como {@code int}
     */
    public static int construirColor(float gray, float alpha) {
        return construirColor(gray, gray, gray, alpha);
    }

}
