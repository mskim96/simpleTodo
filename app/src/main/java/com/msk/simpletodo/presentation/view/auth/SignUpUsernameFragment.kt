package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.databinding.FragmentSignUpUsernameBinding
import com.msk.simpletodo.presentation.util.SignUpUser
import com.msk.simpletodo.presentation.util.validate
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpUsernameFragment : Fragment() {

    private var _binding: FragmentSignUpUsernameBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpUsernameBinding.inflate(inflater, container, false)


        // screen Element
        val signUpUsernameCompleteButton = binding.signUpUsernameCompleteButton
        val signUpUsernameLayout = binding.signUpUsernameLayout
        val signUpUsername = binding.signUpUsername
        val textClearButton = binding.textClearButton

        // set button disabled
        signUpUsernameCompleteButton.isEnabled = false
        textClearButton.visibility = View.GONE

        textClearButton.setOnClickListener {
            signUpUsername.text = null
        }

        // When editText changed
        signUpUsername.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = SignUpUser.Username.validate(it.toString())
            if (!validate) {
                signUpUsernameCompleteButton.isEnabled = false
                signUpUsernameLayout.error = "Please check your name"
            } else {
                signUpUsernameCompleteButton.isEnabled = true
                signUpUsernameLayout.helperText = "Perfect."
            }

            // show textClearButton
            if (signUpUsername.text.isNullOrBlank()) {
                textClearButton.visibility = View.GONE
            } else {
                textClearButton.visibility = View.VISIBLE
            }
        }

        signUpUsernameCompleteButton.setOnClickListener {
            val email = sharedViewModel.userDataEmail.value!!
            val username = signUpUsername.text?.trim().toString()

            createAccount(email, username)
            navTodoActivity(username)
        }

        return binding.root
    }

    // create Account method from viewModel
    private fun createAccount(email: String, username: String) {
        sharedViewModel.createAccount(email, username)
    }

    private fun navTodoActivity(username: String) {
        val intent = Intent(requireActivity(), TodoActivity::class.java)
        intent.putExtra("username", username)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}