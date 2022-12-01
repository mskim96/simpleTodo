package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.msk.simpletodo.databinding.FragmentSignUpPasswordBinding

class SignUpPasswordFragment : Fragment() {

    private var _binding: FragmentSignUpPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpPasswordBinding.inflate(inflater, container, false)

        binding.signUpNext.setOnClickListener {
            // TODO: Password Validate
            val password = binding.signUpPassword.text.toString()

            // Send email data for username fragment
            setFragmentResult("userPassword", bundleOf("password" to password))
            (activity as AuthActivity).setFragment(SignUpUsernameFragment())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}