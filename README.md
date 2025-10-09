# MiniZoológico

¡Bienvenido a **MiniZoológico**! Una aplicación Android educativa desarrollada con Jetpack Compose que permite buscar información sobre animales usando la API de OpenAI (ChatGPT). La app muestra datos curiosos como hábitat, dieta y una curiosidad interesante, en formato JSON parseado.

## Integrantes
- **José Rivera** – Desarrollo principal, integración de OpenAI y UI.
- **Marcela Castillo** – Diseño de la interfaz, manejo de estados y navegación.

##Video de la app funcionando: 
adf

## Descripción
- **Funcionalidades principales**:
  - Buscar un animal por nombre (e.g., "león") y obtener info generada por IA.
  - Generar un animal aleatorio.
  - Navegación a pantalla de resultados con datos estructurados.
  - Soporte opcional para imágenes de Unsplash (en desarrollo; por ahora usa placeholders).
  - Pantalla "About" con info del proyecto.
- **Tecnologías usadas**:
  - **Lenguaje**: Kotlin.
  - **UI**: Jetpack Compose (Material3).
  - **Arquitectura**: MVVM con StateFlow y ViewModel.
  - **APIs**: OpenAI (ChatGPT para texto) y Unsplash (imágenes, opcional).
  - **Networking**: Retrofit + Moshi (JSON parsing).
  - **Navegación**: Navigation Compose.
  - **Otras**: Coroutines para async, Coil para imágenes.

La app es un prototipo educativo para aprender sobre animales de forma interactiva.

## Requisitos
- **Android Studio**: Versión Hedgehog (2023.1.1) o superior.
- **SDK**: Android API 24+ (mínimo) y API 34 (compilación).
- **Dispositivo/Emulador**: Android 7.0+ con conexión a internet (para APIs).
- **Dependencias**: Gradle 8.0+ (configurado en `gradle/wrapper/gradle-wrapper.properties`).

## Configuración de API Keys
La app usa dos APIs externas que requieren claves gratuitas. **No incluyas las keys en el repositorio** (usa `local.properties` para mantenerlas privadas).

### 1. **OpenAI API Key** (Obligatoria – para texto de animales)
   - Ve a [platform.openai.com/api-keys](https://platform.openai.com/api-keys) y crea una cuenta (gratuita con créditos iniciales).
   - Copia tu clave (empieza con `sk-...`).
   - En la raíz del proyecto, crea o edita `local.properties`:
     ```
     sdk.dir=/ruta/a/tu/Android/Sdk
     OPENAI_API_KEY=sk-tu-clave-openai-real-aqui
     ```
   - En `app/build.gradle.kts` (en `defaultConfig`), asegúrate de que lea la key:
     ```kotlin
     val localProps = Properties()
     val localFile = rootProject.file("local.properties")
     if (localFile.exists()) {
         localFile.inputStream().use { localProps.load(it) }
     }
     val openAiKey: String = localProps.getProperty("OPENAI_API_KEY") ?: ""
     buildConfigField("String", "OPENAI_API_KEY", "\"$openAiKey\"")
     ```
   - **Límites**: 3 requests/minuto en plan gratuito. Si excedes, actualiza a pago ($0.002/1k tokens).

### 2. **Unsplash API Key** (Opcional – para imágenes; en desarrollo)
   - Ve a [unsplash.com/developers](https://unsplash.com/developers), regístrate y crea una "New Application".
   - Copia la **Access Key** (empieza con letras/números, e.g., `F5eQaJBD...`).
   - Agrega a `local.properties`:
     ```
     UNSPLASH_API_KEY=tu-access-key-unsplash-real-aqui
     ```
   - En `app/build.gradle.kts` (en `defaultConfig`), agrega:
     ```kotlin
     val unsplashKey: String = localProps.getProperty("UNSPLASH_API_KEY") ?: ""
     buildConfigField("String", "UNSPLASH_API_KEY", "\"$unsplashKey\"")
     ```
   - **Notas**: 
     - Sin key: 50 requests/hora. Con key: Ilimitado para desarrollo (cumple directrices: hotlink URLs, attribution en app).
     - Si no funciona: Verifica logs en Logcat ("UnsplashRepository"). Queries en inglés dan mejores resultados (e.g., "lion" para "león").
     - Attribution: La app incluye crédito al fotógrafo (e.g., "Foto por John Doe en Unsplash").

- **Sync Gradle**: Después de editar `local.properties`, ve a File > Sync Project with Gradle Files.
- **Seguridad**: Agrega `local.properties` a `.gitignore` para no subir keys al Git.

## Cómo correr la app
1. **Clona el proyecto**:
