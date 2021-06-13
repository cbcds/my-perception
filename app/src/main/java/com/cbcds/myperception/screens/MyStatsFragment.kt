package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cbcds.myperception.databinding.FragmentMyStatsBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip

class MyStatsFragment : Fragment() {
    private lateinit var binding: FragmentMyStatsBinding
    private val viewModel by activityViewModels<DiaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartData = viewModel.getChartData()
        if (chartData != null) {
            val chartModel = AAChartModel()
            chartModel.apply {
                chartType(AAChartType.Spline)
                backgroundColor("#4b2b7f")
                series(
                    arrayOf(
                        AASeriesElement().data(chartData).tooltip(AATooltip().pointFormat(""))
                    )
                )
                chartModel.legendEnabled = false
                xAxisLabelsEnabled = false
                yAxisTitle = ""
            }
            binding.chartView.aa_drawChartWithChartModel(chartModel)
        }
    }
}