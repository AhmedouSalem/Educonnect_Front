package com.educonnect.ui.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary

@Composable
fun UnderlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onVisibilityToggle: (() -> Unit)? = null,
) {
    val visualTransformation =
        if (isPassword && !passwordVisible) PasswordVisualTransformation()
        else VisualTransformation.None

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Secondary) },
        leadingIcon = {
            Icon(leadingIcon, contentDescription = null, tint = Secondary)
        },
        trailingIcon = if (isPassword && onVisibilityToggle != null) {
            {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = onVisibilityToggle) {
                    Icon(icon, contentDescription = "Toggle password visibility", tint = Primary)
                }
            }
        } else null,
        visualTransformation = visualTransformation,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Secondary,
            unfocusedIndicatorColor = Secondary,
            cursorColor = Secondary,
            focusedLeadingIconColor = Secondary,
            unfocusedLeadingIconColor = Secondary,
            focusedTrailingIconColor = Secondary,
            unfocusedTrailingIconColor = Secondary,
            focusedLabelColor = Secondary,
            unfocusedLabelColor = Secondary,
            focusedTextColor = Secondary,
            unfocusedTextColor = Secondary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        )
    )
}
