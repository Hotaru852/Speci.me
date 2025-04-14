package com.example.specime.screens.home.components

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.specime.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun DataChart(
    result: Map<String, Int>,
) {
    val traits = listOf(
        R.drawable.eagle to "Đại Bàng",
        R.drawable.peacock to "Chim Công",
        R.drawable.dove to "Bồ Câu",
        R.drawable.owl to "Chim Cú"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            factory = { context ->
                BarChart(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setExtraOffsets(0f, 0f, 0f, 0f)
                    description.isEnabled = false
                    legend.isEnabled = false

                    axisLeft.isEnabled = false
                    axisRight.isEnabled = false

                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    xAxis.setDrawLabels(false)

                    setTouchEnabled(false)
                    isHighlightPerTapEnabled = false
                    isHighlightPerDragEnabled = false
                }
            },
            update = { barChart ->
                val quadrantOrder =
                    listOf("Dominance", "Influence", "Steadiness", "Conscientiousness")
                val total = result.values.sum().coerceAtLeast(1) // Avoid division by zero
                val entries = quadrantOrder.mapIndexed { index, trait ->
                    val rawValue = result[trait]?.toFloat() ?: 0f
                    val percentage = rawValue / total * 100f
                    BarEntry(index.toFloat(), percentage)
                }

                val traitColors = mapOf(
                    "Dominance" to Color(0xFFFF4D4D),
                    "Influence" to Color(0xFFFFD700),
                    "Steadiness" to Color(0xFF4CAF50),
                    "Conscientiousness" to Color(0xFF2196F3)
                )

                val barDataSet = BarDataSet(entries, "").apply {
                    colors = quadrantOrder.mapNotNull { traitColors[it]?.toArgb() }
                    valueTextColor = Color.Black.toArgb()
                    valueTextSize = 12f

                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return "${value.toInt()}%"
                        }
                    }
                }

                val barData = BarData(barDataSet).apply {
                    barWidth = 0.6f
                }

                barChart.data = barData
                barChart.invalidate()
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            traits.forEach { (icon, label) ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = label,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}