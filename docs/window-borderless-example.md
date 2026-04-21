# Borderless Window Example

## Overview

`BorderlessWindowSketch` shows how to create a Processing window without the
native operating system border.

The example is intentionally separate from the main template sketch because
borderless window behavior has renderer-specific constraints.

---

## Important limitation

This example intentionally uses Processing's default renderer (`JAVA2D`).

This is a host-level customization, not a rendering feature.

This example is not compatible with `P2D` or `P3D`. Borderless customization depends on
AWT access through `PSurfaceAWT` and `Frame`, which is only available for AWT
surfaces.

```text
Renderer (P2D / JAVA2D)
          ↓
Surface (OpenGL vs AWT)
          ↓
Window control (only AWT)
```

---

## How it works

`BorderlessWindowSketch` overrides `initSurface()` and passes the surface
created by Processing to `ProcessingWindowConfigurator.setUndecorated(...)`.

`ProcessingWindowConfigurator` expects a `PSurfaceAWT`, reads its native
`SmoothCanvas`, obtains the backing `Frame`, and calls `setUndecorated(...)` on
that frame.

Note: ProcessingWindowConfigurator.setUndecorated(...) also forces the window to be always on top (`setAlwaysOnTop(true)`).

---

## Code reference

```java
@Override
public PSurface initSurface() {
    return ProcessingWindowConfigurator.setUndecorated(super.initSurface(), true);
}
```

---

## Why this does not work with P2D

`P2D` uses Processing's OpenGL renderer through JOGL. `P3D` uses the same
OpenGL surface model for 3D rendering.

Those renderers do not expose the AWT `Frame` expected by
`ProcessingWindowConfigurator`, so `setUndecorated()` cannot be applied through
`PSurfaceAWT`.

---

## When to use this

- kiosk-style tools
- dashboards without native window UI
- custom fullscreen applications

---

## When NOT to use this

- sketches that require `P2D` or `P3D`
- applications that need GPU rendering
