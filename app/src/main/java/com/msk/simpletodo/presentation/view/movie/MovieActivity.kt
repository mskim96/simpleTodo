package com.msk.simpletodo.presentation.view.movie

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.msk.simpletodo.R
import com.msk.simpletodo.data.model.movie.Movie
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


        supportFragmentManager.commit {
            replace<MovieMainFragment>(R.id.movieMainFrame)
            setReorderingAllowed(true)
        }
    }

    // TODO: Set Main Fragment replace and nav Function
    fun navDetailFragment() {
        supportFragmentManager.commit {
            replace<MovieDetailFragment>(R.id.movieMainFrame)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun navBackButton() {
        supportFragmentManager.commit {
            replace<MovieMainFragment>(R.id.movieMainFrame)
            setReorderingAllowed(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}