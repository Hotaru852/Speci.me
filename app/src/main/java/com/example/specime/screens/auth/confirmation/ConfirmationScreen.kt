package com.example.specime.screens.auth.confirmation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.example.specime.R
import com.example.specime.screens.auth.components.FlexibleButton

@Composable
fun ConfirmationScreen(
    navController: NavController,
    title: String,
    message: String? = null,
    buttonText: String,
    route: String
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
    val progress by animateLottieCompositionAsState(composition, iterations = 1)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            title,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        if (message != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                message,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.padding(bottom = 30.dp)
        ) {
            FlexibleButton(
                text = buttonText,
                width = 320,
                height = 45,
                onClick = {
                    navController.navigate(route)
                },
                rounded = 40,
            )
        }
    }
}