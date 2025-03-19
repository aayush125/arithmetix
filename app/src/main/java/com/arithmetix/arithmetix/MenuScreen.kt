package com.arithmetix.arithmetix

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arithmetix.arithmetix.ui.components.UIButton
import kotlinx.coroutines.launch

@Composable
fun Menu() {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scrollScope = rememberCoroutineScope()
    val interactionSourceLeft = remember { MutableInteractionSource() }
    val interactionSourceRight = remember { MutableInteractionSource() }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .size(250.dp)
//                .border(2.dp, Color.Black)
            ) {
                Row(
                    modifier = Modifier.weight(0.2f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_left_24),
                        contentDescription = "Menu left arrow",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .weight(0.1f)
                            .clickable(
                                interactionSource = interactionSourceLeft,
                                indication = null,
                                onClick = {
                                    scrollScope.launch {
                                        pagerState.animateScrollToPage(
                                            pagerState.currentPage - 1
                                        )
                                    }
                                }
                            )
                    )
                    Box(
                        modifier = Modifier.weight(0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        HorizontalPager(
                            state = pagerState,
                        ) { page ->
                            Text(
                                text = if (page == 0) "Time Attack" else "Endless",
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                fontSize = 24.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_right_24),
                        contentDescription = "Menu right arrow",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .weight(0.1f)
                            .clickable(
                                interactionSource = interactionSourceRight,
                                indication = null,
                                onClick = {
                                    scrollScope.launch {
                                        pagerState.animateScrollToPage(
                                            pagerState.currentPage + 1
                                        )
                                    }
                                }
                            )
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        UIButton(
                            onClick = {

                            },
                            buttonText = "Standard",
                            modifier = Modifier.width(250.dp)
                        )
                        UIButton(
                            onClick = {  },
                            buttonText = "Hard",
                            modifier = Modifier.width(250.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
//    Menu()
}