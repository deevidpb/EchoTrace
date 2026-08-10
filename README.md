# 🎵 EchoTrace - Spotify Stats

> Aplicación Full Stack para el análisis y visualización de datos de Spotify en tiempo real.

[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=rws-EchoTrace&metric=alert_status&token=9f67bdba588cfe727446f19a90ac78559ec1da8a)](https://sonarcloud.io/summary/new_code?id=rws-EchoTrace)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=rws-EchoTrace&metric=coverage&token=9f67bdba588cfe727446f19a90ac78559ec1da8a)](https://sonarcloud.io/summary/new_code?id=rws-EchoTrace)

🌐 **Demo en vivo:** [https://echo-trace-eta.vercel.app/](https://echo-trace-eta.vercel.app)

---

## 🚀 Características

* Autenticación segura mediante **OAuth 2.0 con Spotify**.
* Gestión segura de sesiones mediante cookies cross-site con `SameSite=None` y `Secure`.
* Protección mediante **CORS y CSRF**.
* Interfaz reactiva y adaptativa para la consulta de métricas de reproducción.
* Configuración mediante **variables de entorno**, sin valores específicos del entorno en el código.
* Separación de configuración entre entornos **local** y **producción**.
* Automatización de pruebas y validaciones mediante **GitHub Actions**.
* Quality Gate de **SonarCloud** con más de un **90 % de cobertura de código**.

## 🛠️ Tech Stack

* **Backend:** Java 21, Spring Boot 4, Spring Security 7.
* **Frontend:** TypeScript, React, Next.js.
* **Testing:** JUnit 5, Mockito.
* **Calidad:** SonarCloud.
* **CI:** GitHub Actions.
* **Contenedores:** Docker, Docker Compose.
* **Despliegue:** Render (Backend API) + Vercel (Frontend).

---

## ⚙️ Configuración e Instalación Local

### Prerrequisitos
- **Java 21** o superior
- **Node.js 22** y **npm**
- **Docker**

### 1. Aplicación Spotify Dashboard

Para poder utilizar la aplicación es necesario crear una aplicación en el **Spotify Developer Dashboard** y configurar las credenciales de acceso mediante OAuth 2.0.

1. Accede al [Spotify Developer Dashboard](https://developer.spotify.com/dashboard).
2. Inicia sesión con tu cuenta de Spotify.
3. Selecciona **Create app**.
4. Introduce un nombre y una descripción para la aplicación.
5. En **Redirect URI**, añade la URL de callback que utiliza este proyecto. (http://127.0.0.1:8080/login/oauth2/code/spotify)
6. Selecciona los productos/scopes necesarios para la aplicación. 
7. Acepta los términos y condiciones de Spotify.
8. Pulsa **Save**.

Una vez creada la aplicación, Spotify proporcionará las siguientes credenciales:

* **Client ID**
* **Client Secret**

> ⚠️ **Importante:** el `Client Secret` es una credencial privada y no debe publicarse ni incluirse directamente en el código fuente.



### 2. Variables de entorno
Crea un archivo `.env` en la raíz del proyecto  con la siguiente estructura:
```env
FRONTEND_URL=http://127.0.0.1:3000
NEXT_PUBLIC_API_URL=http://127.0.0.1:8080
CLIENT_ID=your_spotify_client_id
CLIENT_SECRET=your_spotify_client_secret
SPRING_PROFILES_ACTIVE=local
```

### 3. Levantar contenedor
```bash
docker compose up --build
```



---

## 🧪 Testing y Calidad

Para ejecutar las pruebas del backend:

```bash
./mvnw verify
```

El proyecto utiliza **JUnit 5** y **Mockito** para las pruebas, junto con **SonarCloud** para el análisis de calidad y cobertura de código.

Los workflows de **GitHub Actions** ejecutan automáticamente las comprobaciones correspondientes al realizar cambios en el proyecto.

---

## 📝 Licencia

Este proyecto está publicado bajo la **PolyForm Noncommercial License**.

Puedes utilizar, estudiar, modificar y distribuir este código siempre que dicho uso sea **no comercial**.

No está permitido utilizar este proyecto, ni partes sustanciales del mismo, con fines comerciales o para obtener un beneficio económico sin autorización previa del autor.

Para más información, consulta el archivo [`LICENSE`](LICENSE) incluido en este repositorio.

