package com.educonnect.ui.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.educonnect.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ResponsiveLoginScreen(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.app_background),
                contentScale = ContentScale.Crop
            )
    ) {
        if (maxWidth < 600.dp) {
            LoginScreen()
        } else {
            TabletLoginLayout()
        }
    }
}
