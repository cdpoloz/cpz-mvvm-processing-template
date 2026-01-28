package com.cpz.processingtemplate.util.time;

import lombok.Getter;

/**
 * Temporizador de intervalos manejado por entrada de tiempo externa
 * Diseñado para lazos de render/simulación en donde el invocante proporciona el valor tiempoActual
 * Sin hilos, sleeps o dependencias de frameworks
 *
 * @author CPZ
 *
 */
public class IntervalTimer {

    // <editor-fold defaultstate="collapsed" desc="*** variables ***">
    private final int periodo;
    private @Getter boolean running;
    private long ultimoTiempoDeInicio;
    // </editor-fold>

    public IntervalTimer(int periodo) {
        this.periodo = periodo;
        this.running = false;
        this.ultimoTiempoDeInicio = 0L;
    }

    public void start(long tiempoActual) {
        this.running = true;
        this.ultimoTiempoDeInicio = tiempoActual;
    }

    public void stop() {
        this.running = false;
    }

    /**
     * Actualiza el timer y devuelve true cuando ocurre un pulso.
     *
     * @param tiempoActual tiempo actual en milisegundos, proporcionado por el invocante
     * @return true si ya pasó un intervalo y si fue emitido un pulso
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
