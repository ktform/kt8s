package dev.ktform.kt8s.charts.observability

import dev.ktform.kt8s.Chart
import dev.ktform.kt8s.ChartGroup

class GrafanaChart(
  override val version: String,
  override val group: ChartGroup,
) : Chart {
  override fun dependsOnGroups(): List<ChartGroup> {
    TODO("Not yet implemented")
  }

  override fun dependsOnCharts(): List<Chart> {
    TODO("Not yet implemented")
  }
}
