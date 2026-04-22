package com.cpz.processing.template.window;

/**
 * @author CPZ
 */
public final class WindowPositionResolver {

    public static WindowPosition resolve(
            DisplayInfo targetDisplay,
            float requestedX,
            float requestedY,
            int sketchWidth,
            int sketchHeight,
            WindowOffsets offsets
    ) {
        if (requestedX < 0 || requestedY < 0) throw new IllegalStateException("Invalid window position");
        int relativeX = toRelativeCoordinate(requestedX, targetDisplay.width());
        int relativeY = toRelativeCoordinate(requestedY, targetDisplay.height());
        int absoluteX = targetDisplay.x() + relativeX;
        int absoluteY = targetDisplay.y() + relativeY;
        int outerWidth = sketchWidth + offsets.totalHorizontal();
        int outerHeight = sketchHeight + offsets.totalVertical();
        int minX = targetDisplay.x();
        int minY = targetDisplay.y();
        int maxX = targetDisplay.x() + targetDisplay.width() - outerWidth;
        int maxY = targetDisplay.y() + targetDisplay.height() - outerHeight;
        if (maxX < minX) maxX = minX;
        if (maxY < minY) maxY = minY;
        absoluteX = Math.clamp(absoluteX, minX, maxX);
        absoluteY = Math.clamp(absoluteY, minY, maxY);
        return new WindowPosition(absoluteX, absoluteY);
    }

    private static int toRelativeCoordinate(float value, int displaySize) {
        if (value < 1f) return (int) (value * displaySize);
        return (int) value;
    }
}
