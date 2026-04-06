# CPZ Processing Template

Plantilla minima para arrancar una app Processing como consumidora de `cpz-mvvm-processing-controls`.

## Que incluye

- Un `AppState` con estado de aplicacion.
- Un `MainViewModel` con logica pura Java.
- Un `Sketch` que solo hace bootstrap de Processing, tema, input y controles.
- Un ejemplo funcional con `ButtonView`, `SliderView` y `LabelView`.

## Arquitectura

La arquitectura compartida vive en `cpz-mvvm-processing-controls`.
Esta plantilla no define su propio framework MVVM, input manager, pipeline de render ni sistema de temas.

## Demo incluida

- El boton activa o desactiva una bandera booleana del `MainViewModel`.
- El slider modifica un valor numerico del `MainViewModel`.
- El label refleja el estado actual, el valor y el tiempo transcurrido.

## Dependencia local

El modulo IntelliJ de la plantilla depende del modulo hermano `cpz-mvvm-processing-controls`.
La plantilla debe mantenerse como consumidor limpio de esa libreria.
