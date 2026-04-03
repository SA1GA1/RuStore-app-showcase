package com.example.rustore_app_showcase.ui.screens.appdetails

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustore_app_showcase.R
import com.example.rustore_app_showcase.data.models.AppInfo
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

@Composable
fun AppDetailsScreen(
    viewModel: AppDetailsViewModel = viewModel(),
    onBackClick: () -> Unit,
    onInstallClick: () -> Unit
) {
    val app by viewModel.state.collectAsState() // Используй viewModel.app (как мы писали в плане)

    app?.let { currentApp ->
        AppDetailsContent(
            app = currentApp,
            onBackClick = onBackClick,
            onInstallClick = onInstallClick
        )
    }
}
@Composable
fun AppDetailsContent(
    app: AppInfo,
    onBackClick: () -> Unit,
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
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // загрушка вместо иконки приложения
                contentDescription = "AppLogo",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(99.dp)
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
                .height(100.dp)
                .padding(vertical = 16.dp, horizontal = 10.dp),
            color = Color(0xFFEFEFEF),
            shape = RoundedCornerShape(12.dp)
        ) {
            // характеристики приложения
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // рейтинг
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ОЦЕНКА",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(text = "${app.rating}", fontWeight = FontWeight.Bold)
                    Text(
                        text = "${app.ratingCount}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                VerticalDivider(modifier = Modifier.padding(horizontal = 3.dp) ,color = Color.LightGray, thickness = 1.dp)

                // размер
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "РАЗМЕР",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(text = "${app.size} MB", fontWeight = FontWeight.Bold)
                }

                VerticalDivider(modifier = Modifier.padding(horizontal = 3.dp) ,color = Color.LightGray, thickness = 1.dp)

                // возраст
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ВОЗРАСТ",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(text = "${app.rating}+", fontWeight = FontWeight.Bold)
                }

                VerticalDivider(modifier = Modifier.padding(horizontal = 3.dp) ,color = Color.LightGray, thickness = 1.dp)

                // место в категории
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "РЕЙТИНГ",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(text = "№${app.ratingPlace}", fontWeight = FontWeight.Bold)
                    Text(
                        text = app.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
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
            screenshots = emptyList(),
            isInstalled = false,
            size = "167",
            lastVersion = "23.7.3",
            lastVersionDescription = "В это обновлении были добавлены новые функции и фичи"
        )

        AppDetailsContent(
            app = mockApp,
            onBackClick = {},
            onInstallClick = {}
        )
    }
}