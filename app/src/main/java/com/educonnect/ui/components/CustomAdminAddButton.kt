package com.educonnect.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educonnect.ui.theme.Primary
import com.educonnect.ui.theme.Secondary

@Composable
fun CustomAdminAddButton(
    onAddClick: () -> Unit,
    buttonText: String,
) {
    Button(
        onClick = onAddClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Primary)
    ) {
        Text(buttonText, color = Secondary)
    }
}