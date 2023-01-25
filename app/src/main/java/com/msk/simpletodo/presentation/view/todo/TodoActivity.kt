package com.msk.simpletodo.presentation.view.todo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityTodoBinding
import com.msk.simpletodo.presentation.view.auth.AuthActivity
import com.msk.simpletodo.presentation.view.movie.MovieActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : AppCompatActivity() {

    private var _binding: ActivityTodoBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace<TodoMainFragment>(R.id.todoMainFrame)
            setReorderingAllowed(true)
        }
        val navigationView = binding.mainNavigationView
        val headerView = navigationView.getHeaderView(0)
        val header = headerView.findViewById<ImageView>(R.id.navClearButton)
        header.setOnClickListener { closeNav() }
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_movie -> {
                    val intent = Intent(this@TodoActivity, MovieActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                R.id.menu_signOut -> {
                    val intent = Intent(this@TodoActivity, AuthActivity::class.java)
                    startActivity(intent)
                    authViewModel.signOutAccount()
                    this.finish()
                }
            }
            true
        }
    }

    fun openNav() {
        val drawerLayout = binding.mainDrawerLayout
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeNav() {
        val drawerLayout = binding.mainDrawerLayout
        drawerLayout.closeDrawer(GravityCompat.START)
    }


    fun setListFragment(position: Int) {
        supportFragmentManager.commit {
            replace<TodoListFragment>(R.id.todoMainFrame, args = bundleOf("position" to position))
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun setFragmentAddToDo(position: Int) {
        supportFragmentManager.commit {
            replace<TodoAddTodoFragment>(
                R.id.todoMainFrame,
                args = bundleOf("position" to position)
            )
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun completeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        fragment.onDestroy()
        fragment.onDetach()
        supportFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
