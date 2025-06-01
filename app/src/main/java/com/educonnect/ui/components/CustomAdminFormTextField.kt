package com.educonnect.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.educonnect.ui.theme.Secondary

@Composable
fun CustomAdminFormTextField(
    value: String,
    label: String,
    color: Color = Secondary,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("$label *", color = color) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = color,
            focusedIndicatorColor = color,
            unfocusedIndicatorColor = color,
            focusedTextColor = color,
            unfocusedTextColor = color,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}