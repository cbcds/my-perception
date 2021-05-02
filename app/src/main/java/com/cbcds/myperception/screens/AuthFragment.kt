package com.cbcds.myperception.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.cbcds.myperception.R
import com.cbcds.myperception.databinding.FragmentAuthBinding
import com.cbcds.myperception.models.UserViewModel

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private val userViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener { (activity as MainActivity).launchSignInFlow() }
        userViewModel.authState.observe(viewLifecycleOwner) { authState ->
            if (authState == UserViewModel.AuthState.AUTHENTICATED) {
                findNavController().navigate(R.id.action_tab_auth_to_tab_global_stats)
            }
        }
    }
}