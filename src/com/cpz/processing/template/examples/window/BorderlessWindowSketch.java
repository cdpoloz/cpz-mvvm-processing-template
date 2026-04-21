package com.cpz.processing.template.examples.window;

import com.cpz.processing.infrastructure.window.ProcessingWindowConfigurator;
import processing.core.PApplet;
import processing.core.PSurface;

import static com.cpz.processing.template.main.Launcher.PROPS;

/**
 * @author CPZ
 */
public class BorderlessWindowSketch extends PApplet {

    private float circleX;
    private float circleY;

    public PSurface initSurface() {
        return ProcessingWindowConfigurator.setUndecorated(super.initSurface(), Boolean.parseBoolean(PROPS.getProperty("windowUndecorated")));
    }

    public void settings() {
        size(800, 500);
        smooth(8);
    }

    public void setup() {
        frameRate(60);
        getSurface().setTitle("BorderlessWindowSketch");
        circleX = width * 0.5f;
        circleY = height * 0.5f;
        textAlign(CENTER, CENTER);
    }

    public void draw() {
        background(24);

        fill(70, 140, 220);
        noStroke();
        rect(0, 0, width, 42);

        fill(255);
        textSize(18);
        text("BorderlessWindowSketch", width * 0.5f, 21);

        fill(230);
        textSize(14);
        text("Drag the mouse to move the circle", width * 0.5f, 80);
        text("Press ESC to quit", width * 0.5f, 105);

        fill(255, 180, 80);
        ellipse(circleX, circleY, 80, 80);
    }

    public void mouseDragged() {
        circleX = mouseX;
        circleY = mouseY;
    }

    public void keyPressed() {
        if (key == ESC) {
            key = 0;
            exit();
        }
    }
}