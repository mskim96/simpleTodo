package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityTodoBinding
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {

    private var _binding: ActivityTodoBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout = binding.mainDrawerLayout
        val bottomNavigation = binding.todoBottomNavigation

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
        lifecycleScope.launchWhenCreated {
            authViewModel.createAccountLocal(
                auth.currentUser?.uid.toString(),
                auth.currentUser?.email.toString(),
                auth.currentUser?.displayName.toString()
            )
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.editProfileFragment -> hideBottomNavigation()
                R.id.profileDetailFragment -> hideBottomNavigation()
                else -> setBottomNavigation()
            }
        }
    }


    fun hideBottomNavigation() {
        binding.todoBottomNavigation.visibility = View.GONE
    }

    fun setBottomNavigation() {
        binding.todoBottomNavigation.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
