package com.cpz.processingtemplate.viewmodel;

import com.cpz.processingtemplate.model.AppState;
import com.cpz.processingtemplate.util.MouseButton;
import com.cpz.processingtemplate.util.time.IntervalTimer;

import static com.cpz.processingtemplate.util.Constantes.*;

/**
 * Componente de la capa ViewModel (paquete {@code viewmodel}) que contiene las reglas de la
 * aplicación y las transiciones de state sobre el Model ({@link AppState}).
 * <p>
 * Es la única fuente de verdad para las decisiones, actúa como intermediario entre View y
 * Model, consume input normalizado desde los adaptadores de input y valores de tiempo
 * proporcionados por la View, y nunca usa APIs de Processing.
 * </p>
 * <p>
 * Esta clase no renderiza, no orquesta el lifecycle de Processing y no construye DTOs de
 * render; en su lugar, la View consulta su state de solo lectura para construir DTOs por frame.
 * </p>
 *
 * @author CPZ
 */
public class MainViewModel {

    /**
     * Referencias a state y timer gestionadas por el ViewModel para coordinar reglas de la aplicación.
     */
    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final AppState appState;
    private IntervalTimer timer;
    private long ultimoTiempoDeInicio;
    // </editor-fold>

    /**
     * Crea el ViewModel vinculado a la instancia de Model indicada.
     *
     * @param appState contenedor de state del Model gestionado por este ViewModel
     */
    public MainViewModel(AppState appState) {
        this.appState = appState;
    }

    // <editor-fold defaultstate="collapsed" desc="*** setup ***">
    /**
     * Inicializa el state del Model y la configuración interna del timer.
     * <p>
     * Es invocado por la View durante el bootstrap para establecer colores por defecto,
     * visibilidad del cursor, rect mode y el periodo del timer.
     * </p>
     *
     * @param periodoTimer periodo del timer en milisegundos usado por {@link IntervalTimer}
     */
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
    /**
     * Gestiona un evento de liberación del mouse enrutado por los adaptadores de input.
     * <p>
     * Es invocado desde la capa de input de la View. Actualiza el state del Model según el
     * {@link MouseButton} normalizado, sin dependencias de Processing.
     * </p>
     *
     * @param mouseButton identificador normalizado del botón del mouse
     * @param o datos adicionales opcionales del adaptador de input (ignorado en esta plantilla)
     */
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

    /**
     * Marcador de posición para la gestión de mouse pressed en el ViewModel.
     * <p>
     * Es invocado por adaptadores de input cuando se conecten; actualmente no aplica lógica de negocio.
     * </p>
     *
     * @param mouseButton código de botón del mouse de Processing
     * @param o datos adicionales opcionales del adaptador de input
     */
    public void onMousePressed(int mouseButton, Object... o) {
    }

    /**
     * Marcador de posición para la gestión de mouse dragged en el ViewModel.
     * <p>
     * Es invocado por adaptadores de input cuando se conecten; actualmente no aplica lógica de negocio.
     * </p>
     *
     * @param mouseButton código de botón del mouse de Processing
     * @param o datos adicionales opcionales del adaptador de input
     */
    public void onMouseDragged(int mouseButton, Object... o) {
    }

    /**
     * Marcador de posición para la gestión de mouse wheel en el ViewModel.
     * <p>
     * Es invocado por adaptadores de input cuando se conecten; actualmente no aplica lógica de negocio.
     * </p>
     *
     * @param d delta del wheel proporcionado por el callback de input de la View
     * @param o datos adicionales opcionales del adaptador de input
     */
    public void onMouseWheel(int d, Object... o) {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** teclado ***">
    /**
     * Gestiona eventos de key release enrutados por los adaptadores de input.
     * <p>
     * Es invocado desde la capa de input de la View. Puede conmutar el color de fondo o
     * controlar el timer interno según la tecla liberada.
     * </p>
     *
     * @param key carácter liberado
     * @param keyCode key code de Processing liberado
     * @param o datos adicionales opcionales del adaptador de input (ignorado en esta plantilla)
     */
    public void onKeyReleased(int key, int keyCode, Object... o) {
        if (keyCode == BARRA_ESPACIADORA && !timer.isRunning()) conmutarColorFondo();
        if (key == 't') {
            if (timer.isRunning()) timer.stop();
            else timer.start(ultimoTiempoDeInicio);
        }
    }

    /**
     * Marcador de posición para la gestión de key pressed en el ViewModel.
     * <p>
     * Es invocado por adaptadores de input cuando se conecten; actualmente no aplica lógica de negocio.
     * </p>
     *
     * @param key carácter presionado
     * @param keyCode key code de Processing presionado
     * @param o datos adicionales opcionales del adaptador de input
     */
    public void onKeyPressed(char key, int keyCode, Object... o) {
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** update ***">
    /**
     * Avanza el timer interno usando un valor de tiempo suministrado por la View.
     * <p>
     * Se llama una vez por frame desde el bucle de Processing; puede conmutar el color de fondo
     * cuando {@link IntervalTimer} completa un periodo.
     * </p>
     *
     * @param nowMillis tiempo actual en milisegundos proporcionado por la View
     */
    public void actualizarTimer(long nowMillis) {
        if (timer.isFinPeriodo(nowMillis)) conmutarColorFondo();
        ultimoTiempoDeInicio = nowMillis;
    }

    /**
     * Conmuta el color de fondo almacenado en el Model.
     * <p>
     * Efecto secundario: actualiza {@link AppState} con el siguiente color de fondo.
     * </p>
     */
    private void conmutarColorFondo() {
        if (appState.getColorFondo() == NEGRO) appState.setColorFondo(GRIS_MEDIO);
        else appState.setColorFondo(NEGRO);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** getters ***">
    /**
     * Expone el color de fondo actual para el render de la View.
     *
     * @return color de fondo del Model
     */
    public int getColorFondo() {
        return appState.getColorFondo();
    }

    /**
     * Indica si el cursor debe ser visible en la View.
     *
     * @return {@code true} si el cursor debe ser visible
     */
    public boolean isCursorVisible() {
        return appState.isCursorVisible();
    }

    /**
     * Expone el color del rectángulo para construir DTOs de render.
     *
     * @return color del rectángulo del Model
     */
    public int getColorRectangulo() {
        return appState.getColorRectangulo();
    }

    /**
     * Expone el color del círculo para construir DTOs de render.
     *
     * @return color del círculo del Model
     */
    public int getColorCirculo() {
        return appState.getColorCirculo();
    }

    /**
     * Expone el rect mode usado por la View para la configuración de Processing.
     *
     * @return rect mode del Model
     */
    public int getRectMode() {
        return appState.getRectMode();
    }

    /**
     * Indica si el timer interno está en ejecución.
     *
     * @return {@code true} si el timer existe y está en ejecución
     */
    public boolean isTimerRunning() {
        return timer != null && timer.isRunning();
    }
    // </editor-fold>
}
