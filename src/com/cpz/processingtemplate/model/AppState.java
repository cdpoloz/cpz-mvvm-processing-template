package com.cpz.processingtemplate.model;

import java.math.BigDecimal;

/**
 * Pure state for the example application.
 */
public class AppState {

    private boolean demoEnabled;
    private BigDecimal sliderValue = BigDecimal.ZERO;
    private long elapsedMillis;
    private int tickCount;

    public boolean isDemoEnabled() {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public BigDecimal getSliderValue() {
        return sliderValue;
    }

    public void setSliderValue(BigDecimal sliderValue) {
        this.sliderValue = sliderValue;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public void setElapsedMillis(long elapsedMillis) {
        this.elapsedMillis = elapsedMillis;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }
}
