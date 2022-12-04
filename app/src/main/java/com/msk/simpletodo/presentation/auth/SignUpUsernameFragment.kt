package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.msk.simpletodo.SignUpUser
import com.msk.simpletodo.SignUpUser.Companion.validate
import com.msk.simpletodo.data.database.AppDatabase
import com.msk.simpletodo.data.model.UserEntity
import com.msk.simpletodo.databinding.FragmentSignUpUsernameBinding
import com.msk.simpletodo.presentation.util.decryptECB
import com.msk.simpletodo.presentation.util.encryptECB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        // build database
        val db = Room.databaseBuilder(
            requireContext(), AppDatabase::class.java, "todo-database"
        ).build()

        // set button disabled
        binding.signUpUsernameComplete.isEnabled = false
        textClear.visibility = View.GONE

        textClear.setOnClickListener {
            signUpUsername.text = null
        }

        signUpUsername.doAfterTextChanged {

            // validate function is from Companion object in SignUpUser
            val validate = validate(SignUpUser.Username, it.toString())
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

            lifecycleScope.launch(Dispatchers.IO) {
                db.userDao().createAccount(user = UserEntity(email = email,
                    password = password,
                    username = username))
            }

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}