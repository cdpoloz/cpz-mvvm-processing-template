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
 * Componente de la capa View (paquete {@code main}) que orquesta el lifecycle de Processing.
 * <p>
 * Recibe tiempo e input desde Processing, delega decisiones al ViewModel, construye DTOs
 * de render por frame y llama al Renderer ({@link SketchView}) para draw.
 * </p>
 * <p>
 * Esta clase no implementa lógica de negocio y no muta el state del Model directamente.
 * </p>
 *
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

    /**
     * Crea la View principal, conectando el ViewModel y el Renderer.
     */
    public Sketch() {
        viewModel = new MainViewModel(new AppState());
        sketchView = new SketchView(this);
    }

    /**
     * Fase settings de Processing.
     * <p>
     * Es llamada una vez por Processing antes de {@link #setup()}. Define tamaño de ventana
     * y suavizado según la configuración proporcionada por {@link com.cpz.processingtemplate.config.Config}.
     * </p>
     */
    @Override
    public void settings() {
        LOG.info("Inicio settings");
        size((int) (anchoPantalla * factorHorizontalPantalla), (int) (altoPantalla * factorVerticalPantalla), P2D);
        smooth(suavizado);
        LOG.info("Fin settings");
    }

    /**
     * Fase setup de Processing.
     * <p>
     * Es llamada una vez después de {@link #settings()}. Define fps, título de la ventana
     * y conecta los adaptadores de input.
     * </p>
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

    /**
     * Inicializa el ViewModel con aquellas variables necesarias para el estado inicial
     * del Sketch. Para esta plantilla de ejemplo sería el periodo de timer configurado.
     * <p>
     * Es invocado por el bootstrap después de crear el Sketch.
     * </p>
     *
     * @param periodoTimer periodo del timer en milisegundos
     */
    public void inicializarSketch(int periodoTimer) {
        viewModel.inicializar(periodoTimer);
    }

    /**
     * Loop principal de Processing ejecutado una vez por frame.
     * <p>
     * Actualiza el tiempo del ViewModel, aplica el state visual a Processing y construye
     * DTOs de render para el Renderer.
     * </p>
     */
    @Override
    public void draw() {
        update();
        aplicarEstadoVisual();
        dibujarUI();
    }

    /**
     * Envía el tiempo actual al ViewModel para reglas basadas en tiempo.
     */
    private void update() {
        viewModel.actualizarTimer(millis());
    }

    /**
     * Aplica el state visual del ViewModel a Processing. Para esta plantilla de
     * ejemplo serían cursor y rectMode.
     */
    private void aplicarEstadoVisual() {
        if (viewModel.isCursorVisible()) cursor();
        else noCursor();
        rectMode(viewModel.getRectMode());
    }

    /**
     * Construye DTOs de render para el frame actual y delega el dibujo al Renderer.
     */
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
    /**
     * Reenvía el input de mouse wheel al adaptador de input.
     *
     * @param event evento de mouse wheel de Processing
     */
    @Override
    public void mouseWheel(@NotNull MouseEvent event) {
        mouse.mouseWheel(event.getCount());
    }

    /**
     * Reenvía el input de mouse released al adaptador de input.
     */
    @Override
    public void mouseReleased() {
        if (mouseButton == LEFT) mouse.mouseReleasedLeft();
        else if (mouseButton == CENTER) mouse.mouseReleasedCenter();
        else if (mouseButton == RIGHT) mouse.mouseReleasedRight();
        else mouse.mouseReleasedOther();
    }

    /**
     * Reenvía el input de mouse pressed al adaptador de input.
     */
    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) mouse.mousePressedLeft();
        else if (mouseButton == CENTER) mouse.mousePressedCenter();
        else if (mouseButton == RIGHT) mouse.mousePressedRight();
        else mouse.mousePressedOther();
    }

    /**
     * Reenvía el input de mouse dragged al adaptador de input.
     */
    @Override
    public void mouseDragged() {
        if (mouseButton == LEFT) mouse.mouseDraggedLeft();
        else if (mouseButton == CENTER) mouse.mouseDraggedCenter();
        else if (mouseButton == RIGHT) mouse.mouseDraggedRight();
        else mouse.mouseDraggedOther();
    }

    /**
     * Reenvía el input de key release al adaptador de input.
     */
    @Override
    public void keyReleased() {
        teclado.keyReleased(key, keyCode);
    }

    /**
     * Reenvía el input de key pressed al adaptador de input.
     */
    @Override
    public void keyPressed() {
        teclado.keyPressed(key, keyCode);
    }

    /**
     * Callback de resize de la ventana de Processing.
     * <p>
     * No se define comportamiento en esta plantilla.
     * </p>
     */
    @Override
    public void windowResized() {
    }
    // </editor-fold>
}
