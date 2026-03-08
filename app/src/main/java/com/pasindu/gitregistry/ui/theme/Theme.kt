package com.pasindu.gitregistry.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    surface = BG,
    onSurface = OnBG,
    background = BG,
    onBackground = OnBG,
    outline = Divider,
    surfaceVariant = SearchBarBG,
    onSurfaceVariant = MedColorText
)
@Composable
fun AppNewTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LScheme,
        typography = Typography,
        content = content
    )
}