package com.msk.simpletodo.presentation.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.databinding.FragmentSignUpSendEmailBinding
import com.msk.simpletodo.domain.model.UiEvent
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

class SignUpSendEmailFragment : Fragment() {

    private var _binding: FragmentSignUpSendEmailBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpSendEmailBinding.inflate(inflater, container, false)
        var userEmailAddress: String?

        // back button event
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            authViewModel.signResult.collectLatest { state ->
                userEmailAddress = when (state) {
                    is UiEvent.Success -> state.data
                    is UiEvent.Failed -> null
                }
                binding.signUpEmailTitle.text = userEmailAddress
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}