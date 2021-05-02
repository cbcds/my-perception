package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cbcds.myperception.R
import com.cbcds.myperception.databinding.FragmentGlobalStatsBinding
import com.cbcds.myperception.models.UserViewModel

class GlobalStatsFragment : Fragment() {
    private lateinit var binding: FragmentGlobalStatsBinding
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlobalStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.authState.observe(viewLifecycleOwner) { authState ->
            if (authState == UserViewModel.AuthState.UNAUTHENTICATED) {
                findNavController().navigate(R.id.action_tab_global_stats_to_tab_auth)
            }
        }
    }
}