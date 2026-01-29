package com.cpz.processingtemplate.view.dto;

import com.cpz.processingtemplate.interfaces.Parametros;

/**
 * DTO de render (paquete {@code view.dto}) para el dibujo de rectángulos en el frame actual.
 * <p>
 * Los DTOs de render son inmutables, viven un solo frame y no contienen lógica. La View
 * los crea y el Renderer los consume.
 * </p>
 *
 * @param x posición x
 * @param y posición y
 * @param w ancho
 * @param h alto
 * @param c color de relleno (ARGB)
 * @author CPZ
 */
public record ParametrosRectangulo(float x, float y, float w, float h, int c) implements Parametros {

}
