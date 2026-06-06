package com.arithmetix.arithmetix.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.R
import com.arithmetix.arithmetix.model.AppThemeNames
import com.arithmetix.arithmetix.model.KeypadStyles
import com.arithmetix.arithmetix.ui.theme.BlueColorScheme
import com.arithmetix.arithmetix.ui.theme.DarkColorScheme
import com.arithmetix.arithmetix.ui.theme.LightColorScheme

data class ThemeOption(
    val id: String,
    val name: String,
    val backgroundColor: Color
)

val themeOptions = listOf(
    ThemeOption(AppThemeNames.LIGHT.name, "Light", LightColorScheme.background),
    ThemeOption(AppThemeNames.BLUE.name, "Blue", BlueColorScheme.background),
    ThemeOption(AppThemeNames.DARK.name, "Dark", DarkColorScheme.background)
)

@Composable
fun ThemeCard(
    themeOption: ThemeOption,
    currentTheme: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val checkMarkOpacity by animateFloatAsState(
        targetValue = if (currentTheme == themeOption.id) 1f else 0f,
        animationSpec = tween(300),
        label = "Check mark opacity"
    )

    Column(
        modifier = Modifier
            .width(75.dp)
            .height(90.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(themeOption.backgroundColor, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (currentTheme == themeOption.id) {
                Icon(
                    painter = painterResource(R.drawable.baseline_check_circle_24),
                    contentDescription = "Check circle",
                    tint = Color(0xff61c27b),
                    modifier = Modifier
                        .alpha(checkMarkOpacity)
                        .size(30.dp)
                )
            }
        }
        Text(
            text = themeOption.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun KeypadStyleCard(
    text: String,
    iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val checkMarkOpacity by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(300),
        label = "Check mark opacity"
    )


    Box(
        modifier = Modifier
            .width(100.dp)
            .height(280.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = "Phone Keypad Icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(45.dp)
            )
            Text(
                text = text,
                modifier = Modifier.padding(top = 5.dp)
            )
            Icon(
                painter = painterResource(R.drawable.baseline_check_circle_24),
                contentDescription = "Check circle",
                tint = Color(0xff61c27b),
                modifier = Modifier
                    .alpha(checkMarkOpacity)
                    .size(28.dp)
            )
        }
    }
}

@Composable
fun Settings(
    currentTheme: String,
    onThemeChange: (theme: String) -> Unit,
    currentKeypad: String,
    onKeypadChange: (keypad: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = "Theme",
            modifier = Modifier.padding(10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            themeOptions.forEach { theme ->
                ThemeCard(
                    themeOption = theme,
                    currentTheme = currentTheme,
                    onClick = { onThemeChange(theme.id) }
                )
            }
        }
        Text(
            text = "Keypad Style",
            modifier = Modifier.padding(10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeypadStyleCard(
                text = "Calculator",
                iconId = R.drawable.calculator_icon,
                isSelected = currentKeypad == KeypadStyles.CALCULATOR.name,
                onClick = { onKeypadChange(KeypadStyles.CALCULATOR.name) }
            )
            KeypadStyleCard(
                text = "Phone",
                iconId = R.drawable.telephone_icon,
                isSelected = currentKeypad == KeypadStyles.PHONE.name,
                onClick = { onKeypadChange(KeypadStyles.PHONE.name) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    MenuOptionModal(title = "Settings", onClose = {}) {
        Settings(
            currentTheme = "",
            onThemeChange = {},
            currentKeypad = "",
            onKeypadChange = {}
        )
    }
}