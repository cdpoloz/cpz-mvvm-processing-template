package com.cpz.processing.template.input.noise;

import com.cpz.utils.noise.NoiseSource;
import processing.core.PApplet;

/**
 * @author CPZ
 */
public class ProcessingNoiseSource implements NoiseSource {

    private final PApplet sketch;

    public ProcessingNoiseSource(PApplet sketch) {
        this.sketch = sketch;
    }

    @Override
    public float noise(float x) {
        return sketch.noise(x);
    }
}