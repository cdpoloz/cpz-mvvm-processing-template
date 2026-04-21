# Noise Examples

Small examples for `cpz-utils.noise` live in:

```text
src/com/cpz/processing/template/examples/noise/
```

Both sketches use `ProcessingNoiseSource` as the adapter between Processing's
`noise(float)` function and the `cpz-utils` `NoiseSource` interface. This keeps
Processing in the template layer while `cpz-utils` remains independent from
Processing.

---

## NoiseValueSketch

`NoiseValueSketch` demonstrates the one-dimensional case:

- create a `ProcessingNoiseSource`
- create one `NoiseValue`
- advance it once per frame with `update()`
- read the current value with `get()`
- map the value from `0..1` to a vertical screen coordinate

The sketch draws a horizontal line that moves smoothly up and down.

---

## NoiseVector3Sketch

`NoiseVector3Sketch` demonstrates the three-dimensional convenience wrapper:

- `NoiseVector3` composes three noise values
- x and y are mapped to screen position
- z is mapped to the ellipse diameter
- each axis starts from a different random position and can use a different speed

The sketch validates real integration between `ProcessingNoiseSource`,
`NoiseVector3`, and `Vector3f` without putting Processing-specific code inside
`cpz-utils`.
