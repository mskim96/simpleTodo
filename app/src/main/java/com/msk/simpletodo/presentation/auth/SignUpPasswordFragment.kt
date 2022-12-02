package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.databinding.FragmentSignUpPasswordBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpPasswordFragment : Fragment() {

    private var _binding: FragmentSignUpPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpPasswordBinding.inflate(inflater, container, false)

        val signUpPasswordComplete = binding.signUpPasswordComplete
        val signUpPasswordLayout = binding.signUpPasswordLayout
        val signUpPassword = binding.signUpPassword

        // set button disabled
        binding.signUpPasswordComplete.isEnabled = false

        signUpPassword.doOnTextChanged { _, _, _, _ ->
            signUpPasswordComplete.isEnabled = false
        }

        // set when text change
        signUpPassword.doAfterTextChanged {
            signUpPasswordLayout.error = null
            val valid = checkPasswordData(signUpPassword.text.toString())
            lifecycleScope.launch {
                if (valid != null) {
                    delay(1000)
                    signUpPasswordLayout.error = valid
                    signUpPasswordComplete.isEnabled = false
                } else {
                    signUpPasswordComplete.isEnabled = true
                }
            }
        }


        binding.signUpPasswordComplete.setOnClickListener {
            val password = signUpPassword.text?.trim().toString()

            // Send email data for username fragment
            setFragmentResult("userPassword", bundleOf("password" to password))
            (activity as AuthActivity).setFragment(SignUpUsernameFragment())
        }

        return binding.root
    }

    private fun checkPasswordData(data: String): String? {
        return if (data.isNullOrBlank() || data.length < 5) {
            "Please write password length more than 5"
        } else {
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}