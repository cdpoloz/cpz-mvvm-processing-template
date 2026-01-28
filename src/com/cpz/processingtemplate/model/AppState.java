package com.cpz.processingtemplate.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CPZ
 */
public class AppState {

    /**
     * Estado observable de la aplicacion (Model en MVVM).
     * Contiene datos puros sin dependencias de Processing.
     */
    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private @Setter @Getter int colorFondo;
    private @Setter @Getter int colorRectangulo;
    private @Setter @Getter int colorCirculo;
    private @Setter @Getter int rectMode;
    private @Setter @Getter boolean cursorVisible;
    // </editor-fold>

}
