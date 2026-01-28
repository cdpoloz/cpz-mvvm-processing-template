package com.cpz.processingtemplate.util;

import org.jetbrains.annotations.NotNull;

/**
 * @author CPZ
 */
public class Tools {

    public static int construirColor(@NotNull String s) {
        String[] componentes = s.split(":");
        int r = Integer.parseInt(componentes[0]);
        int g = Integer.parseInt(componentes[1]);
        int b = Integer.parseInt(componentes[2]);
        int a = Integer.parseInt(componentes[3]);
        return construirColor(r, g, b, a);
    }

    public static int construirColor(float red, float green, float blue, float alpha) {
        return ((int) alpha << 24) | ((int) red << 16) | ((int) green << 8) | (int) blue;
    }

    public static int construirColor(float gray) {
        return construirColor(gray, gray, gray, 255);
    }

    public static int construirColor(float gray, float alpha) {
        return construirColor(gray, gray, gray, alpha);
    }

}
