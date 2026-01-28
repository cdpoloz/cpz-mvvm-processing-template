# CPZ Processing Template — MVVM

## 🎯 Propósito

Esta plantilla sirve como base profesional para **aplicaciones de escritorio en Java usando Processing**,
estructuradas mediante el patrón **MVVM (Model–View–ViewModel)**.

Está pensada para:
- evitar el crecimiento desordenado típico de sketches Processing,
- facilitar mantenimiento a largo plazo,
- permitir escalar a aplicaciones con múltiples vistas y elementos gráficos,
- servir como base común para varios desarrolladores.

Nota: este proyecto es 100% Java + Processing y no depende de frameworks externos ni requiere dependencias externas.
Lombok y las anotaciones de JetBrains se usan solo como ayudas de desarrollo (boilerplate/legibilidad) y pueden eliminarse sin impacto arquitectónico ni de runtime.

---

## 🧠 Arquitectura general

La arquitectura sigue el siguiente flujo:

```
Input → ViewModel → Model
                 ↓
               View → Renderer
```

- **Model**: estado puro de la aplicación.
- **ViewModel**: lógica, reglas y decisiones (fuente única de verdad).
- **View**: orquestación del ciclo Processing.
- **Renderer**: dibujo puro sobre Processing.
- **DTOs de render**: datos efímeros por frame.

📐 Diagramas disponibles:
- `uml/uml-architecture.puml` → visión global.
- `uml/uml-detail.puml` → documentación técnica detallada.

---

## 🧩 Capas y responsabilidades

### Model (`model`)
- Estado puro.
- Sin lógica.
- Sin dependencias de Processing.

Ejemplo: `AppState`.

### ViewModel (`viewmodel`)
- Toda la lógica de negocio.
- Gestión de input y tiempo.
- No dibuja.
- No depende de Processing.

Ejemplo: `MainViewModel`.

### View (`main`)
- Implementada por `Sketch` (`PApplet`).
- Orquesta `update()` y `dibujarUI()`.
- Construye DTOs de render por frame.
- No contiene lógica de negocio.

### Renderer (`view`)
- Código de dibujo puro.
- Encapsula llamadas a Processing.
- No mantiene estado.

Ejemplo: `SketchView`.

### DTOs de render (`view.dto`)
- Objetos inmutables.
- Viven solo un frame.
- No contienen lógica.

Ejemplos:
- `ParametrosRectangulo`
- `ParametrosElipse`

---

## ⏱️ Tiempo y timers

- El tiempo (`millis`) se captura en la View.
- La lógica temporal vive en el ViewModel.
- `IntervalTimer` es un utilitario puro.
- El Model no contiene temporizadores.

---

## 📜 Reglas clave

- ❌ No poner lógica en `Sketch`.
- ❌ No usar Processing en Model o ViewModel.
- ❌ No dibujar desde el ViewModel.
- ✔ El ViewModel es la única fuente de verdad.
- ✔ El Renderer solo dibuja.
- ✔ Los DTOs se crean por frame y se descartan.

---

## 🧭 Uso recomendado

1. Copia esta plantilla.
2. Crea nuevos ViewModels por vista.
3. Mantén las reglas arquitectónicas.
4. Documenta cambios relevantes en UML.
