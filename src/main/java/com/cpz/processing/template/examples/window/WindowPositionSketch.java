package com.cpz.processing.template.examples.window;

import com.cpz.processing.template.window.DisplayDetector;
import com.cpz.processing.template.window.DisplayInfo;
import com.cpz.processing.template.window.WindowOffsetResolver;
import com.cpz.processing.template.window.WindowOffsets;
import com.cpz.processing.template.window.WindowPosition;
import com.cpz.processing.template.window.WindowPositionResolver;
import processing.core.PApplet;

import static com.cpz.processing.template.main.Launcher.LOG;
import static com.cpz.processing.template.main.Launcher.PROPS;

/**
 * @author CPZ
 */
public class WindowPositionSketch extends PApplet {

    private DisplayInfo target;
    private float circleX;
    private float circleY;

    public void settings() {
        LOG.info("Starting settings");
        // target monitor
        boolean usePrimary = Boolean.parseBoolean(PROPS.getProperty("window.display.primary"));
        target = DisplayDetector.resolveTargetDisplay(usePrimary);
        // window size
        float screenScaleFactor = Float.parseFloat(PROPS.getProperty("screen.scale.factor"));
        int sketchWidth = (int) (target.width() * screenScaleFactor);
        int sketchHeight = (int) (target.height() * screenScaleFactor);
        size(sketchWidth, sketchHeight, P2D);
        // antialiasing
        smooth(Integer.parseInt(PROPS.getProperty("smoothing")));
        LOG.info("Finishing settings");
    }

    public void setup() {
        LOG.info("Starting final setup");
        // frames per second
        frameRate(Integer.parseInt(PROPS.getProperty("fps")));
        // window title
        surface.setTitle("WindowPositionSketch");
        // window position
        WindowOffsets offsets = WindowOffsetResolver.resolve(surface, g);
        WindowPosition position = WindowPositionResolver.resolve(
                target,
                Float.parseFloat(PROPS.getProperty("window.position.x")),
                Float.parseFloat(PROPS.getProperty("window.position.y")),
                width,
                height,
                offsets
        );
        surface.setLocation(position.x(), position.y());
        // sketch variables
        circleX = width * 0.5f;
        circleY = height * 0.5f;
        textAlign(CENTER, CENTER);
        LOG.info("Finishing final setup");
    }

    public void draw() {
        background(24);

        fill(230);
        textSize(14);
        text("Click the mouse to change the position of the circle", width * 0.5f, height * 0.15f);

        fill(255, 180, 80);
        ellipse(circleX, circleY, 80, 80);
    }

    public void mouseReleased() {
        circleX = mouseX;
        circleY = mouseY;
    }

}
