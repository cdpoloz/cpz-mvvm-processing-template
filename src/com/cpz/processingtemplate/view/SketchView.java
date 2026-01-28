package com.cpz.processingtemplate.view;

import com.cpz.processingtemplate.interfaces.Parametros;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import com.cpz.processingtemplate.view.dto.ParametrosRectangulo;
import com.cpz.processingtemplate.view.dto.ParametrosElipse;

/**
 * @author CPZ
 */
public class SketchView {

    private final PApplet sketch;

    public SketchView(PApplet sketch) {
        this.sketch = sketch;
    }

    public void dibujarRectangulo(float x, float y, float w, float h, int c) {
        sketch.pushStyle();
        sketch.noStroke();
        sketch.fill(c);
        sketch.rect(x, y, w, h);
        sketch.popStyle();
    }

    public void dibujarElipse(float x, float y, float w, float h, int c) {
        sketch.pushStyle();
        sketch.noStroke();
        sketch.fill(c);
        sketch.ellipse(x, y, w, h);
        sketch.popStyle();
    }

    public void dibujar(@NotNull Parametros p) {
        if (p instanceof ParametrosRectangulo) dibujar((ParametrosRectangulo) p);
        else if (p instanceof ParametrosElipse) dibujar((ParametrosElipse) p);
    }

    private void dibujar(@NotNull ParametrosRectangulo p) {
        dibujarRectangulo(p.x(), p.y(), p.w(), p.h(), p.c());
    }

    private void dibujar(@NotNull ParametrosElipse p) {
        dibujarElipse(p.x(), p.y(), p.d(), p.d(), p.c());
    }

}
