package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.databinding.FragmentSignUpPasswordBinding
import com.msk.simpletodo.domain.model.SignUpUser
import com.msk.simpletodo.domain.model.validate
import com.msk.simpletodo.presentation.util.encryptECB
import com.msk.simpletodo.presentation.viewModel.AuthViewModel

class SignUpPasswordFragment : Fragment() {

    // binding
    private var _binding: FragmentSignUpPasswordBinding? = null
    private val binding get() = _binding!!

    // viewModel
    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpPasswordBinding.inflate(inflater, container, false)

        val signUpPasswordComplete = binding.signUpPasswordComplete
        val signUpPasswordLayout = binding.signUpPasswordLayout
        val signUpPassword = binding.signUpPassword

        // set button disabled
        binding.signUpPasswordComplete.isEnabled = false


        // set when text change
        signUpPassword.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = SignUpUser.Password.validate(it.toString())
            if (!validate) {
                signUpPasswordComplete.isEnabled = false
                signUpPasswordLayout.error =
                    "Please enter at least 5 characters for the password."
            } else {
                signUpPasswordComplete.isEnabled = true
                signUpPasswordLayout.helperText = "The password format is correct."
            }
        }

        binding.signUpPasswordComplete.setOnClickListener {
            val password = signUpPassword.text?.trim().toString().encryptECB()
            // Send password data for username fragment
            sharedViewModel.putUserInformation(SignUpUser.Password, password)
            (activity as AuthActivity).setFragment(SignUpUsernameFragment())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}