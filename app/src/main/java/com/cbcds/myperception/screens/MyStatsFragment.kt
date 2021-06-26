package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cbcds.myperception.EmotionsApplication
import com.cbcds.myperception.databinding.FragmentMyStatsBinding
import com.cbcds.myperception.models.MyStatsViewModel
import com.cbcds.myperception.models.MyStatsViewModelFactory
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyStatsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentMyStatsBinding
    private val viewModel by activityViewModels<MyStatsViewModel> {
        MyStatsViewModelFactory((activity?.application as EmotionsApplication).repository)
    }

    private val chartModel = AAChartModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initChartModel()
    }

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

        updateChartData()

        viewModel.data.observe(viewLifecycleOwner) {
            updateChartData()
        }

        binding.spinnerTimePeriod.onItemSelectedListener = this
    }

    private fun updateChartData() {
        GlobalScope.launch(Dispatchers.Main) {
            val chartData: Array<Any>? = viewModel.getChartDataAsync().await()
            if (chartData != null) {
                chartModel.series(
                    arrayOf(
                        AASeriesElement().data(chartData).tooltip(AATooltip().pointFormat(""))
                    )
                )
                setChartVisibility(View.VISIBLE)
            } else {
                setChartVisibility(View.GONE)
            }
        }
    }

    private fun setChartVisibility(visibility: Int) {
        binding.chartView.visibility = visibility

        when (visibility) {
            View.VISIBLE -> {
                binding.chartView.aa_drawChartWithChartModel(chartModel)
                binding.tvNoData.visibility = View.GONE
            }
            View.GONE -> {
                binding.tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun initChartModel() {
        chartModel.apply {
            chartType(AAChartType.Spline)
            backgroundColor("#4b2b7f")
            chartModel.legendEnabled = false
            xAxisLabelsEnabled = false
            yAxisTitle = ""
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.timePeriod =
            if (position == 0) MyStatsViewModel.TimePeriod.WEEK else MyStatsViewModel.TimePeriod.MONTH
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}