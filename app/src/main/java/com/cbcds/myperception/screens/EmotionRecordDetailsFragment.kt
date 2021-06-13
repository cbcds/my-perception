package com.cbcds.myperception.screens

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.databinding.FragmentEmotionRecordDetailsBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.cbcds.myperception.utils.DateUtils

class EmotionRecordDetailsFragment(private val record: EmotionRecord) : DialogFragment() {
    companion object {
        const val TAG = "emotionRecordDetails"

        private const val LAYOUT_WEIGHT = 0.75
    }

    private lateinit var binding: FragmentEmotionRecordDetailsBinding
    private val viewModel by activityViewModels<DiaryViewModel>()

    override fun onResume() {
        super.onResume()
        setWindowParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmotionRecordDetailsBinding.inflate(inflater, container, false)
        binding.tvEmotionName.text = record.name
        binding.tvDate.text = DateUtils.dateToString(record.date)
        binding.etEmotionDetails.setText(record.details)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEdit.setOnClickListener { btnEdit ->
            binding.btnSave.visibility = Button.VISIBLE
            btnEdit.visibility = Button.INVISIBLE
            binding.etEmotionDetails.isEnabled = true
            binding.etEmotionDetails.requestFocus()
            binding.etEmotionDetails.setSelection(binding.etEmotionDetails.text.toString().length)
            showKeyboard()
        }

        binding.btnSave.setOnClickListener { btnSave ->
            record.details = binding.etEmotionDetails.text.toString()
            viewModel.update(record)

            binding.btnEdit.visibility = Button.VISIBLE
            btnSave.visibility = Button.INVISIBLE
            binding.etEmotionDetails.isEnabled = false
        }

        binding.btnBack.setOnClickListener { dismiss() }
    }

    private fun setWindowParams() {
        Resources.getSystem().displayMetrics.let { metrics ->
            val width = metrics.widthPixels
            val height = (metrics.heightPixels * LAYOUT_WEIGHT).toInt()
            dialog?.window?.setLayout(width, height)
        }
    }

    private fun showKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}