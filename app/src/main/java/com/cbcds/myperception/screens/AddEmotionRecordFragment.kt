package com.cbcds.myperception.screens

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.databinding.FragmentAddEmotionBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.cbcds.myperception.utils.DateUtils
import java.util.*

class AddEmotionRecordFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentAddEmotionBinding
    private val viewModel by activityViewModels<DiaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEmotionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(SelectEmotionFragment.REQUEST_KEY) { key, bundle ->
            binding.spinnerEmotionName.text = bundle.getString(key)
        }

        binding.spinnerEmotionName.text = viewModel.allEmotions.value!!.first().name
        binding.spinnerEmotionName.setOnClickListener {
            SelectEmotionFragment().show(parentFragmentManager, SelectEmotionFragment.TAG)
        }

        binding.spinnerSelectDate.text = DateUtils.dateToString(Calendar.getInstance().time)
        binding.spinnerSelectDate.setOnClickListener {
            DatePickerFragment(this).show(parentFragmentManager, DatePickerFragment.TAG)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.spinnerEmotionName.text.toString()
            val date = DateUtils.stringToDate(binding.spinnerSelectDate.text.toString())
            val details = binding.etEmotionDetails.text.toString()
            val level = viewModel.calculateEmotionLevel(name, binding.sbLevel.progress)
            viewModel.insert(EmotionRecord(name, date, details, level))
            findNavController().navigateUp()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.spinnerSelectDate.text = DateUtils.dateToString(year, month + 1, dayOfMonth)
    }
}