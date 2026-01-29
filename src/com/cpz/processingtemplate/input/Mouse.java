package com.cpz.processingtemplate.input;

import com.cpz.processingtemplate.viewmodel.MainViewModel;
import com.cpz.processingtemplate.interfaces.Input;
import com.cpz.processingtemplate.util.MouseButton;

/**
 * Adaptador de input (paquete {@code input}) que reenvía eventos de mouse al ViewModel.
 * <p>
 * Esta clase no aplica lógica de negocio; solo mapea callbacks de Processing a los valores
 * normalizados de {@link MouseButton} esperados por el ViewModel.
 * </p>
 *
 * @author CPZ
 */
public class Mouse implements Input {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final MainViewModel vm;
    // </editor-fold>

    /**
     * Crea un adaptador de mouse vinculado al ViewModel.
     *
     * @param vm ViewModel que posee las reglas de input
     */
    public Mouse(MainViewModel vm) {
        this.vm = vm;
    }

    // <editor-fold defaultstate="collapsed" desc="*** wheel ***">
    /**
     * Reenvía el movimiento del mouse wheel al ViewModel.
     *
     * @param d delta del wheel
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseWheel(int d, Object... o) {
        vm.onMouseWheel(d, o);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** released ***">
    /**
     * Reenvía la liberación del botón izquierdo al ViewModel.
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseReleasedLeft(Object... o) {
        vm.onMouseReleased(MouseButton.LEFT, o);
    }

    /**
     * Reenvía la liberación del botón central al ViewModel.
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseReleasedCenter(Object... o) {
        vm.onMouseReleased(MouseButton.CENTER, o);
    }

    /**
     * Reenvía la liberación del botón derecho al ViewModel.
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseReleasedRight(Object... o) {
        vm.onMouseReleased(MouseButton.RIGHT, o);
    }

    /**
     * Reenvía la liberación de un botón no estándar al ViewModel.
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseReleasedOther(Object... o) {
        vm.onMouseReleased(MouseButton.OTHER, o);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** pressed ***">
    /**
     * Marcador de posición para reenviar la presión del botón izquierdo.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mousePressedLeft(Object... o) {
        //vm.onMousePressed(MouseButton.LEFT, o);
    }

    /**
     * Marcador de posición para reenviar la presión del botón central.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mousePressedCenter(Object... o) {
        //vm.onMousePressed(MouseButton.CENTER, o);
    }

    /**
     * Marcador de posición para reenviar la presión del botón derecho.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mousePressedRight(Object... o) {
        //vm.onMousePressed(MouseButton.RIGHT, o);
    }

    /**
     * Marcador de posición para reenviar la presión de un botón no estándar.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mousePressedOther(Object... o) {
        //vm.onMousePressed(MouseButton.OTHER, o);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="*** dragged ***">
    /**
     * Marcador de posición para reenviar el arrastre del botón izquierdo.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseDraggedLeft(Object... o) {
        //vm.onMouseDragged(MouseButton.LEFT, o);
    }

    /**
     * Marcador de posición para reenviar el arrastre del botón central.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseDraggedCenter(Object... o) {
        //vm.onMouseDragged(MouseButton.CENTER, o);
    }

    /**
     * Marcador de posición para reenviar el arrastre del botón derecho.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseDraggedRight(Object... o) {
        //vm.onMouseDragged(MouseButton.RIGHT, o);
    }

    /**
     * Marcador de posición para reenviar el arrastre de un botón no estándar.
     * <p>
     * No se realiza delegación en esta plantilla.
     * </p>
     *
     * @param o datos adicionales opcionales desde la View
     */
    public void mouseDraggedOther(Object... o) {
        //vm.onMouseDragged(MouseButton.OTHER, o);
    }
    // </editor-fold>

}
