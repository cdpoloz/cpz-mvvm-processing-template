package com.cpz.processing.template.window;

import processing.awt.PSurfaceAWT;
import processing.core.PSurface;

import java.awt.Frame;

/**
 * @author CPZ
 */
public class ProcessingWindowConfigurator {

    public static void setUndecorated(PSurface surface, boolean undecorated) {
        Frame frame = getFrame(surface);
        frame.setUndecorated(undecorated);
    }

    public static PSurface setAlwaysOnTop(PSurface surface, boolean alwaysOnTop) {
        Frame frame = getFrame(surface);
        frame.setAlwaysOnTop(alwaysOnTop);
        return surface;
    }

    public static Frame getFrame(PSurface surface) {
        if (!(surface instanceof PSurfaceAWT awtSurface)) throw new IllegalStateException("Only AWT surfaces are supported");
        PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) awtSurface.getNative();
        return canvas.getFrame();
    }

}
