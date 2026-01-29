package com.cpz.processingtemplate.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Componente de la capa Model (paquete {@code model}) que almacena el state puro de la aplicación.
 * <p>
 * Esta clase no contiene lógica de negocio y no tiene dependencias de Processing. Todas las
 * mutaciones de state están a cargo del ViewModel.
 * </p>
 *
 * @author CPZ
 */
public class AppState {

    /**
     * Campos de state observable gestionados por el ViewModel.
     */
    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private @Setter @Getter int colorFondo;
    private @Setter @Getter int colorRectangulo;
    private @Setter @Getter int colorCirculo;
    private @Setter @Getter int rectMode;
    private @Setter @Getter boolean cursorVisible;
    // </editor-fold>

}
