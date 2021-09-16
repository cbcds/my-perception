package com.cbcds.myperception.screens

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.cbcds.myperception.R
import com.cbcds.myperception.databinding.FragmentSelectEmotionBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.google.android.material.chip.Chip

class SelectEmotionFragment : DialogFragment() {
    companion object {
        const val TAG = "selectEmotion"
        const val REQUEST_KEY = "EmotionName"
        private const val LAYOUT_WEIGHT = 0.75
    }

    private lateinit var binding: FragmentSelectEmotionBinding
    private val diaryViewModel by activityViewModels<DiaryViewModel>()

    override fun onResume() {
        super.onResume()
        setWindowParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectEmotionBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inflateEmotionsViews()

        binding.btnOk.setOnClickListener {
            val result = (binding.groupEmotions.children.first { (it as Chip).isChecked } as Chip)
                .text.toString()
            setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to result))
            dismiss()
        }
    }

    private fun inflateEmotionsViews() {
        for (emotion in diaryViewModel.allEmotions.value!!) {
            val chip = layoutInflater.inflate(
                R.layout.chip_emotion,
                binding.groupEmotions,
                false
            ) as Chip
            chip.text = emotion.name
            binding.groupEmotions.addView(chip)
        }
        (binding.groupEmotions[0] as Chip).isChecked = true
    }

    private fun setWindowParams() {
        Resources.getSystem().displayMetrics.let { metrics ->
            val width = metrics.widthPixels
            val height = (metrics.heightPixels * LAYOUT_WEIGHT).toInt()
            dialog?.window?.setLayout(width, height)
        }
    }
}