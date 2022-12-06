package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.databinding.FragmentSignUpEmailBinding
import com.msk.simpletodo.domain.auth.model.SignUpUser
import com.msk.simpletodo.domain.auth.model.validate
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel

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
        val textClear = binding.textClear
        // set button disabled
        signUpEmailComplete.isEnabled = false
        textClear.visibility = View.GONE

        textClear.setOnClickListener {
            signUpEmail.text = null
        }


        // set when text change
        signUpEmail.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = SignUpUser.Email.validate(it.toString())
            if (!validate) {
                signUpEmailComplete.isEnabled = false
                signUpEmailLayout.error = "Please enter an email that matches the format"
            } else {
                signUpEmailComplete.isEnabled = true
                signUpEmailLayout.helperText = "Email format is correct"
            }

            // show textClear Button
            if (signUpEmail.text.isNullOrBlank()) {
                textClear.visibility = View.GONE
            } else {
                textClear.visibility = View.VISIBLE
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