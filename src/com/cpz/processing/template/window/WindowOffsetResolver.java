package com.cpz.processing.template.window;

import processing.awt.PSurfaceAWT;
import processing.core.PGraphics;
import processing.core.PSurface;

import java.awt.Frame;
import java.awt.Insets;

/**
 * @author CPZ
 */
public final class WindowOffsetResolver {

    private static final WindowOffsets P2D_DEFAULT_OFFSETS = new WindowOffsets(5, 5, 28, 5);

    public static WindowOffsets resolve(PSurface surface, PGraphics graphics) {
        boolean isJava2D = graphics.getClass().getSimpleName().contains("Java2D");
        if (isJava2D) {
            Frame frame = ((PSurfaceAWT.SmoothCanvas) surface.getNative()).getFrame();
            Insets insets = frame.getInsets();
            return new WindowOffsets(insets.left, insets.right, insets.top, insets.bottom);
        }
        return P2D_DEFAULT_OFFSETS;
    }
}
