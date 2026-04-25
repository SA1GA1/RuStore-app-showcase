package com.example.rustore_app_showcase.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rustore_app_showcase.R
import com.example.rustore_app_showcase.ui.theme.MainColor
import org.koin.androidx.compose.koinViewModel


@Composable
fun OnboardingScreen(
    onContinueClick: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column (
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.rustorelogo),
                contentDescription = "RuStore Logo",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Добро пожаловать в RuStore",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Магазин с гарантированно безопасными приложениями для вас и ваших близких",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center

            )

            Spacer(modifier = Modifier.height(100.dp))
        }


        Button(
            onClick = {
                viewModel.onOnboardingContinueClick()
                onContinueClick()
                      },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(45.dp)
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor
            )
            ) {
            Text(
                text = "Перейти к витрине",
                fontSize = 18.sp
                )
        }

    }
}