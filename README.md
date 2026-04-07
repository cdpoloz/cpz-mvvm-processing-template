# CPZ MVVM Processing Template
![Java](https://img.shields.io/badge/Java-25+-orange)
![Processing](https://img.shields.io/badge/Processing-4.5.x-blue)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

This is a starter template that consumes `cpz-mvvm-processing-controls`.
It provides a clean starting point for Processing desktop applications that want to keep Processing bootstrap code thin and move UI behavior into the controls library.

## Architecture Overview

The template follows a strict consumer model:

- `model`: application state.
- `viewmodel`: pure Java application logic.
- `main`: Processing bootstrap, dependency wiring, callback forwarding, and control drawing.

`Sketch` is intentionally small. It sets up Processing, creates the controls wiring, forwards keyboard and pointer callbacks to adapters, syncs state from `MainViewModel`, and draws controls.

The controls library owns the reusable UI framework pieces:

- `InputManager`
- `KeyboardEvent` / `PointerEvent`
- control views and adapters
- theming and rendering infrastructure

This template does not reimplement those systems.

## Input Flow (Simplified)

```text
Processing (or any source)
        ↓
Adapters (Keyboard / Pointer)
        ↓
State (KeyboardState / PointerState)
        ↓
InputManager
        ↓
Controls (via InputLayer & Adapters)
        ↓
ViewModel
```

In practice:

- Processing keyboard callbacks go to `ProcessingKeyboardAdapter`.
- Processing pointer callbacks go to `ProcessingPointerAdapter`.
- Adapters update `KeyboardState` / `PointerState`.
- Adapters dispatch `KeyboardEvent` / `PointerEvent` through `InputManager`.
- Controls react through the controls library input pipeline.
- `MainViewModel` stays independent from Processing APIs.

## What Is Included

- Minimal `Sketch` and `Launcher`
- Application-specific `AppState` and `MainViewModel`
- `InputManager` and `ThemeManager` wiring
- Keyboard and pointer adapter/state classes
- Example controls: button, slider, and label

## Usage Guidelines

DO:

- Forward Processing callbacks to the input adapters.
- Keep `MainViewModel` free of Processing dependencies.
- Use controls from `cpz-mvvm-processing-controls` as the UI layer.
- Keep `Sketch` focused on bootstrap, forwarding, and drawing.

DO NOT:

- Handle input logic directly in `Sketch`.
- Access `mouseX`, `mouseY`, `key`, or `keyCode` outside adapters.
- Reintroduce custom input systems or direct Processing-to-control shortcuts.
- Rebuild framework pieces that already belong to the controls project.

## Quick Start

1. Clone this repository.
2. Make sure Processing 4.x is available in the project.
3. Add `cpz-mvvm-processing-controls` to the classpath.
4. Run `Launcher`.

## Related Project

`cpz-mvvm-processing-controls`

- controls = reusable framework and UI infrastructure
- template = starter consumer that shows how to wire the framework into an app

Use the controls project as the reference for shared architecture and reusable behavior.

## More Documentation

- [Architecture](/C:/Users/carlos.polo/Software/CPZ/cpz-mvvm-processing-template/docs/architecture.md)
- [UML Architecture](/C:/Users/carlos.polo/Software/CPZ/cpz-mvvm-processing-template/docs/uml/uml-architecture.puml)
- [UML Detail](/C:/Users/carlos.polo/Software/CPZ/cpz-mvvm-processing-template/docs/uml/uml-detail.puml)

## License

This project is licensed under the MIT License.
