package com.example.specime.screens.result.components

import android.view.ViewGroup
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.example.specime.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

@Composable
fun DataChart(
    result: Map<String, Int>,
) {
    AndroidView(
        modifier = Modifier.size(300.dp),
        factory = { context ->
            PieChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setUsePercentValues(true)
                description.isEnabled = false
                isDrawHoleEnabled = false
                setEntryLabelColor(Color(0xFFF9F9FF).toArgb())
                setEntryLabelTextSize(20f)
                setEntryLabelTypeface(ResourcesCompat.getFont(context, R.font.roboto_bold))
                legend.isEnabled = false
            }
        },
        update = { pieChart ->
            val quadrantOrder = listOf("Influence", "Steadiness", "Conscientiousness", "Dominance")
            val total = result.values.sum().toFloat()

            val percentages = quadrantOrder.associateWith { trait ->
                (result[trait]?.toFloat() ?: 0f) / total * 100
            }.toMutableMap()

            val roundedPercentages = percentages.mapValues { (_, value) -> value.toInt() }.toMutableMap()
            val difference = 100 - roundedPercentages.values.sum()

            if (difference != 0) {
                val lowestKey = roundedPercentages.minByOrNull { it.value }?.key
                if (lowestKey != null) {
                    roundedPercentages[lowestKey] = roundedPercentages[lowestKey]!! + difference
                }
            }

            val entries = roundedPercentages.map { (label, value) ->
                PieEntry(value.toFloat(), label.first().toString())
            }

            val traitColors = mapOf(
                "Dominance" to Color(0xFFFF4D4D),
                "Influence" to Color(0xFFFFD700),
                "Steadiness" to Color(0xFF4CAF50),
                "Conscientiousness" to Color(0xFF2196F3)
            )

            val rotationAngle = calculateEdgeRotationAngle(roundedPercentages)

            val pieDataSet = PieDataSet(entries, "").apply {
                colors = quadrantOrder.mapNotNull { traitColors[it]?.toArgb() }
                valueTextColor = Color(0xFFF9F9FF).toArgb()
                valueTextSize = 20f
                selectionShift = 0f
            }

            val pieData = PieData(pieDataSet).apply {
                setValueFormatter(object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "${value.toInt()}%"
                    }
                })
            }

            pieChart.data = pieData
            pieChart.animateY(1000)
            pieChart.rotationAngle = rotationAngle
            pieChart.setUsePercentValues(true)
            pieChart.invalidate()
        }
    )
}

private fun calculateEdgeRotationAngle(roundedPercentages: Map<String, Int>): Float {
    var cumulativePercentage = 0f
    val total = 100f

    for ((key, value) in roundedPercentages) {
        if (key == "Dominance") {
            val targetStartAngle = cumulativePercentage / total * 360
            return 180f - targetStartAngle
        }
        cumulativePercentage += value
    }
    return 0f
}