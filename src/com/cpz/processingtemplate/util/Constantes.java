package com.cpz.processingtemplate.util;

import static com.cpz.processingtemplate.util.Tools.construirColor;

/**
 * @author CPZ
 */
public class Constantes {

    // teclas especiales
    public static final int BACKSPACE = 8;
    public static final int BARRA_ESPACIADORA = 32;
    public static final int FLECHA_ABAJO = 40;
    public static final int FLECHA_ARRIBA = 38;
    public static final int FLECHA_DERECHA = 39;
    public static final int FLECHA_IZQUIERDA = 37;
    public static final int TECLA_MAS = 139;
    public static final int TECLA_MENOS = 140;
    // rect modes (compatibles con Processing)
    public static final int RECT_MODE_CORNER = 0;
    public static final int RECT_MODE_CENTER = 3;
    // colores
    public static final int BLANCO = construirColor(255);
    public static final int NEGRO = construirColor(0);
    public static final int GRIS_MEDIO = construirColor(128);
    public static final int ROJO = construirColor(255, 0, 0, 255);
    public static final int VERDE = construirColor(0, 255, 0, 255);
    public static final int AZUL = construirColor(0, 0, 255, 255);
    public static final int CYAN = construirColor(0, 255, 255, 255);
    public static final int MAGENTA = construirColor(255, 0, 255, 255);
    public static final int AMARILLO = construirColor(255, 255, 0, 255);

}
