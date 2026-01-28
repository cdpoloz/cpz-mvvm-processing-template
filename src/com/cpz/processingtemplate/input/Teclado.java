package com.cpz.processingtemplate.input;

import com.cpz.processingtemplate.viewmodel.MainViewModel;
import com.cpz.processingtemplate.interfaces.Input;

/**
 * @author CPZ
 */
public class Teclado implements Input {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final MainViewModel vm;
    // </editor-fold>

    public Teclado(MainViewModel vm) {
        this.vm = vm;
    }

    public void keyReleased(char key, int keyCode, Object... o) {
        vm.onKeyReleased(key, keyCode, o);
    }

    public void keyPressed(char key, int keyCode, Object... o) {
        vm.onKeyPressed(key, keyCode, o);
    }

}
