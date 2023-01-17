package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.msk.simpletodo.databinding.FragmentSignMainBinding
import com.msk.simpletodo.presentation.view.auth.onboarding.*
import com.msk.simpletodo.presentation.view.auth.signUp.SignUpFragment


/**
 * When start Application, replaced this fragment
 */
class SignMainFragment : Fragment() {

    private var _binding: FragmentSignMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignMainBinding.inflate(inflater, container, false)
        // nav signUp fragment from AuthActivity method

        val pagerAdapter = BoardingAdapter(this)
        val fragmentList =
            listOf(OnBoardingFragment1(), OnBoardingFragment2(), OnBoardingFragment3())
        pagerAdapter.addFragment(fragmentList)
        binding.signMainViewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.viewPagerIndicator, binding.signMainViewPager) { tab, _ ->
            binding.signMainViewPager.currentItem = tab.position
        }.attach()

        binding.navSignButton.setOnClickListener {
            (activity as AuthActivity).navFragment(SignUpFragment())
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}