package com.msk.simpletodo.presentation.view.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.msk.simpletodo.databinding.FragmentSignInBinding
import com.msk.simpletodo.presentation.view.auth.AuthActivity

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        with(binding) {
            navSignInGoogleButton.setOnClickListener {
                (activity as AuthActivity).googleSignIn()
            }
            navSignInButton.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignInEmailFragment()
                this@SignInFragment.findNavController().navigate(action)
            }
            signInNavButton.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                this@SignInFragment.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}