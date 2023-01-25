package com.msk.simpletodo.presentation.view.auth.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.msk.simpletodo.databinding.ActivityOnBoardingBinding
import com.msk.simpletodo.presentation.view.todo.TodoActivity

class OnBoardingActivity : AppCompatActivity() {

    private var _binding: ActivityOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pagerAdapter = BoardingAdapter(this)
        val fragmentList =
            listOf(OnBoardingFragment1(), OnBoardingFragment2(), OnBoardingFragment3())
        pagerAdapter.addFragment(fragmentList)
        binding.onBoardingViewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.viewPagerIndicator, binding.onBoardingViewPager) { tab, _ ->
            binding.onBoardingViewPager.currentItem = tab.position
        }.attach()

        binding.skipOnBoardingButton.setOnClickListener {
            val intent = Intent(this, TodoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}