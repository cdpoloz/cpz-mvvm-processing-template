# CPZ MVVM Processing Template
![Java](https://img.shields.io/badge/Java-25+-orange)
![Processing](https://img.shields.io/badge/Processing-4.5.x-blue)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
[![GitHub](https://img.shields.io/badge/GitHub-cdpoloz-181717?logo=github)](https://github.com/cdpoloz)

A minimal, ready-to-run Processing template built on top of
`cpz-mvvm-processing-controls`.

Clone it, run it, click the button — and you're ready to start building.

---

## Overview

This repository is a clean starting point for building Processing desktop
application with `cpz-mvvm-processing-controls`.

`cpz-mvvm-processing-controls` is the framework. It owns the reusable UI
controls, MVVM internals, rendering pipeline, event types, theming, and input
manager.

This template contains only the application-side code:

- `Launcher`: loads `data/config.properties` and starts the Processing sketch
- `Config`: applies window size, icon, smoothing, frame rate, and title
- `Sketch`: creates controls, wires behavior, dispatches Processing input events
- `MainInputLayer`: routes normalized input events to the controls selected by the sketch

The example intentionally stays direct: pointer input reaches a button, the
button increments a counter, and a label displays the result. The label uses
`data/font/abel-regular.ttf`.

---

## What happens when you run it

- A window opens with a button and a label
- Clicking the button increments a counter
- The label updates immediately with the new value
- The label uses a custom font loaded from `data/font`

Everything is wired explicitly in the sketch:
no binding system, no hidden behavior.

---

## Quick start

1. Clone this repository
2. Open it in your Java IDE (IntelliJ recommended)
3. Add Processing 4.5.x and `cpz-mvvm-processing-controls` to the classpath
4. Run `com.cpz.processing.template.main.Launcher`

You should see a window with a button and a label.
Click the button to verify everything is working.

---

## Dependencies

This template includes local copies of the required libraries under `lib/`:

- Processing 4.5.x
- cpz-mvvm-processing-controls

After cloning the repository, make sure your IDE adds those JARs to the project
classpath before running `Launcher`.

If your IDE does not detect them automatically, add the JAR files from `lib/`
manually in the project/module dependencies.

---

## Mental Model

At a glance, the template behaves like this:

```text
Template sketch
   |-- Launcher + Config
   |-- Sketch
   |   |-- InputManager
   |   |-- MainInputLayer
   |   `-- Controls (public facades)
   `-- data/

Input:
Processing mouse callbacks -> PointerEvent -> InputManager
                           -> MainInputLayer -> selected control facades

Behavior:
control listener -> sketch method -> update other public facades

Rendering:
Sketch.draw() -> control.draw()
```

The template does not create or expose control `Model`, `ViewModel`, `View`,
`Style`, `Renderer`, or control-specific input adapters. Those are framework
internals.

---

## Key design decisions

- Companion template, not a second framework
- Public facades only (`Button`, `Label`, and other controls as your sketch grows)
- Explicit input routing through `InputManager` and a template-owned `MainInputLayer`
- Behavior and binding stay in the sketch
- Configuration and launch bootstrap stay application-side and replaceable
- No automatic binding, no reflection, no hidden global state

---

## Renderer Policy

The main template uses `P2D` by default.

`P2D` is Processing's OpenGL-based 2D renderer. It is the default choice here
because the template is intended for real-time 2D sketches and UI-driven
applications where GPU-backed rendering is a practical baseline.

### Window customization limitations

Borderless window customization depends on AWT-based surfaces.

`ProcessingWindowConfigurator` accesses the native window through
`PSurfaceAWT` and `Frame`. This only works with Processing's default renderer
(`JAVA2D`).

It does not work with `P2D` or `P3D`, because those renderers use OpenGL
surfaces instead of the AWT surface expected by `ProcessingWindowConfigurator`.
Those renderers do not expose an AWT Frame.

---

## Current Example

The example is intentionally minimal and interactive:

- `Button btnTest`: interactive facade from `controls`
- `Label lblTest`: non-interactive facade from `controls`
- `InputManager`: framework dispatcher
- `MainInputLayer`: app-owned input layer that forwards pointer events to the button

The main example runs with the template renderer policy, so it uses `P2D`.
Window behavior examples, such as the borderless window sketch, live separately
because they may require different renderer constraints.

The current example uses mouse input only. `MainInputLayer` also supports
keyboard targets so downstream sketches can register text fields, numeric
fields, radio groups, or other keyboard-aware facades when needed.

The click behavior is explicit:

```java
button.setClickListener(this::btnTestClicked);
```

The binding is also explicit:

```java
private void btnTestClicked() {
    clickCount++;
    label.setText("Click count = " + clickCount);
}
```

There is no automatic binding layer. The sketch decides what a click means.

---

## Minimal Editing Path

1. Add controls in `Sketch.setup()`.
2. Register pointer and/or keyboard-aware controls in `MainInputLayer`.
3. Attach listeners to the facades that own user actions.
4. Update other facades from sketch methods.
5. Draw controls in `Sketch.draw()`.

The default example uses direct facade construction. JSON composition from
`controls` can be added later when your sketch needs structural configuration,
but behavior should still stay in sketch code.

---

## Input Routing
### How input flows in the template

This section explains the full input pipeline.  
If you just want to use the template, you can skip this for now.

The framework input model is:

```text
External Source -> Adapter -> InputManager -> InputLayer -> InputAdapter -> ViewModel
```

In the current example, Processing is the external source and `Sketch` performs
the small adapter step by creating framework-owned `PointerEvent` instances:

```java
public void mousePressed() {
    inputManager.dispatchPointer(
            new PointerEvent(PointerEvent.Type.PRESS, mouseX, mouseY, mouseButton)
    );
}
```

`MainInputLayer` is the application layer. It receives events from
`InputManager` and forwards them to the public facades registered by the sketch.
The example registers only the button as a pointer target:

```java
MainInputLayer mainInputLayer = new MainInputLayer(0)
        .addPointerTarget(button::handlePointerEvent);
inputManager.registerLayer(mainInputLayer);
```

When you add more interactive controls, register pointer and keyboard targets
explicitly:

```java
mainInputLayer
        .addPointerTarget(button::handlePointerEvent)
        .addPointerTarget(slider::handlePointerEvent)
        .addPointerTarget(textField::handlePointerEvent)
        .addKeyboardTarget(textField::handleKeyboardEvent);
```

This keeps the routing visible without coupling the template to framework
internals such as `ButtonInputLayer`, control views, or control view models.

`MainInputLayer` uses simple template semantics: if at least one registered
target receives an event, the layer consumes that event.

---

## Project Structure

```text
data/
  config.properties          Window and runtime properties used by Launcher/Config
  font/abel-regular.ttf      Font used by the example Label
  img/windowIcon.png         Window icon

src/com/cpz/processing/template/
  config/                    Processing window setup helpers
  examples/noise/            Minimal cpz-utils noise integration sketches
  input/
    MainInputLayer.java
    noise/ProcessingNoiseSource.java
  logging/                   Local logging bootstrap
  main/Launcher.java         Application entry point
  main/Sketch.java           Example sketch and behavior wiring

docs/
  architecture.md            Template architecture notes
  noise-examples.md          ProcessingNoiseSource + cpz-utils noise examples
  window-borderless.md       Borderless window renderer limitations
  uml/                       Lightweight PlantUML diagrams
```

---

## Relationship With Controls

If you're starting from scratch, begin with this template.  
If you want to understand or extend the framework itself, go to controls.

Use `cpz-mvvm-processing-controls` for:

- public control facades
- `Control`
- `InputManager`
- `InputLayer`
- `PointerEvent` and `KeyboardEvent`
- theming and rendering infrastructure
- optional JSON control loading

Use this template for:

- Processing launch/bootstrap
- example control creation
- app-owned input layer composition
- explicit listener wiring
- sketch-specific behavior

The boundary is intentional: controls supplies reusable UI infrastructure; the
template shows how a consuming sketch wires that infrastructure without becoming
another framework.

---

## Related project

[cpz-mvvm-processing-controls](https://github.com/cdpoloz/cpz-mvvm-processing-controls)

---

## Documentation

- [Architecture](docs/architecture.md)
- [Noise Examples](docs/noise-examples.md)
- [Borderless Window Example](docs/window-borderless-example.md)
- [UML Architecture](docs/uml/uml-architecture.puml)
- [UML Detail](docs/uml/uml-detail.puml)

---

## License


`cpz-mvvm-processing-template` is released under the MIT License. See [LICENSE](LICENSE).

---

## Author

**Carlos Polo Zamora**  
GitHub: https://github.com/cdpoloz  
Alias: CPZ / cepezeta / cdpoloz
