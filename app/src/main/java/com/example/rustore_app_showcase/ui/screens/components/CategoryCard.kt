package com.example.rustore_app_showcase.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rustore_app_showcase.data.models.CategoryInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.rustore_app_showcase.R
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme

@Composable
fun CategoryCard (
    category : CategoryInfo,
    onClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .height(160.dp)
            .width(160.dp)
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = category.color
        )
    ) {

        // загрушка вместо иконки категории
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // загрушка вместо иконки приложения
            contentDescription = "AppLogo",
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(70.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = category.title,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,

            )
            Text(
                text = "${category.appsCount} приложений",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 10.sp

                )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    RuStoreappshowcaseTheme {
        CategoryCard(
            category = CategoryInfo(
                id = 1,
                title = "Игры",
                color = Color(0xFFE3F2FD),
                icon = 0, // Заглушка
                appsCount = 10,
                apps = emptyList()
            ),
            onClick = {}
        )
    }
}
