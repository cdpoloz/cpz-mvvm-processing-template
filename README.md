# CPZ MVVM Processing Template
![Java](https://img.shields.io/badge/Java-25+-orange)
![Processing](https://img.shields.io/badge/Processing-4.x-blue)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

Minimal starter template for building Processing desktop applications using a strict MVVM architecture powered by cpz-mvvm-processing-controls.

------------------------------------------------------------------------

## Why this template?

Processing sketches typically grow around a single `PApplet` class where rendering, input handling, and state are mixed together.

This template provides a clean starting point that:

- separates application logic from rendering
- delegates UI behavior to a dedicated controls library
- uses explicit input routing instead of direct event handling
- keeps the Processing sketch focused only on bootstrapping and orchestration

It is designed to be cloned and used as the foundation for new applications.

------------------------------------------------------------------------

## Relationship with cpz-mvvm-processing-controls

This template is a **consumer** of:

→ `cpz-mvvm-processing-controls`

The controls library provides:

- MVVM architecture primitives
- Input system (`InputManager`, `InputLayer`, `FocusManager`)
- Rendering pipeline (`ViewState → Style → RenderStyle → Renderer`)
- Theming system (`ThemeManager`, `ThemeSnapshot`)

This template **does not reimplement any of these systems**.

Instead, it demonstrates how to wire them together in a minimal application.

------------------------------------------------------------------------

## What is included

- Minimal `Sketch` acting as Processing entry point
- Application-specific `Model` and `ViewModel`
- Basic wiring of:
    - `InputManager`
    - `ThemeManager`
- Example controls:
    - Button
    - Slider
    - Label
- Simple interaction:
    - Button toggles state
    - Slider updates a numeric value
    - Label reflects current state

------------------------------------------------------------------------

## Quick Start

1. Clone this repository
2. Ensure Processing 4.x is available in your project
3. Add `cpz-mvvm-processing-controls` to your classpath
4. Run `Launcher`

The application will display a minimal UI composed of controls from the library.

------------------------------------------------------------------------

## Application Flow

Typical execution flow:

- Processing calls `draw()`
- The `Sketch` updates the `ViewModel`
- Controls receive input through `InputManager`
- Views build `ViewState`
- Styles resolve visual properties using `ThemeSnapshot`
- Renderers draw to the screen

The `Sketch` itself does not contain rendering logic or business logic.

------------------------------------------------------------------------

## Project Structure

- `main` → Processing entry point (`Sketch`, `Launcher`)
- `model` → application state (pure data)
- `viewmodel` → interaction logic and state transitions
- `config` → application configuration
- `util` → generic utilities (time, logging, etc.)
- `data` → configuration files and assets

------------------------------------------------------------------------

## Design Principles

- No duplicated architecture (all core systems come from controls)
- Explicit data flow
- Separation of concerns
- Minimal bootstrap code
- Ready to extend

------------------------------------------------------------------------

## Intended Usage

This template is meant to:

- serve as a starting point for new Processing desktop applications
- demonstrate correct usage of the controls library
- avoid reimplementing MVVM, input, or rendering infrastructure

------------------------------------------------------------------------

## Status

This template is actively maintained alongside the controls library and reflects the current architecture.

------------------------------------------------------------------------

## License

This project is licensed under the MIT License.