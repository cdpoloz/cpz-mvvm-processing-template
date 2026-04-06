package com.cpz.processingtemplate.util.time;

/**
 * Timer de intervalos impulsado por tiempo externo.
 */
public class IntervalTimer {

    private final int periodo;
    private boolean running;
    private long ultimoTiempoDeInicio;

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

    public boolean isRunning() {
        return running;
    }

    public boolean isFinPeriodo(long tiempoActual) {
        if (!running) {
            return false;
        }
        if (periodo <= 0) {
            return false;
        }
        if (tiempoActual - ultimoTiempoDeInicio >= periodo) {
            ultimoTiempoDeInicio = tiempoActual;
            return true;
        }
        return false;
    }
}
