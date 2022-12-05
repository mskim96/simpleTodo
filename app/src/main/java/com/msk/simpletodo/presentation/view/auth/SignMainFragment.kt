package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentSignMainBinding

class SignMainFragment : Fragment() {

    private var _binding: FragmentSignMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignMainBinding.inflate(inflater, container, false)

        with(binding) {
            navSignUp.setOnClickListener {
                (activity as AuthActivity).setFragment(SignUpEmailFragment())
            }

            navSignIn.setOnClickListener {
                (activity as AuthActivity).setFragment(SignInFragment())
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}