package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentSignInBinding
import com.msk.simpletodo.domain.model.UserState
import com.msk.simpletodo.presentation.viewModel.AuthViewModel

class SignInFragment : Fragment() {


    // binding
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    // viewModel
    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        val signInEmail = binding.signInEmail
        val signInPassword = binding.signInPassword

        // clear text when click x button
        binding.textClear.setOnClickListener {
            signInEmail.text = null
        }

        binding.signInComplete.setOnClickListener {
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            loginAccount(email, password)
        }

        sharedViewModel.userResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is UserState.Success -> {
                    val intent = Intent(requireActivity(), AuthToMainSplashActivity::class.java)
                    startActivity(intent)
                }
                is UserState.Error -> Toast.makeText(
                    requireContext(),
                    "${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        return binding.root
    }

    private fun loginAccount(email: String, password: String) {
        sharedViewModel.loginAccount(email, password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}