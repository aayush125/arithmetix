package com.example.numsprint.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Keypad(
    onKeyPressed: (String) -> Unit,
    reversed: Boolean,
    onBackspacePressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val digits: Array<IntArray> = arrayOf(
        intArrayOf(7, 8, 9),
        intArrayOf(4, 5, 6),
        intArrayOf(1, 2, 3),
    )

    if (reversed) digits.reverse()

    val keySize: Dp = 70.dp
    val keysPadding: Dp = 15.dp

    Row() {
        Column(modifier = modifier, horizontalAlignment = Alignment.End) {
            digits.forEach { row ->
                Row {
                    row.forEach { digit ->
                        KeypadKey(
                            digit.toString(),
                            fontSize = 50.sp,
                            onClick = { onKeyPressed(digit.toString()) },
                            Modifier
                                .padding(keysPadding)
                                .size(keySize)
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                KeypadKey(
                    "-", fontSize = 50.sp, onClick = { onKeyPressed("-") },
                    Modifier
                        .padding(keysPadding)
                        .size(keySize)
                )
                KeypadKey(
                    "0", fontSize = 50.sp, onClick = { onKeyPressed("0") },
                    Modifier
                        .padding(keysPadding)
                        .size(keySize)
                )
//                KeypadKey(
//                    ".", fontSize = 50.sp, onClick = { onKeyPressed(".") },
//                    Modifier
//                        .padding(keysPadding)
//                        .size(keySize)
//                )
                BackspaceKey(
                    Modifier
                        .padding(keysPadding)
                        .size(keySize), onClick = { onBackspacePressed() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeypadPreview() {
    Keypad({}, false, {})
}
