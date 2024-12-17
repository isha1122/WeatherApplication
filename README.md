# WeatherApplication

A modular, clean-architecture-based Android application that fetches and displays weather information for a city using Jetpack Compose, Hilt for dependency injection, and Retrofit for network operations.

## Features ✨

- **Multi-Module Architecture**: Divided into feature-specific and core modules for scalability and maintainability.
- **Jetpack Compose**: Modern declarative UI design.
- **Dependency Injection**: Powered by Hilt.
- **Coroutines**: Efficient asynchronous operations.
- **Navigation**: Simple and reusable navigation between screens.
- **Unit Testing**: Thoroughly tested ViewModels and UseCases.
- **SOLID Principles**: Follows best software engineering practices.

## Architecture 🏗️

The application follows the **Clean Architecture** pattern and is divided into multiple modules:

1. **Core Modules**:
   - `core-domain`: Contains business logic such as UseCases and repository interfaces.
   - `core-model`: Shared models between layers.
   - `core-network`: Defines the API interface and Retrofit client.

2. **Feature Modules**:
   - `feature-weather`: Handles the display of weather details.
   - `feature-search`: Handles user input for searching weather.

3. **App Module**:
   - Orchestrates navigation and app lifecycle.

---

## Technologies Used 💻

- **Kotlin**
- **Jetpack Compose**
- **Hilt (Dependency Injection)**
- **StateFlow & Coroutines**
- **Retrofit (Networking)**
- **JUnit & Mockk (Testing)**
