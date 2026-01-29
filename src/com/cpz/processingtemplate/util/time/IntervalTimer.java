package com.cpz.processingtemplate.util.time;

import lombok.Getter;

/**
 * Timer de intervalos sin state (paquete {@code util.time}) impulsado por input de tiempo externo.
 * <p>
 * Diseñado para bucles de render donde el invocante proporciona el tiempo actual. No usa hilos,
 * sleeps ni dependencias de frameworks.
 * </p>
 *
 * @author CPZ
 */
public class IntervalTimer {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final int periodo;
    private @Getter boolean running;
    private long ultimoTiempoDeInicio;
    // </editor-fold>

    /**
     * Crea un timer con el periodo indicado.
     *
     * @param periodo intervalo en milisegundos
     */
    public IntervalTimer(int periodo) {
        this.periodo = periodo;
        this.running = false;
        this.ultimoTiempoDeInicio = 0L;
    }

    /**
     * Inicia el timer desde el valor de tiempo indicado.
     *
     * @param tiempoActual tiempo actual en milisegundos
     */
    public void start(long tiempoActual) {
        this.running = true;
        this.ultimoTiempoDeInicio = tiempoActual;
    }

    /**
     * Detiene el timer; los pulsos posteriores quedan deshabilitados hasta reiniciarlo.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Actualiza el timer y devuelve {@code true} cuando transcurre un periodo.
     *
     * @param tiempoActual tiempo actual en milisegundos proporcionado por el invocante
     * @return {@code true} si transcurrió el periodo y se emitió un pulso
     */
    public boolean isFinPeriodo(long tiempoActual) {
        if (!running) return false;
        if (periodo <= 0) return false;
        if (tiempoActual - ultimoTiempoDeInicio >= periodo) {
            ultimoTiempoDeInicio = tiempoActual;
            return true;
        }
        return false;
    }
}
