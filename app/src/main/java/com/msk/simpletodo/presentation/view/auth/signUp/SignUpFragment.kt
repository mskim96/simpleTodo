package com.msk.simpletodo.presentation.view.auth.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentSignUpBinding
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.view.auth.SignInFragment

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.navSignUpGoogleButton.setOnClickListener {
            (activity as AuthActivity).googleSignIn()
        }

        binding.navSignUpButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignUpMainFragment())
        }

        binding.signInNavButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignInFragment())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}