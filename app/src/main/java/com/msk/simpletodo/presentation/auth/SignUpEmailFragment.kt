package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.SignUpUser
import com.msk.simpletodo.SignUpUser.Companion.validate
import com.msk.simpletodo.databinding.FragmentSignUpEmailBinding

class SignUpEmailFragment : Fragment() {

    // binding
    private var _binding: FragmentSignUpEmailBinding? = null
    private val binding get() = _binding!!

    // viewModel
    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)

        val signUpEmailComplete = binding.signUpEmailComplete
        val signUpEmailLayout = binding.signUpEmailLayout
        val signUpEmail = binding.signUpEmail

        // set button disabled
        signUpEmailComplete.isEnabled = false

        // set when text change
        signUpEmail.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = validate(SignUpUser.Email, it.toString())
            if (!validate) {
                signUpEmailComplete.isEnabled = false
                signUpEmailLayout.error = "Please enter an email that matches the format"
            } else {
                signUpEmailComplete.isEnabled = true
                signUpEmailLayout.helperText = "Email format is correct"
            }
        }

        signUpEmailComplete.setOnClickListener {
            val email = signUpEmail.text?.trim().toString()
            sharedViewModel.putUserInformation(SignUpUser.Email, email)
            (activity as AuthActivity).setFragment(SignUpPasswordFragment())
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}