package it.esercizi.ninethegame.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import it.esercizi.ninethegame.R

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = BlueBack

)

private val LightColorPalette = lightColors(
    primary = BtnColor,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = BlueBack

)

@Composable
fun MyAppTheme(
    backgroundChoice: Int,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    Image(
        painter = painterResource(id = when (backgroundChoice) {
            1 -> {
                R.drawable.gradient_backgrounds_b1
            }
            2 -> {
                R.drawable.gradient_backgrounds_b2
            }
            3 -> {
                R.drawable.gradient_backgrounds_b3
            }
            else -> {
                R.drawable.gradient_backgrounds_b1
            }
        }
        ),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds)
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content

    )

}