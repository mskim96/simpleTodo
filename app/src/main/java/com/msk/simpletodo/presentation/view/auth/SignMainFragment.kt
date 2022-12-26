package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentSignMainBinding


/**
 * When start Application, replaced this fragment
 */
class SignMainFragment : Fragment() {

    private var _binding: FragmentSignMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AuthActivity).activityFullScreen(0)
    }

    override fun onResume() {
        super.onResume()
        (activity as AuthActivity).activityFullScreen(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignMainBinding.inflate(inflater, container, false)
        // nav signUp fragment from AuthActivity method


        binding.navSignUpButton.setOnClickListener {
            (activity as AuthActivity).signUpBottomSheet()
        }

        binding.navSignInButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignInFragment())
        }

        /**
         * Animation
         */
        AnimationUtils.loadAnimation(requireContext(), R.anim.text_animation_500).also {
            binding.signMainTitle.startAnimation(it)
        }

        AnimationUtils.loadAnimation(requireContext(), R.anim.text_animation_1000).also {
            binding.signMainAppName.startAnimation(it)
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}