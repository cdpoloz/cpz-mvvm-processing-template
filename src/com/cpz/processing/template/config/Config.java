package com.cpz.processing.template.config;

import com.cpz.processing.template.window.DisplayDetector;
import com.cpz.processing.template.window.DisplayInfo;
import processing.core.PApplet;
import processing.opengl.PJOGL;

import java.io.File;

import static com.cpz.processing.template.main.Launcher.LOG;
import static com.cpz.processing.template.main.Launcher.PROPS;
import static processing.core.PConstants.P2D;

/**
 * @author CPZ
 */
public class Config {

    public static void settings(PApplet sk) {
        LOG.info("Starting settings");
        // window icon
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + PROPS.getProperty("window.icon"));
        // screen size
        DisplayInfo screen = DisplayDetector.resolveTargetDisplay(true);
        int screenWidth = screen.width();
        int screenHeight = screen.height();
        // screen scale
        float screenScaleFactor = Float.parseFloat(PROPS.getProperty("screen.scale.factor"));
        // window size
        sk.size((int) (screenWidth * screenScaleFactor), (int) (screenHeight * screenScaleFactor), P2D);
        // antialiasing
        sk.smooth(Integer.parseInt(PROPS.getProperty("smoothing")));
        LOG.info("Finished settings");
    }

    public static void setup(PApplet sk) {
        LOG.info("Starting initial setup");
        // frames per second
        sk.frameRate(Integer.parseInt(PROPS.getProperty("fps")));
        // window title
        sk.getSurface().setTitle(PROPS.getProperty("window.title"));
        LOG.info("Finished initial setup");
    }

}
