# ProyectoIA_Movil

- Copia los archivos a su ruta correspondiente.
- Ajusta `build.gradle` con las versiones que uses.
- La app usa una API simulada (`MockApi`) como data source.
- El ViewModel sigue un patrón MVI simple: intents que disparan cambios en el state.


Mejoras sugeridas:
- Integrar Hilt/Koin para inyección de dependencias.
- Reemplazar `MockApi` con Retrofit y un servidor real.
- Añadir tests unitarios para UseCases y ViewModel.