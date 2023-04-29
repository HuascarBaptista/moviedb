# Moviebs

Moviebs es una aplicación de Android desarrollada en Kotlin para mostrar un listado de películas y programas de televisión. La aplicación permite ver el detalle de cada elemento, realizar búsquedas por texto o por categoría y cuenta con tres secciones: Home, Buscador y Buscador por categoría.

## Características

- Base de datos Room para almacenar en caché todas las consultas realizadas a la API, lo que permite al usuario seguir viendo las búsquedas realizadas incluso sin conexión a Internet.
- Detección del tema del dispositivo (claro u oscuro) y ajuste de los colores de la aplicación en consecuencia.
- Función "pull to refresh".
- Soporte en español e inglés, consultando la API en el idioma configurado en el dispositivo del usuario.

## Configuración del API Key

Para consultar la API, es necesario agregar un API key en el archivo `build.gradle`. El valor del API key está en un `buildConfigField` llamado `TMDB_API_KEY_CODE`. Ya tiene un valor por defecto, pero se puede cambiar si se desea.

## Arquitectura y librerías utilizadas

La aplicación se desarrolló siguiendo el patrón MVVM y utilizando UseCases. Para la actualización de las vistas se empleó LiveData y RxJava para programación reactiva. También fueron creados distintos UnitTests.

### Dependencias

A continuación se presentan las principales dependencias utilizadas en el proyecto:

- Android: androidx, appCompat, material, constraintLayout
- Presentation: coreKtx, lifecycle, navigation, recyclerView, cardView, groupie, shimmer, glide, swipeRefreshLayout
- Network: retrofit, okHttp
- Tests: jUnit, mockito, coreTest, mockk
- Data: room
