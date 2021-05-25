package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cbcds.myperception.EmotionsApplication
import com.cbcds.myperception.adapters.DictionaryAdapter
import com.cbcds.myperception.databinding.FragmentDictionaryBinding
import com.cbcds.myperception.models.DictionaryViewModel
import com.cbcds.myperception.models.DictionaryViewModelFactory

class DictionaryFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryBinding
    private lateinit var adapter: DictionaryAdapter

    private val viewModel by activityViewModels<DictionaryViewModel> {
        DictionaryViewModelFactory((activity?.application as EmotionsApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false)

        adapter = DictionaryAdapter(requireContext())
        binding.dictionary.adapter = adapter
        binding.dictionary.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allEmotions.observe(viewLifecycleOwner) { emotionItems ->
            adapter.submitList(emotionItems)
        }
    }
}