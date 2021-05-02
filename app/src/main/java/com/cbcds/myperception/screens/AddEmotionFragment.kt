package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cbcds.myperception.database.EmotionRecord
import com.cbcds.myperception.databinding.FragmentAddEmotionBinding
import com.cbcds.myperception.models.DiaryViewModel

class AddEmotionFragment : Fragment() {
    private lateinit var binding: FragmentAddEmotionBinding
    private val viewModel by activityViewModels<DiaryViewModel>()

  /*  companion object {
        const val REQUEST_KEY = "create_record"
        const val BUNDLE_KEY = "record"
    }*/

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

        binding.btnSaveEmotion.setOnClickListener {
            val newRecord = EmotionRecord(binding.etEmotionName.text.toString())
            viewModel.insert(newRecord)
            findNavController().navigateUp()
        }
    }
}