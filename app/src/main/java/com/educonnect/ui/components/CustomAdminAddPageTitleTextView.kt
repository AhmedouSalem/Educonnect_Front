package com.educonnect.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.educonnect.R
import com.educonnect.ui.theme.Primary

@Composable
fun CustomAdminAddPageTitleTextView(
    text: String,
) {
    Text(
        text = text,
        fontSize = 20.sp,
        color = Primary,
        fontWeight = FontWeight.Bold,
    )
}