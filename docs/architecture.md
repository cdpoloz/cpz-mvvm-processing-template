# Architecture

This repository is a consumer template for `cpz-mvvm-processing-controls`.

The controls project is the architectural source of truth for reusable UI
behavior. The template exists to show how a Processing sketch can wire the
framework from the outside using public facades and explicit input routing.

---

## Template Responsibilities

The template owns:

- Processing bootstrap
- window/runtime configuration
- sketch-specific control creation
- explicit listener wiring
- dispatch from Processing callbacks into framework-owned pointer events
- app-level input routing through `MainInputLayer`

The template does not own:

- control MVVM internals
- control views or view models
- control-specific input adapters
- rendering pipelines
- automatic binding
- global theme or input state

---

## Components

### Launcher

`Launcher` is the application entry point.

It loads `data/config.properties`, configures logging shutdown, and starts
`Sketch` through Processing.

### Config

`Config` applies the current window bootstrap:

- window icon
- screen-relative size
- renderer
- smoothing
- frame rate
- window title

It is application-side setup. It is intentionally outside the controls framework.

### Sketch

`Sketch` is the example consumer.

It creates public facades from `controls`, wires listeners, dispatches pointer
events, and draws controls.

Current controls:

- `Button btnTest`
- `Label lblTest`

Current behavior:

- clicking the button increments a local counter
- the label text is updated explicitly from the sketch
- the label is not registered as an input target
- keyboard input is not used by this minimal example

### MainInputLayer

`MainInputLayer` is the template-owned input layer.

It receives normalized `PointerEvent` and `KeyboardEvent` instances from
`InputManager` and forwards them to targets registered by the sketch. The
current sketch registers one pointer target, but the layer is already prepared
for keyboard-aware controls.

Registration is explicit:

```java
MainInputLayer mainInputLayer = new MainInputLayer(0)
        .addPointerTarget(button::handlePointerEvent);
inputManager.registerLayer(mainInputLayer);
```

Future pointer and keyboard-aware controls can be added without changing the
structure of the layer:

```java
mainInputLayer
        .addPointerTarget(slider::handlePointerEvent)
        .addPointerTarget(textField::handlePointerEvent)
        .addKeyboardTarget(textField::handleKeyboardEvent);
```

This keeps the app layer responsible for routing while still using only public
facade APIs. The layer uses simple template semantics: an event is consumed when
at least one registered target receives it.

---

## Input Flow

The final controls framework model is:

```text
External Source -> Adapter -> InputManager -> InputLayer -> InputAdapter -> ViewModel
```

In the current example:

```text
Processing mouse callbacks
        |
        v
Sketch creates PointerEvent
        |
        v
InputManager
        |
        v
MainInputLayer
        |
        v
Button facade
```

`Sketch` currently adapts mouse callbacks directly because the example is small.
Keyboard dispatch is supported by `MainInputLayer`, but the example does not
create or dispatch `KeyboardEvent` instances. The important boundary is that the
controls framework receives normalized framework events, not Processing callback
state.

---

## Composition Rule

The sketch owns behavior.

Controls expose public methods and listeners. The sketch decides how controls
affect each other:

```java
button.setClickListener(this::btnTestClicked);
```

```java
private void btnTestClicked() {
    clickCount++;
    label.setText("Click count = " + clickCount);
}
```

There is no binding DSL and no automatic synchronization. That is intentional.

---

## Extension Pattern

When adding a new interactive control:

1. Create the facade in `Sketch.setup()`.
2. Register its public pointer and/or keyboard input method in `MainInputLayer`.
3. Attach any listeners in `Sketch`.
4. Update other controls explicitly from sketch methods.
5. Draw it in `Sketch.draw()`.

For non-interactive controls such as `Label`, create and draw the facade, but do
not register it as an input target.

---

## Related

- [Project README](../README.md)
- [UML Architecture](uml/uml-architecture.puml)
- [UML Detail](uml/uml-detail.puml)
