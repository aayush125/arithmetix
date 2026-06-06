package com.arithmetix.arithmetix.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppInfo() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Arithmetix"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AppInfoPreview() {
    MenuOptionModal(title = "Arithmetix", onClose = {}) {
        AppInfo()
    }
}