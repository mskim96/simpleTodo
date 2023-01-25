package com.msk.simpletodo.presentation.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.msk.simpletodo.databinding.FragmentSignUpEmailBinding
import com.msk.simpletodo.domain.model.UiEvent
import com.msk.simpletodo.presentation.util.KeyboardAction
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.view.util.hideTextView
import com.msk.simpletodo.presentation.view.util.showTextView
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

class SignUpEmailFragment : Fragment() {

    // binding
    private var _binding: FragmentSignUpEmailBinding? = null
    private val binding get() = _binding!!

    // init viewModel
    private val authViewModel: AuthViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }
    private val keyBoardAction by lazy { KeyboardAction(requireActivity()) }
    private val popupAction by lazy { PopUpAction() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)

        with(binding) {
            /**
             * Related View
             */
            // show and hide animation when click username editText
            signUpPassword.setOnFocusChangeListener { _, hasFocus ->
                showTextView(signUpEmailTitle)
                if (hasFocus) hideTextView(signUpEmailTitle) else showTextView(signUpEmailTitle)
            }
            // show and hide animation when click email editText
            signUpEmail.setOnFocusChangeListener { _, _ ->
                val currentFocus = (activity as AuthActivity).currentFocus
                if (currentFocus != null) hideTextView(binding.signUpEmailTitle) else showTextView(
                    binding.signUpEmailTitle
                )
            }
            // back button event
            backButton.setOnClickListener {
                keyBoardAction.hideKeyboard()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            signUpEmailCompleteButton.setOnClickListener {
                val email = signUpEmail.text.toString()
                val password = signUpPassword.text.toString()
                authViewModel.createAccount(email, password)
                keyBoardAction.hideKeyboard()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                authViewModel.signResult.collectLatest { state ->
                    when (state) {
                        is UiEvent.Success -> navFragment()
                        is UiEvent.Failed -> popupAction.emptySnackBar(binding.root, state.message)
                    }
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun navFragment() {
        (activity as AuthActivity).navFragment(SignUpSendEmailFragment())
    }
}