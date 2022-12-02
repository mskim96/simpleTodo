package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.databinding.FragmentSignUpEmailBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpEmailFragment : Fragment() {

    private var _binding: FragmentSignUpEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)

        val signUpEmailComplete = binding.signUpEmailComplete
        val signUpEmailLayout = binding.signUpLEmailLayout
        val signUpEmail = binding.signUpEmail

        // set button disabled
        signUpEmailComplete.isEnabled = false

        // set when text change
        signUpEmail.doAfterTextChanged {
            lifecycleScope.launch {
                val valid = checkEmailData(signUpEmail.text.toString())
                if (valid != null) {
                    signUpEmailLayout.error = valid
                    signUpEmailComplete.isEnabled = false
                } else {
                    signUpEmailComplete.isEnabled = valid == null
                    signUpEmailLayout.error = null
                }
                Log.d("TAG", "onCreateView: ${valid}")
            }
        }

        signUpEmailComplete.setOnClickListener {
            val email = signUpEmail.text?.trim().toString()
            // Send email data for username fragment
            setFragmentResult("userEmail", bundleOf("email" to email))
            (activity as AuthActivity).setFragment(SignUpPasswordFragment())
        }

        return binding.root
    }

    private suspend fun checkEmailData(data: String): String? {
        // TODO: Add logic
        return if (data.isNullOrBlank() || data.length < 6) {
            "Please write your email"
        } else {
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}