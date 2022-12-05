package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msk.simpletodo.R
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import kotlinx.coroutines.*

class AuthToMainSplashActivity : AppCompatActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_to_main_splash)

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
}