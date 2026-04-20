# CPZ MVVM Processing Template
![Java](https://img.shields.io/badge/Java-25+-orange)
![Processing](https://img.shields.io/badge/Processing-4.5.x-blue)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
[![GitHub](https://img.shields.io/badge/GitHub-cdpoloz-181717?logo=github)](https://github.com/cdpoloz)

A starter Processing sketch that consumes `cpz-mvvm-processing-controls`
through its public facades, explicit input routing, and sketch-owned behavior.

---

## Key design decisions

- Companion template, not a second framework
- Public facades only (`Button`, `Label`, and other controls as your sketch grows)
- Explicit input routing through `InputManager` and a template-owned `MainInputLayer`
- Behavior and binding stay in the sketch
- Configuration and launch bootstrap stay application-side and replaceable
- No automatic binding, no reflection, no hidden global state

---

## Overview

This repository is a clean starting point for building a Processing desktop
application with `cpz-mvvm-processing-controls`.

`cpz-mvvm-processing-controls` is the framework. It owns the reusable UI
controls, MVVM internals, rendering pipeline, event types, theming, and input
manager.

This template owns only the application side:

- `Launcher`: loads `data/config.properties` and starts the Processing sketch
- `Config`: applies window size, icon, smoothing, frame rate, and title
- `Sketch`: creates controls, wires behavior, dispatches Processing input events
- `MainInputLayer`: routes normalized input events to the controls selected by the sketch

The example intentionally stays direct: pointer input reaches a button, the
button increments a counter, and a label displays the result. The label uses
`data/font/abel-regular.ttf`.

---

## Mental Model

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

## Current Example

The current sketch creates:

- `Button btnTest`: interactive facade from `controls`
- `Label lblTest`: non-interactive facade from `controls`
- `InputManager`: framework dispatcher
- `MainInputLayer`: app-owned input layer that forwards pointer events to the button

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

## Input Routing

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

## Project Structure

```text
data/
  config.properties          Window and runtime properties used by Launcher/Config
  font/abel-regular.ttf      Font used by the example Label
  img/windowIcon.png         Window icon

src/com/cpz/processing/template/
  config/                    Processing window setup helpers
  input/MainInputLayer.java  Template-owned pointer/keyboard routing layer
  logging/                   Local logging bootstrap
  main/Launcher.java         Application entry point
  main/Sketch.java           Example sketch and behavior wiring

docs/
  architecture.md            Template architecture notes
  uml/                       Lightweight PlantUML diagrams
```

---

## Getting Started

1. Clone this repository.
2. Open it in your Java IDE.
3. Make sure Processing 4.5.x and `cpz-mvvm-processing-controls` are on the classpath.
4. Run `com.cpz.processing.template.main.Launcher`.
5. Edit `Sketch` to replace the example button/label with your own controls.

The repository currently keeps `Config` and `Launcher` as practical bootstrap
code for this template. They are application-side infrastructure and can be
simplified or replaced in downstream sketches.

---

## Relationship With Controls

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

## Documentation

- [Architecture](docs/architecture.md)
- [UML Architecture](docs/uml/uml-architecture.puml)
- [UML Detail](docs/uml/uml-detail.puml)

---

## License

This project is licensed under the MIT License.

---

## Author

**Carlos Polo Zamora**  
GitHub: https://github.com/cdpoloz  
Alias: CPZ / cepezeta / cdpoloz