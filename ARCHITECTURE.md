# Arquitectura y reglas de diseño

Este documento define las **reglas arquitectónicas obligatorias**
para mantener la coherencia del proyecto a lo largo del tiempo.

---

## 📦 Regla de paquetes

| Paquete | Responsabilidad |
|------|-----------------|
| `model` | Estado puro |
| `viewmodel` | Lógica y reglas |
| `main` | Ciclo Processing |
| `view` | Render |
| `view.dto` | Datos de render |
| `input` | Adaptadores de input |
| `util` | Utilidades sin estado ni lógica de negocio (ej. Tools) |
| `config` | Bootstrap |
| `logging` | Infraestructura de logs |

Si dudas dónde poner algo → probablemente va en **ViewModel**.

---

## ❌ Prohibiciones

- Lógica de negocio en `Sketch`.
- Dependencias de Processing en Model/ViewModel.
- Timers en el Model.
- DTOs con lógica.
- Estado compartido fuera del ViewModel.

---

## ✅ Permisos

- `Sketch` orquesta ciclo y render.
- `SketchView` encapsula dibujo.
- `IntervalTimer` vive en ViewModel.
- El tiempo entra como parámetro.
- Utilitarios como `Tools` pueden vivir en `util` si son estáticos y sin estado.
- Lombok y anotaciones JetBrains están permitidas solo para reducir boilerplate o mejorar legibilidad; no deben introducir lógica ni dependencias ocultas.

---

## 🛠️ Checklist de revisión

Antes de aceptar cambios:

### Arquitectura
- [ ] Lógica en ViewModel
- [ ] Model pasivo
- [ ] View sin decisiones

### Dependencias
- [ ] ViewModel sin Processing
- [ ] Model sin dependencias externas

### Render
- [ ] DTOs inmutables
- [ ] DTOs creados por frame

Si alguna respuesta es NO → revisar diseño.

---

Este documento es parte del contrato arquitectónico del proyecto.
