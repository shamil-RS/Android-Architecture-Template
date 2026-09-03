package com.architecturetemplate.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun PreviewWrapper(isDarkMode: Boolean, fontScale: Float, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalDensity provides Density(density = LocalDensity.current.density, fontScale = fontScale)
    ) {
        AppTheme(isDarkMode) {
            content()
        }
    }
}