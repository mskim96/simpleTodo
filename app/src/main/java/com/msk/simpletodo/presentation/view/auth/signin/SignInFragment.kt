package com.msk.simpletodo.presentation.view.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentSignInBinding
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.view.auth.signup.SignUpFragment

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.navSignInGoogleButton.setOnClickListener {
            (activity as AuthActivity).googleSignIn()
        }

        binding.navSignInButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignInEmailFragment())
        }

        binding.signInNavButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignUpFragment())
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}