package com.msk.simpletodo.presentation.view.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentSignUpPwBinding
import com.msk.simpletodo.presentation.util.SignUpUser
import com.msk.simpletodo.presentation.util.validate
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import kotlinx.coroutines.launch

class SignUpPwFragment : Fragment() {

    private var _binding: FragmentSignUpPwBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpPwBinding.inflate(inflater, container, false)

        val signUpPasswordCompleteButton = binding.signUpPasswordCompleteButton
        val signUpPasswordLayout = binding.signUpPasswordLayout
        val signUpPassword = binding.signUpPassword

        val imm = // for soft keyboard
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // set button disabled
        signUpPasswordCompleteButton.isEnabled = false

        lifecycleScope.launchWhenCreated {
            launch { // for auto open soft keyboard
                signUpPasswordCompleteButton.visibility = View.VISIBLE
                signUpPassword.requestFocus() // send focus
                imm.showSoftInput(signUpPassword, 0) // open soft keyboard
            }
        }

        // When editText changed
        signUpPassword.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = SignUpUser.Password.validate(it.toString())
            if (!validate) {
                signUpPasswordCompleteButton.isEnabled = false
                signUpPasswordLayout.error = "Please enter a password of at least 6 characters."
            } else {
                signUpPasswordCompleteButton.isEnabled = true
                signUpPasswordLayout.helperText = "Password format is correct!"
            }
        }

        signUpPasswordCompleteButton.setOnClickListener {
            val password = signUpPassword.text.toString().trim()
            sharedViewModel.putUserPassword(SignUpUser.Password, password)
            (activity as AuthActivity).navFragment(SignUpUsernameFragment())
        }


        /**
         * Animations
         */

        AnimationUtils.loadAnimation(requireContext(), R.anim.text_animation_500).also {
            binding.signUpPasswordTitle.startAnimation(it)
        }

        AnimationUtils.loadAnimation(requireContext(), R.anim.item_fade_up_700).also {
            signUpPasswordCompleteButton.startAnimation(it)
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}