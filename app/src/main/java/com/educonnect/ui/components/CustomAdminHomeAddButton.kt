package com.educonnect.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educonnect.ui.theme.Primary

@Composable
fun CustomAdminHomeAddButton(
    onClick: () -> Unit,
    buttonText: String,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        CustomTextView(text = buttonText)
    }
}