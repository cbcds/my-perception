package com.cbcds.myperception.screens

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cbcds.myperception.R
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.databinding.FragmentAddEmotionBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.cbcds.myperception.utils.DateUtils
import com.cbcds.myperception.utils.DateUtils.Companion.format
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

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.emotions_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerEmotionName.adapter = adapter
        }

        binding.spinnerSelectDate.text = Calendar.getInstance().format()
        binding.spinnerSelectDate.setOnClickListener {
            DatePickerFragment(this).show(parentFragmentManager, DatePickerFragment.TAG)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.spinnerEmotionName.selectedItem.toString()
            val date = DateUtils.stringToDate(binding.spinnerSelectDate.text.toString())
            val details = binding.etEmotionDetails.text.toString()
            viewModel.insert(EmotionRecord(name, date, details))
            findNavController().navigateUp()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        binding.spinnerSelectDate.text = DateUtils.dateToString(year, month, dayOfMonth)
    }
}