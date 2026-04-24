package com.example.rustore_app_showcase.ui.screens.components

import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.rustore_app_showcase.shared.CategoryInfo
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import com.example.rustore_app_showcase.ui.utils.toComposeColor

@Composable
fun CategoryCard (
    category : CategoryInfo,
    onClick : () -> Unit
) {

    Card(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = category.color.toComposeColor()
        )
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            AsyncImage(
                model = category.iconUrl,
                contentDescription = "Иконка категории",
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(80.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = category.title,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 20.sp

            )
            Text(
                text = "${category.appsCount} приложений",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 12.sp
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
                color = "0xFFE3F2FD",
                icon = 0, // Заглушка
                appsCount = 10,
                description = "Описание категории"
            ),
            onClick = {}
        )
    }
}
