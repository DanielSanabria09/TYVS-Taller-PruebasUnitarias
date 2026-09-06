# Registro de Defectos — Registraduría

### Defecto 01
- **Caso de prueba**: Persona con `id = 0`, viva, y no viva a la vez no aplica; el defecto real es de orden de reglas.
- **Entrada**: `Person(id=0, age=15, alive=false)`
- **Resultado esperado**: `INVALID` (R2 se evalúa antes que R3, según el orden R1→R7 de la especificación)
- **Resultado obtenido** (antes de reordenar): `DEAD` (el código original revisaba `alive` antes que `id`)
- **Causa probable**: `Registry.registerVoter` no respetaba el orden de evaluación R1→R7 definido en el README.
- **Estado**: Resuelto — se reordenó el método para chequear `id` (R2) antes que `alive` (R3), y se verificó con `shouldRejectWhenIdIsZeroOrNegative`.