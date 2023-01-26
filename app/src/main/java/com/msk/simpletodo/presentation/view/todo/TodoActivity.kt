package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.databinding.ActivityTodoBinding
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : AppCompatActivity() {

    private var _binding: ActivityTodoBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val headerView = navigationView.getHeaderView(0)
//        val header = headerView.findViewById<ImageView>(R.id.navClearButton)
//        header.setOnClickListener { closeNav() }
//        navigationView.setNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.menu_movie -> {
//                    val intent = Intent(this@TodoActivity, MovieActivity::class.java)
//                    startActivity(intent)
//                    this.finish()
//                }
//                R.id.menu_signOut -> {
//                    val intent = Intent(this@TodoActivity, AuthActivity::class.java)
//                    startActivity(intent)
//                    authViewModel.signOutAccount()
//                    this.finish()
//                }
//            }
//            true
//        }
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
