package com.cpz.processing.infrastructure.window;

import processing.awt.PSurfaceAWT;
import processing.core.PSurface;

import java.awt.Frame;

/**
 * @author CPZ
 */
public class ProcessingWindowConfigurator {

    public static PSurface setUndecorated(PSurface surface, boolean undecorated) {
        Frame frame = getFrame(surface);
        frame.setUndecorated(undecorated);
        frame.setAlwaysOnTop(true);
        return surface;
    }

    public static Frame getFrame(PSurface surface) {
        if (!(surface instanceof PSurfaceAWT awtSurface)) throw new IllegalStateException("Only AWT surfaces are supported");
        PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) awtSurface.getNative();
        return canvas.getFrame();
    }

}
