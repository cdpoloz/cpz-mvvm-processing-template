package com.cpz.processing.template.config;

import com.cpz.processing.template.main.Sketch;
import processing.opengl.PJOGL;

import java.awt.*;
import java.io.File;

import static com.cpz.processing.template.main.Launcher.LOG;
import static com.cpz.processing.template.main.Launcher.PROPS;
import static processing.core.PConstants.P2D;

/**
 * @author CPZ
 */
public class Config {

    public static void settings(Sketch sk) {
        LOG.info("Starting settings");
        // window icon
        PJOGL.setIcon("data" + File.separator + "img" + File.separator + PROPS.getProperty("windowIcon"));
        // screen size
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = ge.getDefaultScreenDevice();
        int screenWidth = screen.getDisplayMode().getWidth();
        int screenHeight = screen.getDisplayMode().getHeight();
        // screen scale
        float horizontalScreenScaleFactor = Float.parseFloat(PROPS.getProperty("horizontalScreenScaleFactor"));
        float verticalScreenScaleFactor = Float.parseFloat(PROPS.getProperty("verticalScreenScaleFactor"));
        // window size
        sk.size((int) (screenWidth * horizontalScreenScaleFactor), (int) (screenHeight * verticalScreenScaleFactor), P2D);
        // antialiasing
        sk.smooth(Integer.parseInt(PROPS.getProperty("smoothing")));
        LOG.info("Finished settings");
    }

    public static void setup(Sketch sk) {
        LOG.info("Starting initial setup");
        // frames per second
        sk.frameRate(Integer.parseInt(PROPS.getProperty("fps")));
        // window title
        sk.getSurface().setTitle(PROPS.getProperty("windowTitle"));
        LOG.info("Finished initial setup");
    }

}
