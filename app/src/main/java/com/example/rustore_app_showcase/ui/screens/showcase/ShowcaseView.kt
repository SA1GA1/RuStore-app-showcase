package com.example.rustore_app_showcase.ui.screens.showcase

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rustore_app_showcase.ui.screens.components.AppCard

@Composable
fun ShowcaseScreen(
    viewModel: ShowcaseViewModel = viewModel(),
    onAppClick: (Int) -> Unit
) {
    val apps by viewModel.state.collectAsState()

    LazyColumn{
        item {
            Text(
                text = "RuStore",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(apps) { app ->
            AppCard(app, onClick = { onAppClick(app.id) }, onInstallClick = {})
        }
    }
}
