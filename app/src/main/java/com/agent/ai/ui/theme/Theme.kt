package com.agent.ai.ui.theme

import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GhostCyan,
    onPrimary = Color.Black,
    primaryContainer = GhostPurple.copy(alpha = 0.3f),
    onPrimaryContainer = GhostCyan,
    secondary = GhostPurple,
    onSecondary = Color.White,
    secondaryContainer = GhostPurple.copy(alpha = 0.2f),
    onSecondaryContainer = GhostPurple,
    tertiary = GhostNeon,
    onTertiary = Color.Black,
    tertiaryContainer = GhostNeon.copy(alpha = 0.2f),
    onTertiaryContainer = GhostNeon,
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFB0B0B0),
    outline = Color(0xFF404050),
    error = Color(0xFFFF5252),
    onError = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = LightPrimary.copy(alpha = 0.1f),
    onPrimaryContainer = LightPrimary,
    secondary = LightSecondary,
    onSecondary = Color.White,
    secondaryContainer = LightSecondary.copy(alpha = 0.1f),
    onSecondaryContainer = LightSecondary,
    tertiary = GhostNeon,
    onTertiary = Color.Black,
    tertiaryContainer = GhostNeon.copy(alpha = 0.1f),
    onTertiaryContainer = GhostNeon,
    background = LightBackground,
    onBackground = Color(0xFF1A1A1A),
    surface = LightSurface,
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFE8E8EC),
    onSurfaceVariant = Color(0xFF666666),
    outline = Color(0xFFC0C0C0),
    error = Color(0xFFD32F2F),
    onError = Color.White,
)

@Composable
fun GhostAgentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled to preserve Ghost Agent aesthetic
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
