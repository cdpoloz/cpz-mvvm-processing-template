package com.cpz.processing.template.examples.noise;

import com.cpz.processing.template.input.noise.ProcessingNoiseSource;
import com.cpz.utils.noise.NoiseValue;
import processing.core.PApplet;

/**
 * Minimal Processing sketch that demonstrates one-dimensional noise with
 * {@link NoiseValue}.
 * <p>
 * {@code NoiseValue} does not depend on Processing. {@link ProcessingNoiseSource}
 * bridges Processing's {@code noise(float)} function to the cpz-utils
 * {@code NoiseSource} interface.
 * </p>
 *
 * @author CPZ
 */
public class NoiseValueSketch extends PApplet {

    private NoiseValue noiseValue;
    private float currentValue;
    private float currentY;

    public void settings() {
        size(800, 500, P2D);
        smooth(8);
    }

    public void setup() {
        frameRate(60);
        getSurface().setTitle("NoiseValueSketch");

        ProcessingNoiseSource source = new ProcessingNoiseSource(this);
        noiseValue = new NoiseValue(source);

        // One random starting position keeps each run from looking identical.
        noiseValue.reset(random(1000));

        // Small speed values make the one-dimensional motion smooth.
        noiseValue.setSpeed(0.006f);
    }

    public void draw() {
        background(28);

        // This example advances a single noise dimension each frame.
        noiseValue.update();

        // get() reads the current value without advancing it again.
        currentValue = noiseValue.get();
        currentY = map(currentValue, 0, 1, 70, height - 70);

        stroke(120, 190, 255);
        strokeWeight(3);
        line(60, currentY, width - 60, currentY);

        noStroke();
        fill(255);
        ellipse(width * 0.5f, currentY, 16, 16);

        text("noise: " + nf(currentValue, 1, 3), 10, 20);
        text("y: " + nf(currentY, 1, 1), 10, 35);
    }
}
