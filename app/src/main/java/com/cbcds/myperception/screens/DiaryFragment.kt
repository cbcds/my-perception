package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cbcds.myperception.EmotionsApplication
import com.cbcds.myperception.R
import com.cbcds.myperception.adapters.DiaryAdapter
import com.cbcds.myperception.databinding.FragmentDiaryBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.cbcds.myperception.models.DiaryViewModelFactory
import com.cbcds.myperception.models.UserViewModel

class DiaryFragment : Fragment() {
    private lateinit var binding: FragmentDiaryBinding
    private lateinit var adapter: DiaryAdapter

    private val userViewModel by activityViewModels<UserViewModel>()
    private val viewModel by activityViewModels<DiaryViewModel> {
        DiaryViewModelFactory((activity?.application as EmotionsApplication).repository)
    }

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(AddEmotionFragment.REQUEST_KEY) { _, bundle ->
            val newRecord = bundle.get(AddEmotionFragment.BUNDLE_KEY) as EmotionRecord
            viewModel.insert(newRecord)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        adapter = DiaryAdapter()
        binding.diary.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allRecords.observe(viewLifecycleOwner) { records ->
            records?.let { adapter.submitList(it) }
        }

        binding.fabAddEmotion.setOnClickListener {
            findNavController().navigate(R.id.action_tab_diary_to_tab_add_emotion)
        }
    }
}