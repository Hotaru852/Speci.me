package com.example.specime.screens.contacts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomTabLayout(
    tabTitles: List<String>,
    tabContents: List<@Composable () -> Unit>,
    indicatorColor: Color,
    indicatorHeight: Int
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    require(tabTitles.size == tabContents.size) {}

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            indicator = { tabPositions ->
                Row(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .height(indicatorHeight.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .background(color = indicatorColor)
                    )
                }
            },
            divider = {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.surfaceDim,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        tabContents[selectedTabIndex]()
    }
}