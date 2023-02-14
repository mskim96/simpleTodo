package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msk.simpletodo.databinding.ActivityAuthBinding
import com.msk.simpletodo.presentation.view.auth.oauth.LoginWithGoogle
import com.msk.simpletodo.service.NotificationService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    // binding
    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    private val loginWithGoogle by lazy { LoginWithGoogle(this) }

    override fun onStart() {
        super.onStart()
        loginWithGoogle.googleSignInResult()
        val intent = Intent(this, NotificationService::class.java)
        startService(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun googleSignIn() {
        loginWithGoogle.googleSignIn()
    }
}