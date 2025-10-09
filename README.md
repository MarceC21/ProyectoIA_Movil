# MiniZoológico

¡Bienvenido a **MiniZoológico**! Una aplicación Android educativa desarrollada con Jetpack Compose que permite buscar información sobre animales usando la API de OpenAI (ChatGPT). La app muestra datos curiosos como hábitat, dieta y una curiosidad interesante, en formato JSON parseado.

## Integrantes
- **José Rivera** – Desarrollo principal, integración de OpenAI y UI.
- **Marcela Castillo** – Diseño de la interfaz, manejo de estados y navegación.

## Video de la app funcionando:
- https://www.youtube.com/shorts/5oGUGPxA10Q 

## Descripción
- **Funcionalidades principales**:
  - Buscar un animal por nombre (e.g., "león") y obtener info generada por IA.
  - Generar un animal aleatorio.
  - Navegación a pantalla de resultados con datos estructurados.
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
La app APIs externas que requieren claves gratuitas. **No incluyas las keys en el repositorio** (usa `local.properties` para mantenerlas privadas).

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


## Cómo correr la app
1. **Clona el proyecto**:
git clone https://tu-repo.com/MiniZoológico.git cd MiniZoológico

2. **Abre en Android Studio**: File > Open > Selecciona la carpeta del proyecto.

3. **Configura API Keys**: Sigue la sección anterior (obligatorio OpenAI; opcional Unsplash).

4. **Sync y Build**:
- File > Sync Project with Gradle Files.
- Build > Clean Project > Rebuild Project.

5. **Run**:
- Conecta un dispositivo o emulador (API 24+).
- Click Run (Shift + F10) o selecciona "Debug".
- Abre la app: Busca "león" o elige "Animal Aleatorio" → Ve a ResultScreen con info.

## Estructura del proyecto
- **app/src/main/java/com/example/proyecto/**:
- **data/**: Modelos (AnimalInfo, UnsplashResponse), Repos (OpenAiRepository, UnsplashRepository), Remote (UnsplashApi).
- **domain/usecase/**: UseCases (GetChatResponseUseCase, GetAnimalImageUseCase).
- **ui/screens/**: HomeScreen, ResultScreen, AboutScreen.
- **ui/viewmodel/**: HomeViewModel (MVVM central).
- **app/build.gradle.kts**: Dependencias (Retrofit, Moshi, Compose, etc.).
- **gradle/libs.versions.toml**: Versiones centralizadas (Kotlin 1.9.10, Compose 1.5.4, etc.).

