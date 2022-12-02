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

        signUpEmail.doBeforeTextChanged { _, _, _, _ ->
            signUpEmailComplete.isEnabled = false
        }

        // set when text change
        signUpEmail.doAfterTextChanged {
            signUpEmailLayout.error = null
            val valid = checkEmailData(signUpEmail.text.toString())
            val job = lifecycleScope.launch {
                if (valid != null) {
                    delay(1000)
                    signUpEmailLayout.error = valid
                    signUpEmailComplete.isEnabled = false
                } else {
                    signUpEmailComplete.isEnabled = true
                }
            }
            Log.d("TAG", "onCreateView: $job")
        }



        signUpEmailComplete.setOnClickListener {
            val email = signUpEmail.text?.trim().toString()
            // Send email data for username fragment
            setFragmentResult("userEmail", bundleOf("email" to email))
            (activity as AuthActivity).setFragment(SignUpPasswordFragment())
        }

        return binding.root
    }

    private fun checkEmailData(data: String): String? {
        return if (data.isNullOrBlank() || !data.contains("@")) {
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