package com.educonnect.ui.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.educonnect.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ResponsiveLoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: (String) -> Unit
) {
    val context = LocalContext.current

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.app_background),
                contentScale = ContentScale.Crop
            )
    ) {
        if (maxWidth < 600.dp) {
            LoginScreen(
                context = context,
                onLoginSuccess = onLoginSuccess
            )
        } else {
            TabletLoginLayout(
                context = context,
                onLoginSuccess = onLoginSuccess
            )
        }
    }
}
