package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionTracker
import com.cbcds.myperception.EmotionsApplication
import com.cbcds.myperception.R
import com.cbcds.myperception.adapters.DiaryAdapter
import com.cbcds.myperception.buildDiarySelectionTracker
import com.cbcds.myperception.databinding.FragmentDiaryBinding
import com.cbcds.myperception.models.DiaryViewModel
import com.cbcds.myperception.models.DiaryViewModelFactory
import com.cbcds.myperception.models.UserViewModel

class DiaryFragment : Fragment() {
    private lateinit var binding: FragmentDiaryBinding
    private lateinit var adapter: DiaryAdapter
    private lateinit var tracker: SelectionTracker<Long>

    private val userViewModel by activityViewModels<UserViewModel>()
    private val viewModel by activityViewModels<DiaryViewModel> {
        DiaryViewModelFactory((activity?.application as EmotionsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryBinding.inflate(inflater, container, false)

        adapter = DiaryAdapter(this)
        binding.diary.adapter = adapter
        binding.diary.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allEmotions.observe(viewLifecycleOwner) {}

        tracker = buildDiarySelectionTracker(binding.diary)
        adapter.tracker = tracker
        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (tracker.selection.size() in 0..1) {
                    activity?.invalidateOptionsMenu()
                }
            }
        })

        viewModel.allRecords.observe(viewLifecycleOwner) {
            viewModel.preprocessListItems()
        }
        viewModel.listItems.observe(viewLifecycleOwner) { listItems ->
            listItems?.let { adapter.submitList(listItems) }
        }

        binding.fabAddEmotion.setOnClickListener {
            findNavController().navigate(R.id.action_tab_diary_to_tab_add_emotion)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_delete).isVisible = tracker.hasSelection()
        menu.findItem(R.id.action_show_dictionary).isVisible = !tracker.hasSelection()
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.delete(tracker.selection.map { it.toInt() }.toList())
                tracker.clearSelection()
                true
            }
            R.id.action_show_dictionary -> {
                findNavController().navigate(R.id.tab_dictionary)
                item.isVisible = false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}