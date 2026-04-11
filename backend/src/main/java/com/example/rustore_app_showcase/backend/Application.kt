package com.example.rustore_app_showcase.backend

import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    // Устанавливаем поддержку JSON
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    val repository = BackendRepository()

    routing {
        get("/") {
            call.respondText("RuStore Backend")
        }

        // запрос на все приложения
        get("/apps") {
            val category = call.parameters["category"]
            val apps = if (category != null) {
                repository.getApps().filter { it.category == category }
            } else {
                repository.getApps()
            }
            call.respond(apps)
        }

        // запрос на категории
        get("/categories") {
            call.respond(repository.getCategories())
        }

        // конкретное приложение
        get("/apps/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val app = repository.getApps().find { it.id == id }
            if (app != null) {
                call.respond(app)
            } else {
                call.respondText("App not found", status = io.ktor.http.HttpStatusCode.NotFound)
            }
        }
    }
}

class BackendRepository {
    fun getApps(): List<AppInfo> = listOf(
        AppInfo(
            id = 1,
            title = "VK: мессенджер",
            shortDescription = "Общайтесь, видеозвонки",
            fullDescription = "VK — это не просто мессенджер. Это целая экосистема для общения, развлечений и работы...",
            category = "Социальные сети",
            rating = 4.8,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 12,
            developerName = "VK.com",
            iconUrl = "https://example.com/vk_icon.png", // Потом заменим на реальные ссылки или ресурсы
            screenshots = listOf("https://example.com/s1.png", "https://example.com/s2.png"),
            isInstalled = false,
            size = "150 МБ",
            lastVersion = "2.4.1",
            lastVersionDescription = "Исправлены ошибки и повышена производительность."
        ),
        AppInfo(
            id = 2,
            title = "SkyFlow: Tasks",
            shortDescription = "Планировщик задач",
            fullDescription = "SkyFlow — это современное решение для управления вашими задачами...",
            category = "Продуктивность",
            rating = 4.9,
            ratingCount = 1001,
            ratingPlace = 1,
            ageRating = 4,
            developerName = "SkyTech Creative",
            iconUrl = "https://example.com/skyflow_icon.png",
            screenshots = listOf("https://example.com/s3.png", "https://example.com/s4.png"),
            isInstalled = false,
            size = "42 МБ",
            lastVersion = "1.0.5",
            lastVersionDescription = "Добавлены темная тема и новые виджеты."
        ),
        AppInfo(
            id = 3,
            title = "СберБанк Онлайн",
            shortDescription = "Финансы и платежи",
            fullDescription = "Приложение для управления вашими финансами, оплаты услуг и переводов в любую точку мира.",
            category = "Финансы",
            rating = 4.7,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 6,
            developerName = "Sberbank",
            iconUrl = "https://example.com/sber_icon.png",
            screenshots = listOf("https://example.com/s5.png", "https://example.com/s6.png"),
            isInstalled = false,
            size = "210 МБ",
            lastVersion = "14.8.0",
            lastVersionDescription = "Улучшена безопасность входов и обновлен дизайн иконок."
        ),
        AppInfo(
            id = 4,
            title = "Госуслуги",
            shortDescription = "Государственные услуги",
            fullDescription = "Официальное приложение портала Госуслуг. Документы, штрафы, запись к врачу — всё под рукой.",
            category = "Государственные",
            rating = 4.5,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 0,
            developerName = "Минцифры России",
            iconUrl = "https://example.com/gos_icon.png",
            screenshots = listOf("https://example.com/s7.png", "https://example.com/s8.png"),
            isInstalled = false,
            size = "85 МБ",
            lastVersion = "5.2.1",
            lastVersionDescription = "Добавлен новый раздел с уведомлениями."
        ),
        AppInfo(
            id = 5,
            title = "Tanks Blitz",
            shortDescription = "Легендарные танковые бои",
            fullDescription = "Огромный мир танков теперь в твоем мобильном. Сражайся в формате 7 на 7, прокачивай технику!",
            category = "Игры",
            rating = 4.6,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 12,
            developerName = "Lesta Games",
            iconUrl = "https://example.com/tanks_icon.png",
            screenshots = listOf("https://example.com/s9.png", "https://example.com/s10.png"),
            isInstalled = false,
            size = "2.5 ГБ",
            lastVersion = "10.3.0",
            lastVersionDescription = "Новая ветка техники и праздничный ангар."
        ),
        AppInfo(
            id = 6,
            title = "2ГИС: карты и навигатор",                shortDescription = "Карты, пробки, транспорт",
            fullDescription = "Детальные карты городов с организациями, маршрутами на авто и общественном транспорте.",
            category = "Транспорт",
            rating = 4.8,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 0,
            developerName = "2GIS",
            iconUrl = "https://example.com/2gis_icon.png",
            screenshots = listOf("https://example.com/s11.png", "https://example.com/s12.png"),
            isInstalled = false,
            size = "120 МБ",
            lastVersion = "11.5.0",
            lastVersionDescription = "Добавлены детальные 3D-модели зданий и уточнены графики автобусов."
        ),
        AppInfo(
            id = 7,
            title = "Литрес: читай и слушай",
            shortDescription = "Книги и аудиокниги",
            fullDescription = "Самый большой каталог электронных и аудиокниг. Читайте и слушайте любимые произведения онлайн и офлайн.",
            category = "Развлечения",
            rating = 4.7,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 12,
            developerName = "LitRes",
            iconUrl = "https://example.com/litres_icon.png",
            screenshots = listOf("https://example.com/s13.png", "https://example.com/s14.png"),
            isInstalled = false,
            size = "65 МБ",
            lastVersion = "4.12.1",
            lastVersionDescription = "Улучшен плеер аудиокниг и исправлены ошибки синхронизации."
        ),
        AppInfo(
            id = 8,
            title = "Мой МТС",
            shortDescription = "Личный кабинет",
            fullDescription = "Управляйте своим тарифом, проверяйте остатки гигабайтов и подключайте полезные услуги в одном приложении.",
            category = "Инструменты",
            rating = 4.4,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 0,
            developerName = "MTS",
            iconUrl = "https://example.com/mts_icon.png",
            screenshots = listOf("https://example.com/s15.png", "https://example.com/s16.png"),
            isInstalled = false,
            size = "95 МБ",
            lastVersion = "7.8.0",
            lastVersionDescription = "Обновленный дизайн главного экрана и быстрый доступ к кэшбэку."
        )
    )

    fun getCategories(): List<CategoryInfo> = listOf(
        CategoryInfo(1, "Финансы", 0, 1245, "0xFFD1F6D3"),
        CategoryInfo(2, "Игры", 0, 8420, "0xFFA6D1EF"),
        CategoryInfo(3, "Покупки", 0, 512, "0xFFD7BFA3"),
        CategoryInfo(4, "Инструменты", 0, 3120, "0xFFAAD2D7"),
        CategoryInfo(5, "Образование", 0, 945, "0xFF747BA6"),
        CategoryInfo(6, "Социальные сети", 0, 218, "0xFFEFE8A0"),
        CategoryInfo(7, "Развлечения", 0, 1890, "0xFFEFCBF5")
    )
}