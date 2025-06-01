package com.educonnect.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.scrollbar(scrollState: ScrollState): Modifier = this.then(
    Modifier.drawBehind {
        val proportion = size.width / scrollState.maxValue.toFloat()
        val thumbWidth = size.width * proportion
        val thumbOffset = scrollState.value.toFloat() * proportion

        drawRect(
            color = Color.Gray.copy(alpha = 0.4f),
            topLeft = Offset(thumbOffset, size.height - 4.dp.toPx()),
            size = androidx.compose.ui.geometry.Size(thumbWidth, 4.dp.toPx())
        )
    }
)
