# Window Position Example

## Overview

`WindowPositionSketch` shows how to place a Processing window on a selected
monitor using configuration values from `data/config.properties`. This is not
handled directly by Processing, so the example provides a reusable approach
for multi-monitor setups.

The example covers the common multi-monitor case: detect available displays,
choose the primary or a secondary display, calculate a window size relative to
that display, resolve a requested position, and keep the final window bounds
inside the target monitor.

---

## What This Example Shows

- monitor detection
- primary or secondary display selection
- relative vs absolute window positioning
- window clamping inside the selected display
- renderer-dependent window decoration offsets

---

## Project Structure

Only the classes involved in this example are shown here:

```text
com.cpz.processing.template.examples.window
  WindowPositionSketch.java

com.cpz.processing.template.window
  DisplayInfo.java
  DisplayDetector.java
  WindowOffsets.java
  WindowOffsetResolver.java
  WindowPosition.java
  WindowPositionResolver.java
```

`WindowPositionSketch` orchestrates the flow. The reusable display and window
logic lives in `com.cpz.processing.template.window`.

---

## Execution Flow

### settings()

The sketch reads `window.display.primary` and asks `DisplayDetector` to resolve
the target display.

It then reads the horizontal and vertical scale factors and calculates the
Processing sketch size from the selected monitor dimensions.

### setup()

The sketch reads the requested window position from properties, uses
`WindowOffsetResolver` to determine window decoration offsets, and then uses
`WindowPositionResolver` to compute the final clamped position before applying
it to the Processing surface.

```java
surface.setLocation(position.x(), position.y());
```

The sketch does not contain the positioning rules. It only wires the reusable
window helpers together.

---

## Display Detection

`DisplayDetector` reads the available monitors from the local graphics
environment and converts each display into a `DisplayInfo`.

`DisplayInfo` stores the display index, whether it is the primary display, and
the display bounds:

- x
- y
- width
- height

The target monitor is selected with:

```properties
window.display.primary=true
```

When the value is `true`, the primary monitor is used. When the value is
`false`, the first non-primary monitor is used when available. If only one
monitor exists, that monitor is used.

---

## Window Size

The window size is calculated from the selected monitor dimensions:

```properties
horizontal.screen.scale.factor=0.8
vertical.screen.scale.factor=0.8
```

For example, with a `1920x1080` target monitor and both factors set to `0.8`,
the sketch size becomes `1536x864`.

This keeps sizing relative to the target display, not necessarily the operating
system primary display.

---

## Window Position

The requested position is configured with:

```properties
window.position.x=0.5
window.position.y=0.5
```

`WindowPositionResolver` interprets each value using this rule:

- `0 <= value < 1`: value is a fraction of the monitor size
- `value >= 1`: value is a pixel coordinate relative to the monitor

With `window.position.x=0.5`, the requested x coordinate starts halfway across
the selected monitor. With `window.position.x=300`, the requested x coordinate
starts 300 pixels from the selected monitor origin.

The selected monitor origin is then added to the relative coordinate to produce
an absolute desktop coordinate.

---

## Window Offsets

Window decoration changes the outer size of the native window. The sketch size
is the drawable Processing area, but the operating system window may also
include borders and a title bar.

`WindowOffsetResolver` resolves those offsets before the final clamp:

- with `JAVA2D`, it reads real `Insets` from the AWT `Frame`
- with `P2D`, it uses a fixed fallback offset

This is needed because clamping only the sketch width and height can still leave
the native window decoration outside the target monitor.

The `P2D` values are approximate because Processing's OpenGL surface does not
expose the same AWT window details as `JAVA2D`.

Without this step, the window may appear correctly positioned but still overflow
the monitor due to its decoration.

---

## Clamping

The final position is clamped so the window stays inside the selected monitor.

`WindowPositionResolver` calculates the outer window size using the sketch size
plus `WindowOffsets`:

```text
outerWidth  = sketchWidth  + left + right
outerHeight = sketchHeight + top  + bottom
```

The minimum position is the target monitor origin. The maximum position is the
target monitor origin plus its size minus the outer window size.

If the requested position would place part of the window outside the monitor,
the position is moved back inside the allowed range.


This guarantees that no part of the window (including its borders) leaves the
visible area of the target display.

---

## Example Configuration

```properties
window.display.primary=true
horizontal.screen.scale.factor=0.8
vertical.screen.scale.factor=0.8
window.position.x=0.5
window.position.y=0.5
```

This configuration selects the primary monitor, creates a window at 80% of that
monitor's width and height, and requests a position halfway across and halfway
down the selected monitor.

The final position may be adjusted by clamping so the outer window remains fully
inside the target monitor.

---

## Notes

- exact behavior can vary by operating system and window manager
- `P2D` decoration offsets are approximate fallback values
- monitor ordering can vary between systems
- secondary display selection uses the first detected non-primary monitor
- window positioning is applied after the Processing surface is created
