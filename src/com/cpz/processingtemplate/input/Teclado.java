package com.cpz.processingtemplate.input;

import com.cpz.processingtemplate.viewmodel.MainViewModel;
import com.cpz.processingtemplate.interfaces.Input;

/**
 * Adaptador de input (paquete {@code input}) que reenvía eventos de teclado al ViewModel.
 * <p>
 * Esta clase no aplica lógica de negocio ni llamadas de Processing; solo normaliza la
 * ruta de input desde la View hacia el ViewModel.
 * </p>
 *
 * @author CPZ
 */
public class Teclado implements Input {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final MainViewModel vm;
    // </editor-fold>

    /**
     * Crea un adaptador de teclado vinculado al ViewModel.
     *
     * @param vm ViewModel que posee las reglas de input
     */
    public Teclado(MainViewModel vm) {
        this.vm = vm;
    }

    /**
     * Reenvía un evento de key released al ViewModel.
     *
     * @param key carácter liberado
     * @param keyCode key code de Processing
     * @param o datos adicionales opcionales desde la View
     */
    public void keyReleased(char key, int keyCode, Object... o) {
        vm.onKeyReleased(key, keyCode, o);
    }

    /**
     * Reenvía un evento de key pressed al ViewModel.
     *
     * @param key carácter presionado
     * @param keyCode key code de Processing
     * @param o datos adicionales opcionales desde la View
     */
    public void keyPressed(char key, int keyCode, Object... o) {
        vm.onKeyPressed(key, keyCode, o);
    }

}
