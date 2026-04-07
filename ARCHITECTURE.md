# Architecture

The only architectural source of truth for this template is `cpz-mvvm-processing-controls`.

## Main Rule

The template must act as a clean consumer of the library:

- `model`: application state.
- `viewmodel`: application logic.
- `main`: Processing bootstrap and minimal wiring.

## Not Allowed

- Reintroducing a custom MVVM framework.
- Reimplementing `InputManager`, `InputLayer`, or keyboard and pointer adapters.
- Recreating render pipelines or generic drawing DTOs.
- Implementing a parallel theme system.

## Allowed

- Creating app-specific state and logic.
- Instantiating library controls and connecting them to `MainViewModel`.
- Using the library `ThemeManager` and `InputManager` in `Sketch`.

## Checklist

- `Sketch` only handles bootstrap, event forwarding, and `draw()`.
- `MainViewModel` remains pure Java.
- Controls use styles and themes from the library.
- No legacy input or render systems appear in the template.
