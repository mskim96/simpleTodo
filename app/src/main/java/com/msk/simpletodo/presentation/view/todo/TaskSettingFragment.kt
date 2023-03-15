package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTaskSettingBinding
import com.msk.simpletodo.presentation.viewModel.todo.SettingAdapter

class TaskSettingFragment : Fragment() {

    private var _binding: FragmentTaskSettingBinding? = null
    private val binding get() = _binding!!

    private val auth by lazy { Firebase.auth }
    private val settingAdapter by lazy { SettingAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskSettingBinding.inflate(inflater, container, false)

        val navigationView = binding.navView
        val getNavigationView = navigationView.getHeaderView(0)
        getNavigationView.findViewById<TextView>(R.id.navHeaderUsername).text =
            auth.currentUser?.email
        val drawerLayout = binding.mainDrawerLayout
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.taskMainFragment,
                R.id.taskCalendarFragment,
                R.id.taskSettingFragment
            ), drawerLayout
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)

        binding.settingRecycler1.adapter = settingAdapter
        binding.profileInfo.text = auth.currentUser?.email
        binding.profileInfo.setOnClickListener {
            val userId = auth.currentUser?.uid
            val action =
                TaskSettingFragmentDirections.actionTaskSettingFragmentToProfileDetailFragment(
                    "", userId!!
                )
            this.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}