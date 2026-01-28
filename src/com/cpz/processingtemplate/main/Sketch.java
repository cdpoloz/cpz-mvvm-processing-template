package com.cpz.processingtemplate.main;

import com.cpz.processingtemplate.input.Mouse;
import com.cpz.processingtemplate.input.Teclado;
import com.cpz.processingtemplate.model.AppState;
import com.cpz.processingtemplate.view.SketchView;
import com.cpz.processingtemplate.view.dto.ParametrosElipse;
import com.cpz.processingtemplate.view.dto.ParametrosRectangulo;
import com.cpz.processingtemplate.viewmodel.MainViewModel;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.event.MouseEvent;

import static com.cpz.processingtemplate.main.Launcher.LOG;

/**
 * @author CPZ
 */
public class Sketch extends PApplet {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private @Setter Mouse mouse;
    private @Setter Teclado teclado;
    private @Setter int anchoPantalla, altoPantalla, suavizado, fps;
    private @Setter float factorHorizontalPantalla, factorVerticalPantalla;
    private @Setter String tituloVentana;
    private final MainViewModel viewModel;
    private final SketchView sketchView;
    // </editor-fold>

    public Sketch() {
        viewModel = new MainViewModel(new AppState());
        sketchView = new SketchView(this);
    }

    /**
     * Configuración inicial del sketch de Processing.
     * Define tamaño de ventana y suavizado
     */
    @Override
    public void settings() {
        LOG.info("Inicio settings");
        size((int) (anchoPantalla * factorHorizontalPantalla), (int) (altoPantalla * factorVerticalPantalla), P2D);
        smooth(suavizado);
        LOG.info("Fin settings");
    }

    /**
     * Inicializa el sketch y configura el título de la ventana
     */
    @Override
    public void setup() {
        LOG.info("Inicio setup");
        frameRate(fps);
        surface.setTitle(tituloVentana);
        // input
        mouse = new Mouse(viewModel);
        teclado = new Teclado(viewModel);
        LOG.info("Fin setup");
    }

    public void inicializarSketch(int periodoTimer) {
        viewModel.inicializar(periodoTimer);
    }

    /**
     * Bucle principal de Processing que se ejecuta en cada frame.
     * Dependiendo del estado actual, llama a la configuración, actualización o renderizado correspondiente.
     */
    @Override
    public void draw() {
        update();
        aplicarEstadoVisual();
        dibujarUI();
    }

    private void update() {
        viewModel.actualizarTimer(millis());
    }

    private void aplicarEstadoVisual() {
        if (viewModel.isCursorVisible()) cursor();
        else noCursor();
        rectMode(viewModel.getRectMode());
    }

    private void dibujarUI() {
        background(viewModel.getColorFondo());
        ParametrosRectangulo rect = new ParametrosRectangulo(
                mouseX,
                mouseY,
                anchoPantalla * 0.1f,
                altoPantalla * 0.1f,
                viewModel.getColorRectangulo()
        );
        sketchView.dibujar(rect);
        if (viewModel.isTimerRunning()) {
            ParametrosElipse circle = new ParametrosElipse(
                    width - altoPantalla * 0.04f,
                    altoPantalla * 0.04f,
                    altoPantalla * 0.025f,
                    viewModel.getColorCirculo()
            );
            sketchView.dibujar(circle);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="*** interrupciones ***">
    @Override
    public void mouseWheel(@NotNull MouseEvent event) {
        mouse.mouseWheel(event.getCount());
    }

    @Override
    public void mouseReleased() {
        if (mouseButton == LEFT) mouse.mouseReleasedLeft();
        else if (mouseButton == CENTER) mouse.mouseReleasedCenter();
        else if (mouseButton == RIGHT) mouse.mouseReleasedRight();
        else mouse.mouseReleasedOther();
    }

    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) mouse.mousePressedLeft();
        else if (mouseButton == CENTER) mouse.mousePressedCenter();
        else if (mouseButton == RIGHT) mouse.mousePressedRight();
        else mouse.mousePressedOther();
    }

    @Override
    public void mouseDragged() {
        if (mouseButton == LEFT) mouse.mouseDraggedLeft();
        else if (mouseButton == CENTER) mouse.mouseDraggedCenter();
        else if (mouseButton == RIGHT) mouse.mouseDraggedRight();
        else mouse.mouseDraggedOther();
    }

    @Override
    public void keyReleased() {
        teclado.keyReleased(key, keyCode);
    }

    @Override
    public void keyPressed() {
        teclado.keyPressed(key, keyCode);
    }

    @Override
    public void windowResized() {
    }
    // </editor-fold>
}
