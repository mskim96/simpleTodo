package com.msk.simpletodo.presentation.view.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentSignInBinding
import com.msk.simpletodo.presentation.util.SignUpUser
import com.msk.simpletodo.presentation.util.validate
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        val imm = // for soft keyboard
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val signInCompleteButton = binding.signInCompleteButton
        val signInEmail = binding.signInEmail

        val signInPassword = binding.signInPassword
        val textClearButton = binding.textClearButton

        textClearButton.visibility = View.GONE

        textClearButton.setOnClickListener {
            signInEmail.text = null // text clear
        }

        lifecycleScope.launchWhenCreated {
            launch { // for auto open soft keyboard
                delay(300L)
                signInCompleteButton.visibility = View.VISIBLE
                signInEmail.requestFocus() // send focus
                imm.showSoftInput(signInEmail, 0) // open soft keyboard
            }

            launch {
                sharedViewModel.loginResult.collectLatest {
                    if (it != null) {
                        val intent = Intent(requireActivity(), TodoActivity::class.java)
                        intent.putExtra("username", it) // send username and nav to todoActivity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        (activity as AuthActivity).startActivity(intent)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            " The password is invalid or the user does not have a password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }



        signInEmail.doAfterTextChanged {
            // show textClearButton
            if (signInEmail.text.isNullOrBlank()) {
                textClearButton.visibility = View.GONE
            } else {
                textClearButton.visibility = View.VISIBLE
            }
        }


        signInCompleteButton.setOnClickListener {
            val email = signInEmail.text.toString().trim()
            val password = signInPassword.text.toString().trim()
            signIn(email, password)
        }

        return binding.root
    }

    private fun signIn(email: String, password: String) = lifecycleScope.launch(Dispatchers.IO) {
        sharedViewModel.signInAccount(email, password)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}