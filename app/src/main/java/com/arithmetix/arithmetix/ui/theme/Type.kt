package com.arithmetix.arithmetix.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

//val fontName = GoogleFont("Kantumruy Pro")
//val fontName = GoogleFont("Recursive")
//val fontName = GoogleFont("Luckiest Guy")
//val fontName = GoogleFont("Galindo")
val fontName = GoogleFont("Open Sans")
val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.ExtraBold)
)

val fontName_keys = GoogleFont("Luckiest Guy")
val fontFamily_keys = FontFamily(
    Font(googleFont = fontName_keys, fontProvider = provider)
)

//val fontName_problem = GoogleFont("Alegreya Sans")
val fontName_problem = GoogleFont("Luckiest Guy")
val fontFamily_problem = FontFamily(
    Font(googleFont = fontName_problem, fontProvider = provider)
)

val fontName_timer = GoogleFont("Open Sans")
val fontFamily_timer = FontFamily(
    Font(googleFont = fontName_timer, fontProvider = provider)
)

val fontName_score = GoogleFont("Montserrat")
//val fontName_score = GoogleFont("Recursive")
val fontFamily_score = FontFamily(
    Font(googleFont = fontName_score, fontProvider = provider, weight = FontWeight.W400),
    Font(googleFont = fontName_score, fontProvider = provider, weight = FontWeight.W500),
    Font(googleFont = fontName_score, fontProvider = provider, weight = FontWeight.W600),
    Font(googleFont = fontName_score, fontProvider = provider, weight = FontWeight.W700),
    Font(googleFont = fontName_score, fontProvider = provider, weight = FontWeight.W800)
)

val TomorrowTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)