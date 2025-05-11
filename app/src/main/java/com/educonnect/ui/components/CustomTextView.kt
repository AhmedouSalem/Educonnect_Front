package com.educonnect.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.educonnect.R

@Composable
fun CustomTextView(
    text: String
) {
    Text(text = text, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
}