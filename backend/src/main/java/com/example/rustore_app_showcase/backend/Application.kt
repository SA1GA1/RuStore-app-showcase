package com.example.rustore_app_showcase.backend

import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.shared.CategoryInfo
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import io.ktor.server.http.content.*
import java.awt.Color

fun main() {
    System.setProperty("java.awt.headless", "true")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    val repository = BackendRepository()

    routing {
        get("/") {
            call.respondText("RuStore Backend Test")
        }

        staticResources("/static", "static")

        // запрос на все приложения
        get("/apps") {
            val category = call.parameters["category"]
            val apps = if (category != null) {
                repository.getApps().filter { it.category.equals(category, ignoreCase = true) }
            } else {
                repository.getApps()
            }
            call.respond(apps)
        }

        // запрос на категории
        get("/categories") {
            val apps = repository.getApps()
            val categories = repository.getCategories().map { category ->
                val count = apps.count { it.category == category.title }
                category.copy(appsCount = count)
            }
            call.respond(categories)
        }

        // конкретное приложение
        get("/apps/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val app = repository.getApps().find { it.id == id }
            if (app != null) {
                call.respond(app)
            } else {
                call.respondText("App not found", status = HttpStatusCode.NotFound)
            }
        }

        // иконка категории — генерируется динамически
        get("/category-icons/{categoryId}") {
            val categoryId = call.parameters["categoryId"]?.toIntOrNull()
                ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            val category = allCategories.find { it.id == categoryId }
                ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            val color = parseArgbColor(category.color)
            val letter = category.title.first().toString()
            call.respondBytes(generateCategoryIconPng(letter, color), contentType = ContentType.Image.PNG)
        }

        // иконка приложения — генерируется динамически
        get("/icons/{appId}") {
            val appId = call.parameters["appId"]?.toIntOrNull()
                ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            val app = repository.getApps().find { it.id == appId }
                ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            val category = allCategories.find { it.title == app.category }
            val color = category?.let { parseArgbColor(it.color) } ?: Color(210, 210, 210)
            val letter = app.title.first().toString()
            call.respondBytes(generateIconPng(letter, color), contentType = ContentType.Image.PNG)
        }

        // APK приложения — для demo возвращает 404; положи реальный .apk в resources/apks/{id}.apk
        get("/apk/{appId}") {
            call.respondText(
                "APK for this app is not available in demo mode",
                status = HttpStatusCode.NotFound
            )
        }

        // скриншот приложения — генерируется динамически
        get("/screenshots/{appId}/{index}") {
            val appId = call.parameters["appId"]?.toIntOrNull()
                ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            val index = call.parameters["index"]?.toIntOrNull() ?: 0
            val app = repository.getApps().find { it.id == appId }
                ?: return@get call.respondText("Not found", status = HttpStatusCode.NotFound)
            val category = allCategories.find { it.title == app.category }
            val color = category?.let { parseArgbColor(it.color) } ?: Color(210, 210, 210)
            call.respondBytes(
                generateScreenshotPng(app.title, app.category, color, index),
                contentType = ContentType.Image.PNG
            )
        }
    }
}

class BackendRepository {
    fun getApps(): List<AppInfo> = allApps
    fun getCategories(): List<CategoryInfo> = allCategories
}