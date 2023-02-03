package com.msk.simpletodo.presentation.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msk.simpletodo.databinding.ActivityAuthBinding
import com.msk.simpletodo.presentation.view.auth.oauth.LoginWithGoogle
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