package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityAuthBinding
import com.msk.simpletodo.presentation.view.auth.oauth.LoginWithGoogle
import com.msk.simpletodo.presentation.view.auth.signin.SignInEmailFragment
import com.msk.simpletodo.presentation.view.auth.signin.SignInFragment
import com.msk.simpletodo.presentation.view.auth.signup.SignUpEmailFragment
import com.msk.simpletodo.presentation.view.auth.signup.SignUpFragment
import com.msk.simpletodo.presentation.view.auth.signup.SignUpSendEmailFragment
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


/**
 * EntryPoint this Application
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    // binding
    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { Firebase.auth }
    private var isReady = false
    private val loginWithGoogle by lazy { LoginWithGoogle(this) }

    // when username exist on datastore, nav TodoActivity
    override fun onStart() {
        auth.signOut()
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) navTodoActivity()
        loginWithGoogle.googleSignInResult()
        lifecycleScope.launchWhenStarted {
            delay(800L)
            isReady = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.viewTreeObserver.addOnPreDrawListener {
            if (isReady) {
                binding.root.viewTreeObserver.removeOnPreDrawListener { true }
                true
            } else false
        }

        supportFragmentManager.commit {
            replace<SignUpFragment>(R.id.authMainFrame)
            setReorderingAllowed(true)
        }
    }

    fun googleSignIn() {
        loginWithGoogle.googleSignIn()
    }

    fun navFragment(fragmentName: Fragment) {
        when (fragmentName) {
            is SignUpEmailFragment -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.enter_from_left,
                        R.anim.exit_from_right,
                        R.anim.exit_from_left,
                        R.anim.enter_from_right,
                    )
                    replace<SignUpEmailFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

            is SignUpFragment -> {
                supportFragmentManager.popBackStack()
                supportFragmentManager.commit {
                    replace<SignUpFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                }
            }

            is SignInFragment -> {
                supportFragmentManager.commit {
                    replace<SignInFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

            is SignInEmailFragment -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.enter_from_left,
                        R.anim.exit_from_right,
                        R.anim.exit_from_left,
                        R.anim.enter_from_right,
                    )
                    replace<SignInEmailFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

            is SignUpSendEmailFragment -> {
                supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.enter_from_bottom,
                        R.anim.exit_from_top,
                        R.anim.exit_from_bottom,
                        R.anim.enter_from_top,
                    )
                    replace<SignUpSendEmailFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }
    }

    fun navTodoActivity() {
        val intent = Intent(this@AuthActivity, TodoActivity::class.java)
        // send username and nav to todoActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}