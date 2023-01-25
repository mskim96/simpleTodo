package com.msk.simpletodo.presentation.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentSignUpBinding
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.view.auth.signin.SignInFragment

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        with(binding) {
            navSignUpGoogleButton.setOnClickListener {
                (activity as AuthActivity).googleSignIn()
            }

            navSignUpButton.setOnClickListener {
                (activity as AuthActivity).navFragment(SignUpEmailFragment())
            }

            signInNavButton.setOnClickListener {
                (activity as AuthActivity).navFragment(SignInFragment())
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}