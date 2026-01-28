package com.cpz.processingtemplate.viewmodel;

import com.cpz.processingtemplate.model.AppState;
import com.cpz.processingtemplate.util.MouseButton;
import com.cpz.processingtemplate.util.time.IntervalTimer;

import static com.cpz.processingtemplate.util.Constantes.*;

/**
 * @author CPZ
 */
public class MainViewModel {

    /**
     * ViewModel de la aplicacion.
     * Sera invocado por la capa de input (Mouse/Teclado) en el futuro.
     */
    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final AppState appState;
    private IntervalTimer timer;
    private long ultimoTiempoDeInicio;
    // </editor-fold>

    public MainViewModel(AppState appState) {
        this.appState = appState;
    }

    // <editor-fold defaultstate="collapsed" desc="*** setup ***">
    public void inicializar(int periodoTimer) {
        appState.setColorFondo(NEGRO);
        appState.setCursorVisible(true);
        appState.setRectMode(RECT_MODE_CORNER);
        appState.setColorRectangulo(VERDE);
        appState.setColorCirculo(AMARILLO);
        this.timer = new IntervalTimer(periodoTimer);
        this.timer.stop();
        this.ultimoTiempoDeInicio = 0L;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** mouse ***">
    public void onMouseReleased(MouseButton mouseButton, Object... o) {
        if (mouseButton == MouseButton.LEFT) {
            if (appState.getColorRectangulo() == VERDE) appState.setColorRectangulo(MAGENTA);
            else appState.setColorRectangulo(VERDE);
        } else if (mouseButton == MouseButton.CENTER) {
            appState.setCursorVisible(!appState.isCursorVisible());
        } else if (mouseButton == MouseButton.RIGHT) {
            if (appState.getRectMode() == RECT_MODE_CORNER) appState.setRectMode(RECT_MODE_CENTER);
            else appState.setRectMode(RECT_MODE_CORNER);
        } else if (mouseButton == MouseButton.OTHER) {
            System.out.println("MouseButton.OTHER");
        }
    }

    public void onMousePressed(int mouseButton, Object... o) {
    }

    public void onMouseDragged(int mouseButton, Object... o) {
    }

    public void onMouseWheel(int d, Object... o) {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** teclado ***">
    public void onKeyReleased(int key, int keyCode, Object... o) {
        if (keyCode == BARRA_ESPACIADORA && !timer.isRunning()) conmutarColorFondo();
        if (key == 't') {
            if (timer.isRunning()) timer.stop();
            else timer.start(ultimoTiempoDeInicio);
        }
    }

    public void onKeyPressed(char key, int keyCode, Object... o) {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** update ***">
    public void actualizarTimer(long nowMillis) {
        if (timer.isFinPeriodo(nowMillis)) conmutarColorFondo();
        ultimoTiempoDeInicio = nowMillis;
    }

    private void conmutarColorFondo() {
        if (appState.getColorFondo() == NEGRO) appState.setColorFondo(GRIS_MEDIO);
        else appState.setColorFondo(NEGRO);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** getters ***">
    public int getColorFondo() {
        return appState.getColorFondo();
    }

    public boolean isCursorVisible() {
        return appState.isCursorVisible();
    }

    public int getColorRectangulo() {
        return appState.getColorRectangulo();
    }

    public int getColorCirculo() {
        return appState.getColorCirculo();
    }

    public int getRectMode() {
        return appState.getRectMode();
    }

    public boolean isTimerRunning() {
        return timer != null && timer.isRunning();
    }
    // </editor-fold>
}
