package com.msk.simpletodo.presentation.view.auth.signUp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SignUpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var fragments: List<Fragment> = listOf()

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(data: List<Fragment>) {
        fragments = data
        notifyItemInserted(fragments.size)
    }
}