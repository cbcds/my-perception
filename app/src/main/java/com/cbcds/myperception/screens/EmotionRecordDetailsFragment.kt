package com.cbcds.myperception.screens

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.databinding.FragmentEmotionRecordDetailsBinding
import com.cbcds.myperception.utils.DateUtils.Companion.format

class EmotionRecordDetailsFragment(private val record: EmotionRecord) : DialogFragment() {
    companion object {
        const val TAG = "emotionRecordDetails"

        private const val LAYOUT_WEIGHT = 0.75
    }

    private lateinit var binding: FragmentEmotionRecordDetailsBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEmotionName.text = record.name
        binding.tvDate.text = record.date.format()
        binding.etEmotionDetails.setText(record.details)
    }

    private fun setWindowParams() {
        Resources.getSystem().displayMetrics.let { metrics ->
            val width = metrics.widthPixels
            val height = (metrics.heightPixels * LAYOUT_WEIGHT).toInt()
            dialog?.window?.setLayout(width, height)
        }
    }
}