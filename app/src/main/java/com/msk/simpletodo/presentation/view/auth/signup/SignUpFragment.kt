package com.msk.simpletodo.presentation.view.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.databinding.FragmentSignUpBinding
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { Firebase.auth }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null) {
            val action = SignUpFragmentDirections.actionSignUpFragmentToTodoActivity()
            this@SignUpFragment.findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        with(binding) {
            navSignUpGoogleButton.setOnClickListener {
                (activity as AuthActivity).googleSignIn()
            }
            navSignUpButton.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpEmailFragment()
                this@SignUpFragment.findNavController().navigate(action)
            }
            signInNavButton.setOnClickListener {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                this@SignUpFragment.findNavController().navigate(action)
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}