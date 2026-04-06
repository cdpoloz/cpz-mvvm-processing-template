# Arquitectura

La unica fuente de verdad arquitectonica para esta plantilla es `cpz-mvvm-processing-controls`.

## Regla principal

La plantilla debe actuar como consumidor limpio de la libreria:

- `model`: estado de aplicacion.
- `viewmodel`: logica de aplicacion.
- `main`: bootstrap de Processing y cableado minimo.

## No permitido

- Reintroducir un framework MVVM propio.
- Reimplementar `InputManager`, `InputLayer` o adaptadores de teclado y puntero.
- Recrear pipelines de render o DTOs genericos de dibujo.
- Implementar un sistema de temas paralelo.

## Permitido

- Crear estado y logica especificos de la app.
- Instanciar controles de la libreria y conectarlos al `MainViewModel`.
- Usar `ThemeManager` e `InputManager` de la libreria en `Sketch`.

## Checklist

- `Sketch` solo hace bootstrap, forwarding de eventos y `draw()`.
- `MainViewModel` sigue siendo Java puro.
- Los controles usan estilos y temas de la libreria.
- No aparecen sistemas legacy de input o render en la plantilla.
