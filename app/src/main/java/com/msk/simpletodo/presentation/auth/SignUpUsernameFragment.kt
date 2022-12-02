package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.msk.simpletodo.databinding.FragmentSignUpUsernameBinding

class SignUpUsernameFragment : Fragment() {

    private var _binding: FragmentSignUpUsernameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpUsernameBinding.inflate(inflater, container, false)

        // set var data for sign up data
        var email: String? = null
        var password: String? = null

        val signUpUsernameComplete = binding.signUpUsernameComplete
        val signUpUsernameLayout = binding.signUpUsernameLayout
        val signUpUsername = binding.signUpUsername

        // set button disabled
        binding.signUpUsernameComplete.isEnabled = false

        // set when text change
        signUpUsernameLayout.error = null

        signUpUsername.doAfterTextChanged {
            val valid = checkUsernameData(signUpUsername.text.toString())
            if (valid != null) {
                signUpUsernameLayout.error = valid
                signUpUsernameComplete.isEnabled = false
            } else {
                signUpUsernameComplete.isEnabled = true
            }
        }


        // Get data from email, password Fragment
        setFragmentResultListener("userEmail") { _, bundle ->
            email = bundle.getString("email")
        }

        setFragmentResultListener("userPassword") { _, bundle ->
            password = bundle.getString("password")
        }

        binding.signUpUsernameComplete.setOnClickListener {
            val username = signUpUsername.text?.trim().toString()
            Log.d("TAG", "onCreateView: $email, $password, $username")
        }

        return binding.root
    }

    private fun checkUsernameData(data: String): String? {
        // TODO: Add logic
        return if (data.isNullOrBlank()) {
            "Please write your name"
        } else {
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}