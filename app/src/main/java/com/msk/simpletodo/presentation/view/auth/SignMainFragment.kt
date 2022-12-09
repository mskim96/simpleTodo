package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentSignMainBinding


/**
 * When start Application, replaced this fragment
 */
class SignMainFragment : Fragment() {

    private var _binding: FragmentSignMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignMainBinding.inflate(inflater, container, false)

        // nav signUp fragment from AuthActivity method
        binding.navSignUpButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignUpEmailFragment())
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}