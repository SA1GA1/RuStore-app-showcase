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
import io.ktor.server.http.content.*

fun main() {
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
                call.respondText("App not found", status = io.ktor.http.HttpStatusCode.NotFound)
            }
        }
    }
}

class BackendRepository {
    fun getApps(): List<AppInfo> = allApps
    fun getCategories(): List<CategoryInfo> = allCategories
}