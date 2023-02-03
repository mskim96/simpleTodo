package com.msk.simpletodo.presentation.view.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.msk.simpletodo.databinding.FragmentSignInEmailBinding
import com.msk.simpletodo.domain.model.UiEvent
import com.msk.simpletodo.presentation.util.KeyboardAction
import com.msk.simpletodo.presentation.util.PopUpAction
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.view.util.hideTextView
import com.msk.simpletodo.presentation.view.util.showTextView
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

class SignInEmailFragment : Fragment() {

    private var _binding: FragmentSignInEmailBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }
    private val keyBoardAction by lazy { KeyboardAction(requireActivity()) }
    private val popupAction by lazy { PopUpAction() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInEmailBinding.inflate(inflater, container, false)
        val view = binding.root
        with(binding) {
            // show and hide animation when click username editText
            signInPassword.setOnFocusChangeListener { _, hasFocus ->
                showTextView(signInEmailTitle)
                if (hasFocus) hideTextView(signInEmailTitle) else showTextView(signInEmailTitle)
            }
            // show and hide animation when click email editText
            signInEmail.setOnFocusChangeListener { _, _ ->
                val currentFocus = (activity as AuthActivity).currentFocus
                if (currentFocus != null) hideTextView(binding.signInEmailTitle) else showTextView(
                    binding.signInEmailTitle
                )
            }
            // back button event
            backButton.setOnClickListener {
                keyBoardAction.hideKeyboard()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            signInEmailCompleteButton.setOnClickListener {
                val email = signInEmail.text.toString()
                val password = signInPassword.text.toString()
                authViewModel.signInAccount(email, password)
                keyBoardAction.hideKeyboard()
            }

            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    authViewModel.signResult.collectLatest { state ->
                        when (state) {
                            is UiEvent.Success -> {
                                val action =
                                    SignInEmailFragmentDirections.actionSignInEmailFragmentToTodoActivity()
                                this@SignInEmailFragment.findNavController().navigate(action)
                            }
                            is UiEvent.Failed -> popupAction.emptySnackBar(
                                view,
                                state.message
                            )
                        }
                    }
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}