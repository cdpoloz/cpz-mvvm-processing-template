package com.cpz.processingtemplate.util.time;

/**
 * Interval timer driven by external time values.
 */
public class IntervalTimer {

    private final int interval;
    private boolean running;
    private long lastStartTime;

    public IntervalTimer(int interval) {
        this.interval = interval;
        this.running = false;
        this.lastStartTime = 0L;
    }

    public void start(long currentTime) {
        this.running = true;
        this.lastStartTime = currentTime;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isIntervalComplete(long currentTime) {
        if (!running) {
            return false;
        }
        if (interval <= 0) {
            return false;
        }
        if (currentTime - lastStartTime >= interval) {
            lastStartTime = currentTime;
            return true;
        }
        return false;
    }
}
