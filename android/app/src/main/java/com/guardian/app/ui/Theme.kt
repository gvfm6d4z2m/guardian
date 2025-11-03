
package com.guardian.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006874),
    onPrimary = Color.White,
    secondary = Color(0xFF4A6265),
    onSecondary = Color.White,
    background = Color(0xFFFBFCFC),
    onBackground = Color(0xFF191C1D),
    surface = Color(0xFFFBFCFC),
    onSurface = Color(0xFF191C1D),
)

@Composable
fun GuardianTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
