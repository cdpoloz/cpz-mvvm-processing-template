package com.cpz.processing.template.examples.noise;

import com.cpz.processing.template.input.noise.ProcessingNoiseSource;
import com.cpz.utils.noise.NoiseVector3;
import com.cpz.utils.noise.Vector3f;
import processing.core.PApplet;

/**
 * Minimal Processing sketch that uses {@link NoiseVector3} through
 * {@link ProcessingNoiseSource}.
 * <p>
 * {@code NoiseVector3} composes three {@code NoiseValue} instances. This sketch
 * maps x and y to the ellipse position, and z to its diameter.
 * </p>
 *
 * @author CPZ
 */
public class NoiseVector3Sketch extends PApplet {

    private NoiseVector3 noiseVector;

    public void settings() {
        size(800, 500, P2D);
        smooth(8);
    }

    public void setup() {
        frameRate(60);
        getSurface().setTitle("NoiseVector3Sketch");

        ProcessingNoiseSource source = new ProcessingNoiseSource(this);
        noiseVector = new NoiseVector3(source);

        // Different starting positions avoid visible correlation between axes.
        noiseVector.reset(random(1000), random(1000), random(1000));

        // Each axis can advance at its own speed.
        noiseVector.setSpeed(0.005f, 0.007f, 0.01f);

        noStroke();
        fill(255);
    }

    public void draw() {
        background(28);

        // update() advances the internal noise positions.
        noiseVector.update();

        // get() reads the current value without mutating state.
        Vector3f v = noiseVector.get();

        // x and y drive screen position; z drives size in this example.
        float x = map(v.x(), 0, 1, 50, width - 50);
        float y = map(v.y(), 0, 1, 50, height - 50);
        float diameter = map(v.z(), 0, 1, 10, 150);

        ellipse(x, y, diameter, diameter);

        fill(255);
        text("x: " + nf(v.x(), 1, 3), 10, 20);
        text("y: " + nf(v.y(), 1, 3), 10, 35);
        text("z: " + nf(v.z(), 1, 3), 10, 50);
    }

}
