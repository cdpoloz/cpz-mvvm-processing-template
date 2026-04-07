# Architecture

The only architectural source of truth for this template is `cpz-mvvm-processing-controls`.
This project is a thin consumer of that library, not a parallel framework.

## Main Rule

The template owns only application-specific state, simple wiring, and demo composition:

- `model`: application state.
- `viewmodel`: application logic.
- `main`: Processing bootstrap, dependency wiring, callback forwarding, and control drawing.
- `input.keyboard`: adapter and state for keyboard input.
- `input.pointer`: adapter and state for pointer input.

## Sketch Responsibilities

`Sketch` is intentionally narrow in scope:

- Configure Processing runtime values already prepared by `Config`.
- Instantiate the theme, `InputManager`, adapters, and demo controls.
- Forward Processing keyboard and pointer callbacks to the corresponding adapters.
- Call `draw()` on controls after syncing view state from `MainViewModel`.

`Sketch` does not:

- interpret keyboard or pointer state directly
- use deprecated Processing keyboard APIs such as `keyEvent`
- implement its own input framework
- contain business logic that belongs in `MainViewModel`

## Input Flow

Keyboard flow:

`Processing -> ProcessingKeyboardAdapter -> KeyboardState -> InputManager -> KeyboardEvent -> Controls`

Pointer flow:

`Processing -> ProcessingPointerAdapter -> PointerState -> InputManager -> PointerEvent -> Controls`

The adapters translate Processing callbacks into the controls library input model.
The state classes retain current input state so modifier and pointer information can be reused across callbacks.

Simplified end-to-end view:

```text
Processing (or any source)
        |
        v
Adapters (Keyboard / Pointer)
        |
        v
State (KeyboardState / PointerState)
        |
        v
InputManager
        |
        v
Controls (via InputLayer & Adapters)
        |
        v
ViewModel
```

## Input Components

### KeyboardState

Responsibility:
- Store current keyboard state, including pressed modifiers and pressed key codes.

Dependencies:
- None outside the Java standard library.

Does not:
- depend on Processing
- dispatch events
- contain control logic

### ProcessingKeyboardAdapter

Responsibility:
- Receive Processing keyboard callbacks.
- Update `KeyboardState`.
- Dispatch `KeyboardEvent` instances through `InputManager`.

Dependencies:
- `KeyboardState`
- `InputManager`

Does not:
- store application state
- interpret ViewModel behavior
- use deprecated `keyEvent`

### PointerState

Responsibility:
- Store pointer position, pressed state, active button, and wheel delta.

Dependencies:
- None outside the Java standard library.

Does not:
- depend on Processing
- dispatch events
- contain control logic

### ProcessingPointerAdapter

Responsibility:
- Receive Processing pointer callbacks.
- Update `PointerState`.
- Reuse `KeyboardState` modifier flags.
- Dispatch `PointerEvent` instances through `InputManager`.

Dependencies:
- `PointerState`
- `KeyboardState`
- `InputManager`

Does not:
- implement control behavior
- contain business logic
- bypass the controls input pipeline

## Allowed

- Creating app-specific state and logic.
- Instantiating controls from the library and binding them to `MainViewModel`.
- Using `ThemeManager`, `InputManager`, `KeyboardEvent`, and `PointerEvent` from the library.
- Extending the template with more app-specific screens or controls while preserving the same input flow.

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

## Not Allowed

- Reintroducing a custom MVVM framework.
- Reimplementing `InputManager` or control-side input behavior.
- Handling keyboard modifiers in `Sketch` through deprecated Processing APIs.
- Creating direct Processing-to-control shortcuts that skip adapters and state holders.
- Recreating legacy render or input systems inside the template.

## Related Project

`cpz-mvvm-processing-controls` is the framework project.
This template is the starter consumer project.

Use the controls project as the reference for:

- shared UI behavior
- control-side input handling
- theming and rendering infrastructure

## Checklist

- `Sketch` only handles bootstrap, dependency wiring, callback forwarding, and `draw()`.
- `MainViewModel` remains pure Java.
- Controls consume `KeyboardEvent` and `PointerEvent` through `InputManager`.
- Keyboard state lives in `KeyboardState`.
- Pointer state lives in `PointerState`.
- No references remain to `keyEvent` or removed legacy input classes.
