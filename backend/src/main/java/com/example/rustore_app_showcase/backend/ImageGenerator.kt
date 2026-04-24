package com.example.rustore_app_showcase.backend

import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun generateIconPng(letter: String, bgColor: Color): ByteArray {
    val size = 200
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

    g.color = bgColor
    g.fillRoundRect(0, 0, size, size, 60, 60)

    g.color = Color(50, 50, 50)
    g.font = Font(Font.SANS_SERIF, Font.BOLD, 90)
    val fm = g.fontMetrics
    val x = (size - fm.stringWidth(letter)) / 2
    val y = (size - fm.height) / 2 + fm.ascent
    g.drawString(letter, x, y)

    g.dispose()

    val out = ByteArrayOutputStream()
    ImageIO.write(image, "PNG", out)
    return out.toByteArray()
}

fun generateScreenshotPng(title: String, category: String, bgColor: Color, index: Int): ByteArray {
    val width = 390
    val height = 844
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

    g.color = bgColor
    g.fillRect(0, 0, width, height)

    // Тонкие полосы для текстуры фона
    g.color = Color(0, 0, 0, 18)
    for (y in 0 until height step 60) {
        if ((y / 60) % 2 == 0) g.fillRect(0, y, width, 30)
    }

    // Карточка по центру
    val cardW = 320
    val cardH = 160
    val cardX = (width - cardW) / 2
    val cardY = (height - cardH) / 2 - 20
    g.color = Color(255, 255, 255, 180)
    g.fillRoundRect(cardX, cardY, cardW, cardH, 24, 24)

    // Название приложения
    g.color = Color(40, 40, 40)
    g.font = Font(Font.SANS_SERIF, Font.BOLD, 26)
    var fm = g.fontMetrics
    val titleStr = if (fm.stringWidth(title) > cardW - 32) title.take(18) + "…" else title
    val titleX = cardX + (cardW - fm.stringWidth(titleStr)) / 2
    g.drawString(titleStr, titleX, cardY + 60)

    // Категория
    g.color = Color(90, 90, 90)
    g.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
    fm = g.fontMetrics
    val catX = cardX + (cardW - fm.stringWidth(category)) / 2
    g.drawString(category, catX, cardY + 100)

    // Номер скриншота
    g.color = Color(0, 0, 0, 90)
    g.fillRoundRect(width - 72, height - 48, 52, 28, 14, 14)
    g.color = Color(255, 255, 255)
    g.font = Font(Font.SANS_SERIF, Font.BOLD, 14)
    fm = g.fontMetrics
    val numStr = "${index + 1}"
    g.drawString(numStr, width - 72 + (52 - fm.stringWidth(numStr)) / 2, height - 28)

    g.dispose()

    val out = ByteArrayOutputStream()
    ImageIO.write(image, "PNG", out)
    return out.toByteArray()
}

fun generateCategoryIconPng(letter: String, bgColor: Color): ByteArray {
    val size = 200
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

    // Фон — чуть темнее цвета категории
    val darker = bgColor.darker()
    g.color = darker
    g.fillRoundRect(0, 0, size, size, 48, 48)

    // Внутренний светлый прямоугольник
    g.color = bgColor
    g.fillRoundRect(12, 12, size - 24, size - 24, 36, 36)

    // Буква
    g.color = Color(40, 40, 40)
    g.font = Font(Font.SANS_SERIF, Font.BOLD, 88)
    val fm = g.fontMetrics
    val x = (size - fm.stringWidth(letter)) / 2
    val y = (size - fm.height) / 2 + fm.ascent
    g.drawString(letter, x, y)

    g.dispose()

    val out = ByteArrayOutputStream()
    ImageIO.write(image, "PNG", out)
    return out.toByteArray()
}

fun parseArgbColor(hex: String): Color {
    val value = hex.removePrefix("0x").toLong(16)
    val r = ((value shr 16) and 0xFF).toInt()
    val g = ((value shr 8) and 0xFF).toInt()
    val b = (value and 0xFF).toInt()
    return Color(r, g, b)
}
