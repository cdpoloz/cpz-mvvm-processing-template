package com.cpz.processing.template.window;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CPZ
 */
public final class DisplayDetector {

    public static DisplayInfo resolveTargetDisplay(boolean usePrimary) {
        List<DisplayInfo> displays = detectDisplays();
        if (displays.isEmpty()) throw new IllegalStateException("No displays detected");
        if (displays.size() == 1) return displays.getFirst();
        if (usePrimary) return displays.stream().filter(DisplayInfo::primary).findFirst().orElse(displays.getFirst());
        return displays.stream().filter(display -> !display.primary()).findFirst().orElse(displays.getFirst());
    }

    public static List<DisplayInfo> detectDisplays() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = environment.getScreenDevices();
        GraphicsDevice primaryDevice = environment.getDefaultScreenDevice();
        List<DisplayInfo> displays = new ArrayList<>();
        for (int i = 0; i < devices.length; i++) {
            GraphicsDevice device = devices[i];
            GraphicsConfiguration configuration = device.getDefaultConfiguration();
            Rectangle bounds = configuration.getBounds();
            displays.add(new DisplayInfo(
                    i,
                    device.equals(primaryDevice),
                    bounds.x,
                    bounds.y,
                    bounds.width,
                    bounds.height
            ));
        }
        return displays;
    }
}