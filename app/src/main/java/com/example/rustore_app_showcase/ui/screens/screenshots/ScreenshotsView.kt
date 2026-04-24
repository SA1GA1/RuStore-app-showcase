package com.example.rustore_app_showcase.ui.screens.screenshots

import androidx.compose.foundation.background
import coil.compose.AsyncImage
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rustore_app_showcase.ui.theme.MainColor
import com.example.rustore_app_showcase.ui.theme.RuStoreappshowcaseTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@Composable
fun ScreenshotsScreen(
    appID: Int,
    initialIndex: Int,
    onBackClick: () -> Unit,
    viewModel: ScreenshotsViewModel = koinViewModel()
) {
    val screenshots by viewModel.screenshots.collectAsState()

    ScreenshotsContent(
        screenshots = screenshots,
        initialIndex = initialIndex,
        onBackClick = onBackClick
    )
}
@Composable
fun ScreenshotsContent(
    screenshots: List<String>,
    initialIndex: Int,
    onBackClick: () -> Unit
) {

    if (screenshots.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White))
        return
    }

    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { screenshots.size }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 52.dp),
            pageSpacing = 24.dp,
            verticalAlignment = Alignment.CenterVertically
        ) { pageIndex ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 100.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction
                                ).absoluteValue
                        alpha = 1f - (pageOffset * 0.3f)
                        scaleY = 1f - (pageOffset * 0.1f)
                    }
            ) {
                AsyncImage(
                    model = screenshots[pageIndex],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .border(
                            width = 2.dp,
                            color = Color.LightGray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Закрыть",
                tint = MainColor
            )
        }
    }
}

@Preview
@Composable
fun ScrenshootsPreview() {
    RuStoreappshowcaseTheme {
        ScreenshotsContent(
            screenshots = listOf("1", "2", "3"),
            initialIndex = 0,
            onBackClick = {}
        )
    }
}