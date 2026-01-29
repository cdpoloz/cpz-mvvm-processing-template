package com.cpz.processingtemplate.view;

import com.cpz.processingtemplate.interfaces.Parametros;
import com.cpz.processingtemplate.view.dto.ParametrosElipse;
import com.cpz.processingtemplate.view.dto.ParametrosRectangulo;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;

/**
 * Componente de la capa Renderer (paquete {@code view}) que encapsula el dibujo puro en Processing.
 * <p>
 * Esta View de render es genérica y no conoce tipos de dominio: recibe objetos desde la View,
 * los traduce a llamadas de Processing cuando aplica y no realiza lógica de negocio, no mantiene
 * state ni accede al ViewModel.
 * </p>
 * <p>
 * La responsabilidad de interpretar qué se dibuja recae en capas superiores (ViewModel / coordinación
 * de render), manteniendo este Renderer desacoplado como plantilla base.
 * </p>
 *
 * @author CPZ
 */
public class SketchView {

    private final PApplet sketch;

    /**
     * Crea un renderer vinculado al sketch de Processing indicado.
     *
     * @param sketch {@link PApplet} de Processing usado para todas las operaciones de dibujo
     */
    public SketchView(PApplet sketch) {
        this.sketch = sketch;
    }

    /**
     * Punto de extensión genérico para el render de la plantilla.
     * <p>
     * Acepta {@code Object...} de forma intencional para evitar acoplamiento a tipos de dominio.
     * La View decide qué objetos enviar y la interpretación de esos objetos pertenece a capas
     * superiores (ViewModel / coordinación de render), no a esta clase.
     * </p>
     *
     * @param o objetos de render proporcionados por la View
     */
    public void dibujar(Object... o) {
        if (o == null) return;
        // Para esta plantilla de ejemplo se evalúa solo un parámetro de entrada
        // o[0] el cual es un objeto de tipo Parametros. Para otras implementaciones
        // se deberán agregar métodos adicionales específicos dependiendo de la
        // necesidad.
        if (o.length == 1 && o[0] instanceof Parametros) dibujarFiguraGeometricas((Parametros) o[0]);
    }

    /**
     * Despacha un DTO de render a su método de dibujo concreto.
     * <p>
     * Es invocado por la View al construir DTOs por frame; no se almacena state.
     * </p>
     *
     * @param p DTO de render creado en la View para el frame actual
     */
    private void dibujarFiguraGeometricas(@NotNull Parametros p) {
        if (p instanceof ParametrosRectangulo) dibujarRectangulo((ParametrosRectangulo) p);
        else if (p instanceof ParametrosElipse) dibujarElipse((ParametrosElipse) p);
    }

    /**
     * Dibuja un DTO de rectángulo mapeando sus campos a llamadas de Processing.
     *
     * @param p parámetros del rectángulo para el frame actual
     */
    private void dibujarRectangulo(@NotNull ParametrosRectangulo p) {
        dibujarRectangulo(p.x(), p.y(), p.w(), p.h(), p.c());
    }

    /**
     * Dibuja un DTO de elipse mapeando sus campos a llamadas de Processing.
     *
     * @param p parámetros de la elipse para el frame actual
     */
    private void dibujarElipse(@NotNull ParametrosElipse p) {
        dibujarElipse(p.x(), p.y(), p.d(), p.d(), p.c());
    }

    /**
     * Realiza el dibujo del rectángulo aislando el state de Processing.
     *
     * @param x posición x
     * @param y posición y
     * @param w ancho
     * @param h alto
     * @param c color de relleno (ARGB)
     */
    private void dibujarRectangulo(float x, float y, float w, float h, int c) {
        sketch.pushStyle();
        sketch.noStroke();
        sketch.fill(c);
        sketch.rect(x, y, w, h);
        sketch.popStyle();
    }

    /**
     * Realiza el dibujo de la elipse aislando el state de Processing.
     *
     * @param x posición x
     * @param y posición y
     * @param w ancho
     * @param h alto
     * @param c color de relleno (ARGB)
     */
    private void dibujarElipse(float x, float y, float w, float h, int c) {
        sketch.pushStyle();
        sketch.noStroke();
        sketch.fill(c);
        sketch.ellipse(x, y, w, h);
        sketch.popStyle();
    }

}
