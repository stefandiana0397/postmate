package com.postmate.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.postmate.presentation.ui.theme.spacingMedium

@Composable
fun ErrorItem(
    error: String?,
    modifier: Modifier = Modifier,
) {
    if (error != null) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.error)
                    .padding(spacingMedium),
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = error,
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
