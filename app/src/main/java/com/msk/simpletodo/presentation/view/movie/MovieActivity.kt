package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val drawerLayout = binding.mainDrawerLayout
        val navigationView = binding.navView
        val bottomNavigation = binding.movieBottomNavigation
//        val getNavigationView = navigationView.getHeaderView(0)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.movieMainFragment,
                R.id.movieSearchFragment,
                R.id.movieLikeFragment,
            ), drawerLayout
        )
        navigationView.setupWithNavController(navController)
        bottomNavigation.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    fun hideBottomNavigation() {
        binding.movieBottomNavigation.visibility = View.GONE
    }

    fun setBottomNavigation() {
        binding.movieBottomNavigation.visibility = View.VISIBLE
    }

    // TODO: Set Main Fragment replace and nav Function
    fun navDetailFragment() {

    }

    fun navDetailInnerFragment(position: Int) {
        when (position) {
            0 -> {
                supportFragmentManager.commit {
                    replace<MovieDetailInfoFragment>(R.id.innerDetailFrame)
                    setReorderingAllowed(true)
                }
            }
            1 -> {
                supportFragmentManager.commit {
                    replace<MovieDetailRelFragment>(R.id.innerDetailFrame)
                    setReorderingAllowed(true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}