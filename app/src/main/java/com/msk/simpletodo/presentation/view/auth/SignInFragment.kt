package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.data.model.UserEntity
import com.msk.simpletodo.databinding.FragmentSignInBinding
import com.msk.simpletodo.domain.auth.model.UserState
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel

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
                    if (it.data is UserEntity) {
                        val username = it.data.username
                        val intent = Intent(requireActivity(), TodoActivity::class.java)
                        intent.putExtra("username", username)
                        startActivity(intent)
                    }

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