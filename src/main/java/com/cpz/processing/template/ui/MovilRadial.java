package com.cpz.processing.template.ui;

import com.cpz.utils.noise.NoiseValue;
import processing.core.PVector;

/**
 * @author CPZ
 */
public class MovilRadial {

    private final PVector pIni, p;
    private float angulo;
    private float radioMax, radioMaxIni, radioMin, radioMinIni, deltaRadioMin, deltaRadioMax, radio;
    private NoiseValue noise;
    private boolean updateRangoRadio;

    public MovilRadial() {
        pIni = new PVector();
        p = new PVector();
    }

    public void update() {
        noise.update();
        if (updateRangoRadio) {
            radioMin += deltaRadioMin;
            radioMax += deltaRadioMax;
        }
        radio = noise.get() * (radioMax - radioMin) + radioMin;
        p.set((float) (pIni.x + radio * Math.cos(angulo)), (float) (pIni.y + radio * Math.sin(angulo)));
    }

    public void setAngulo(float angulo) {
        this.angulo = angulo;
    }

    public void setRadioMin(float radioMin) {
        this.radioMin = radioMin;
    }

    public void setRadioMax(float radioMax) {
        this.radioMax = radioMax;
    }

    public void setNoise(NoiseValue noise) {
        this.noise = noise;
    }

    public void setPosicionInicial(float x, float y) {
        pIni.set(x, y);
    }

    public PVector getPosicion() {
        return p;
    }

    public void setDeltaRadioMin(float deltaRadioMin) {
        this.deltaRadioMin = deltaRadioMin;
    }

    public void setDeltaRadioMax(float deltaRadioMax) {
        this.deltaRadioMax = deltaRadioMax;
    }

    public boolean isUpdateRangoRadio() {
        return updateRangoRadio;
    }

    public void setUpdateRangoRadio(boolean updateRangoRadio) {
        this.updateRangoRadio = updateRangoRadio;
    }

    public void setRadioMaxIni(float radioMaxIni) {
        this.radioMaxIni = radioMaxIni;
    }

    public void setRadioMinIni(float radioMinIni) {
        this.radioMinIni = radioMinIni;
    }

    public boolean isFueraRadioMaxIni() {
        return radio >= radioMaxIni;
    }

    public float getRadio() {
        return radio;
    }

    public void reset() {
        radio = 0;
        radioMax = radioMaxIni;
        radioMin = radioMinIni;
    }
}
