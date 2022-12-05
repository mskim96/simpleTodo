package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityAuthToMainSplashBinding
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import kotlinx.coroutines.*

class AuthToMainSplashActivity : AppCompatActivity() {

    private var _binding: ActivityAuthToMainSplashBinding? = null
    private val binding get() = _binding!!

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthToMainSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get Username
        val username = intent.getStringExtra("username")
        binding.splashGreetingUser.text = username

        activityScope.launch {
            delay(2500)
            val intent = Intent(this@AuthToMainSplashActivity, TodoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}