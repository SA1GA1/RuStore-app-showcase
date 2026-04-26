# RuStore App Showcase

Учебный проект по курсу VK Education — витрина приложений в стиле RuStore. Пользователь может просматривать каталог приложений, фильтровать по категориям, смотреть детальную страницу приложения со скриншотами и устанавливать APK прямо с устройства.

## Демо

<video src="https://github.com/user-attachments/assets/2bdeadd6-0d15-4906-bc23-8409b85cdb98" controls width="320"></video>

## Архитектура

Проект состоит из трёх модулей:

| Модуль | Описание |
|--------|----------|
| `app` | Android-приложение на Jetpack Compose |
| `backend` | REST API сервер на Ktor (Netty, порт 8080) |
| `shared` | Общие модели данных (`AppInfo`, `CategoryInfo`) |

Backend генерирует иконки и скриншоты приложений динамически — никаких статических изображений не нужно.

## Стек технологий

### Android (`app`)
| Библиотека | Назначение |
|------------|------------|
| Jetpack Compose + Material3 | UI |
| Navigation Compose | Навигация между экранами |
| Koin | Dependency Injection |
| Retrofit 3 + OkHttp 5 | HTTP-клиент |
| Coil | Загрузка изображений |
| kotlinx.serialization | JSON десериализация |

### Backend (`backend`)
| Библиотека | Назначение |
|------------|------------|
| Ktor (Netty) | HTTP-сервер |
| kotlinx.serialization | JSON сериализация |
| Java AWT | Генерация PNG-изображений |

### Общее
- Kotlin 2.3
- Gradle Version Catalog (`libs.versions.toml`)

## API

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| GET | `/apps` | Список всех приложений |
| GET | `/apps?category={name}` | Приложения по категории |
| GET | `/apps/{id}` | Данные конкретного приложения |
| GET | `/categories` | Список категорий с количеством приложений |
| GET | `/icons/{appId}` | Иконка приложения (PNG, генерируется динамически) |
| GET | `/category-icons/{categoryId}` | Иконка категории (PNG, генерируется динамически) |
| GET | `/screenshots/{appId}/{index}` | Скриншот приложения (PNG, генерируется динамически) |

## Как запустить

### Backend

```bash
./gradlew :backend:run
```

Сервер запустится на `http://0.0.0.0:8080`.

### Android-приложение

1. Открыть проект в Android Studio
2. В `NetworkConfig.kt` задать LAN IP своей машины (если запуск на реальном устройстве)
3. Запустить `app` на эмуляторе или устройстве
