package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentSignUpEmailBinding

class SignUpEmailFragment : Fragment() {

    private var _binding: FragmentSignUpEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)

        binding.signUpNext.setOnClickListener {
            (activity as AuthActivity).setFragment(SignUpPasswordFragment())
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}