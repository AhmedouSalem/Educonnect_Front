package com.educonnect.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.ui.Alignment
import com.educonnect.ui.theme.OnPrimaryOpacity

@Composable
fun CardBackgroundWrapper(
    modifier: Modifier = Modifier,
    maxWidth: Dp = 600.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .widthIn(max = maxWidth)
            .clip(RoundedCornerShape(16.dp))
            .background(OnPrimaryOpacity.copy(alpha = 0.2f))
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}
