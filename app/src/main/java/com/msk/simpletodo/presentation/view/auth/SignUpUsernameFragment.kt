package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.databinding.FragmentSignUpUsernameBinding
import com.msk.simpletodo.domain.auth.model.SignUpUser
import com.msk.simpletodo.domain.auth.model.validate
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpUsernameFragment : Fragment() {

    // binding
    private var _binding: FragmentSignUpUsernameBinding? = null
    private val binding get() = _binding!!

    // viewModel
    private val sharedViewModel by lazy { ViewModelProvider(requireActivity())[AuthViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpUsernameBinding.inflate(inflater, container, false)

        val signUpUsernameComplete = binding.signUpUsernameComplete
        val signUpUsernameLayout = binding.signUpUsernameLayout
        val signUpUsername = binding.signUpUsername
        val textClear = binding.textClear

        // set button disabled
        binding.signUpUsernameComplete.isEnabled = false
        textClear.visibility = View.GONE

        textClear.setOnClickListener {
            signUpUsername.text = null
        }

        signUpUsername.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = SignUpUser.Username.validate(it.toString())
            if (!validate) {
                signUpUsernameComplete.isEnabled = false
                signUpUsernameLayout.error = "Please write your name"
            } else {
                signUpUsernameComplete.isEnabled = true
                signUpUsernameLayout.helperText = "Perfect!"
            }

            // show textClear Button
            if (signUpUsername.text.isNullOrBlank()) {
                textClear.visibility = View.GONE
            } else {
                textClear.visibility = View.VISIBLE
            }
        }

        binding.signUpUsernameComplete.setOnClickListener {
            val email = sharedViewModel.userDataEmail.value!!
            val password = sharedViewModel.userDataPassword.value!!
            val username = signUpUsername.text?.trim().toString()

            createAccount(email, password, username)
        }

        sharedViewModel.userResult.observe(viewLifecycleOwner, Observer {
            val intent = Intent(requireActivity(), TodoActivity::class.java)
            val username = signUpUsername.text.toString()
            intent.putExtra("username", username)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        })

        return binding.root
    }

    // create Account method from viewModel
    fun createAccount(email: String, password: String, username: String) {
        sharedViewModel.createAccount(email, password, username)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}