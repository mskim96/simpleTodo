package com.msk.simpletodo.presentation.view.auth.signUp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.databinding.FragmentSignUpEmailBinding
import com.msk.simpletodo.presentation.util.SignUpUser
import com.msk.simpletodo.presentation.util.validate
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpEmailFragment : Fragment() {

    // binding
    private var _binding: FragmentSignUpEmailBinding? = null
    private val binding get() = _binding!!

    // init viewModel
    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)

        // screen element
        val signUpEmailCompleteButton = binding.signUpEmailCompleteButton
        val signUpEmailLayout = binding.signUpEmailLayout
        val signUpEmail = binding.signUpEmail
        val textClearButton = binding.textClearButton

        val imm = // for soft keyboard
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // set button disabled
        signUpEmailCompleteButton.isEnabled = false
        textClearButton.visibility = View.GONE

        textClearButton.setOnClickListener {
            signUpEmail.text = null // text clear
        }

        /**
         * If don't use launchWhenStarted, the soft keyboard won't open
         */
        lifecycleScope.launchWhenCreated {
            launch { // for auto open soft keyboard
                delay(300)
                signUpEmailCompleteButton.visibility = View.VISIBLE
                signUpEmail.requestFocus() // send focus
                imm.showSoftInput(signUpEmail, 0) // open soft keyboard
            }
        }

        // When editText changed
        signUpEmail.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = SignUpUser.Email.validate(it.toString())
            if (!validate) {
                signUpEmailCompleteButton.isEnabled = false
                signUpEmailLayout.error = "Please enter an email that matches the format"
            } else {
                signUpEmailCompleteButton.isEnabled = true
                signUpEmailLayout.helperText = "Email format is correct!"
            }

            // show textClearButton
            if (signUpEmail.text.isNullOrBlank()) {
                textClearButton.visibility = View.GONE
            } else {
                textClearButton.visibility = View.VISIBLE
            }
        }

        signUpEmailCompleteButton.setOnClickListener {
            val email = signUpEmail.text?.trim().toString()
            sharedViewModel.putUserEmail(SignUpUser.Email, email)
            (activity as AuthActivity).navFragment(SignUpPwFragment())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}