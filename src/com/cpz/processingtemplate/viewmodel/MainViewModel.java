package com.cpz.processingtemplate.viewmodel;

import com.cpz.processingtemplate.model.AppState;
import com.cpz.processingtemplate.util.time.IntervalTimer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Logic for the example application that consumes controls.
 */
public class MainViewModel {

    private final AppState appState;
    private IntervalTimer timer;

    public MainViewModel(AppState appState) {
        this.appState = appState;
    }

    public void initialize(int timerInterval) {
        appState.setDemoEnabled(true);
        appState.setSliderValue(new BigDecimal("50"));
        appState.setElapsedMillis(0L);
        appState.setTickCount(0);
        timer = new IntervalTimer(timerInterval);
        timer.start(0L);
    }

    public void update(long nowMillis) {
        appState.setElapsedMillis(Math.max(0L, nowMillis));
        if (timer != null && timer.isIntervalComplete(nowMillis)) {
            appState.setTickCount(appState.getTickCount() + 1);
        }
    }

    public void toggleDemoEnabled() {
        appState.setDemoEnabled(!appState.isDemoEnabled());
    }

    public void setSliderValue(BigDecimal sliderValue) {
        if (sliderValue != null) {
            appState.setSliderValue(sliderValue);
        }
    }

    public boolean isDemoEnabled() {
        return appState.isDemoEnabled();
    }

    public BigDecimal getSliderValue() {
        return appState.getSliderValue();
    }

    public String getStatusText() {
        return "Feature enabled: " + (appState.isDemoEnabled() ? "yes" : "no")
                + "\nValue: " + formatSliderValue(appState.getSliderValue())
                + "\nElapsed: " + formatElapsed(appState.getElapsedMillis())
                + "\nTicks: " + appState.getTickCount();
    }

    private String formatSliderValue(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        BigDecimal normalized = value.stripTrailingZeros();
        if (normalized.scale() < 0) {
            normalized = normalized.setScale(0, RoundingMode.HALF_UP);
        }
        return normalized.toPlainString();
    }

    private String formatElapsed(long elapsedMillis) {
        BigDecimal seconds = BigDecimal.valueOf(elapsedMillis)
                .divide(BigDecimal.valueOf(1000), 1, RoundingMode.HALF_UP);
        return seconds.toPlainString() + "s";
    }
}
