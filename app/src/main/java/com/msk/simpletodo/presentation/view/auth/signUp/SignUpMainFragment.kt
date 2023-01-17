package com.msk.simpletodo.presentation.view.auth.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.databinding.FragmentSignUpMainBinding

class SignUpMainFragment : Fragment() {

    private var _binding: FragmentSignUpMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpMainBinding.inflate(inflater, container, false)

        val pagerAdapter = SignUpAdapter(this)
        val fragmentList =
            listOf(SignUpEmailFragment(), SignUpPwFragment(), SignUpUsernameFragment())
        pagerAdapter.addFragment(fragmentList)
        binding.signUpMainViewPager.adapter = pagerAdapter

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}