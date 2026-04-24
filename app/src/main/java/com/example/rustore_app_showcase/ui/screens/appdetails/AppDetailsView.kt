package com.example.rustore_app_showcase.ui.screens.appdetails

import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rustore_app_showcase.shared.AppInfo
import com.example.rustore_app_showcase.ui.screens.components.FeatureItem
import com.example.rustore_app_showcase.ui.screens.components.InfoRow
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppDetailsScreen(
    viewModel: AppDetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onScreenshotClick: (Int) -> Unit,
    onInstallClick: () -> Unit
) {
    val app by viewModel.state.collectAsState()

    if (app == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MainColor)
        }
    } else {
        AppDetailsContent(
            app = app!!,
            onBackClick = onBackClick,
            onScreenshotClick = onScreenshotClick,
            onInstallClick = onInstallClick
        )
    }
}
@Composable
fun AppDetailsContent(
    app: AppInfo,
    onBackClick: () -> Unit,
    onScreenshotClick: (Int) -> Unit,
    onInstallClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {

        // кнопка назад
        IconButton(
            onClick = onBackClick
        ) {
            Icon (
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад"
            )
        }

        // Шапка приложения
        Row (
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = app.iconUrl,
                contentDescription = "AppLogo",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(99.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = app.title,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    fontSize = 22.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // троеточие
                )
                Text(
                    text = app.developerName,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // кнопка установки
        Button(
            onClick = onInstallClick,
            modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Установить",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp), // Сделал отступы как на макете
            color = Color(0xFFF6F6F6),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatureItem(
                    label = "ОЦЕНКА",
                    value = "${app.rating} ★",
                    subValue = "${app.ratingCount}",
                    modifier = Modifier.weight(1f)
                )

                VerticalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))

                FeatureItem(
                    label = "РАЗМЕР",
                    value = "${app.size} MB",
                    modifier = Modifier.weight(1f)
                )

                VerticalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))

                FeatureItem(
                    label = "ВОЗРАСТ",
                    value = "${app.ageRating}+",
                    modifier = Modifier.weight(1f)
                )

                VerticalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                
                FeatureItem(
                    label = "РЕЙТИНГ",
                    value = "№${app.ratingPlace}",
                    subValue = app.category,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // скрины
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(app.screenshots.size) { index ->
                AsyncImage(
                    model = app.screenshots[index],
                    contentDescription = "Скриншот",
                    modifier = Modifier
                        .width(228.dp)
                        .height(390.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onScreenshotClick(index) },
                    contentScale = ContentScale.Crop
                )
            }
        }

        // раздел о приложении
        Text(
            text = "О приложении",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp)
        )
        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            color = Color(0xFFF6F6F6),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = app.fullDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                )
            }
        }

        Text(
            text = "Что нового",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp)
        )
        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            color = Color(0xFFF6F6F6),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Версия ${app.lastVersion}", fontWeight = FontWeight.Bold, color = Color.DarkGray)
                Text(
                    text = app.lastVersionDescription,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text(
            text = "Информация",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp)
        )
        InfoRow("Разработчик", app.developerName)
        InfoRow("Категория", app.category)
        InfoRow("Возрастной ценз", "${app.ageRating}+")
        InfoRow("Размер", "${app.size} MB")
        InfoRow("Версия", app.lastVersion)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDetailsPreview() {
    RuStoreappshowcaseTheme {
        val mockApp = AppInfo (
            id = 1,
            title = "VK мессенджер",
            shortDescription = "Твой главный помошник в коммуникации",
            fullDescription = "Тестовый текст, который был придуман на каленке, потому что мне вообще неважно что я тут напишу, всеравно это тестовые данные, которые буду лежать в одном из каммитов и так никто и никогда не узнает, что я гей",
            category = "Соц. сети",
            rating = 4.7,
            ratingCount = 1000,
            ratingPlace = 1,
            ageRating = 8,
            developerName = "Vk group",
            iconUrl = "",
            screenshots = listOf("1", "2", "3"),
            isInstalled = false,
            size = "167",
            lastVersion = "23.7.3",
            lastVersionDescription = "В это обновлении были добавлены новые функции и фичи"
        )

        AppDetailsContent(
            app = mockApp,
            onBackClick = {},
            onScreenshotClick = {},
            onInstallClick = {}
        )
    }
}