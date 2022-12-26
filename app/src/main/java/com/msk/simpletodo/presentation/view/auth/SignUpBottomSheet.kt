package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.AuthSignUpBottomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpBottomSheet : BottomSheetDialogFragment() {

    private var _binding: AuthSignUpBottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AuthSignUpBottomBinding.inflate(inflater, container, false)

        binding.navSignUpGoogleButton.setOnClickListener {
            (activity as AuthActivity).googleSignIn()
        }


        binding.navSignUpButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignUpEmailFragment())
            (activity as AuthActivity).bottomSheetBH(this@SignUpBottomSheet)
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AuthActivity).activityFullScreen(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

}