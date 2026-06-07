package com.example.supporttickets.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- Color Palette ---
val Primary = Color(0xFF1A237E)          // Deep Indigo
val OnPrimary = Color(0xFFFFFFFF)
val PrimaryContainer = Color(0xFFE8EAF6)
val OnPrimaryContainer = Color(0xFF1A237E)
val Secondary = Color(0xFF283593)
val OnSecondary = Color(0xFFFFFFFF)
val SecondaryContainer = Color(0xFFE8EAF6)
val Tertiary = Color(0xFF5C6BC0)
val Background = Color(0xFFF5F5F5)
val Surface = Color(0xFFFFFFFF)
val SurfaceVariant = Color(0xFFEEEFF9)
val OnBackground = Color(0xFF1A1C1E)
val OnSurface = Color(0xFF1A1C1E)
val Error = Color(0xFFBA1A1A)
val OnError = Color(0xFFFFFFFF)

// Dark variants
val PrimaryDark = Color(0xFFBBBEF5)
val OnPrimaryDark = Color(0xFF1A237E)
val BackgroundDark = Color(0xFF1A1C1E)
val SurfaceDark = Color(0xFF1A1C1E)
val OnSurfaceDark = Color(0xFFE2E2E6)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    tertiary = Tertiary,
    background = Background,
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    onBackground = OnBackground,
    onSurface = OnSurface,
    error = Error,
    onError = OnError
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = Color(0xFF303F9F),
    onPrimaryContainer = Color(0xFFE8EAF6),
    secondary = Color(0xFF9FA8DA),
    tertiary = Color(0xFF9FA8DA),
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = OnSurfaceDark,
    onSurface = OnSurfaceDark
)

@Composable
fun SupportTicketsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            
            window.statusBarColor = colorScheme.primary.toArgb()
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
