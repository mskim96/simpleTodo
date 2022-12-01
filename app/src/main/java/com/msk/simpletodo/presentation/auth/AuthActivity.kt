package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.authMainFrame.id, SignInFragment()).commit()
    }

    /**
     * Nav Fragment Function
     * Application Process :
     * Email -> password -> username
     *
     * If press backButton in username fragment, go back email fragment
     * backstack :
     * email -> back button -> sign in
     * password -> back button -> sign in
     * username -> back button -> sign in
     */
    fun setFragment(fragmentId: Fragment) {
        when (fragmentId) {
            is SignUpEmailFragment -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.authMainFrame, fragmentId)
                    .addToBackStack(null)
                    .commit()
            }
            is SignUpPasswordFragment -> {
                // pop this fragment stack, when click back button, it will go sign in fragment
                // pop signUpEmail fragment stack
                supportFragmentManager.popBackStack()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.authMainFrame, fragmentId)
                    .addToBackStack(null)
                    .commit()
            }
            is SignUpUsernameFragment -> {
                // pop this fragment stack, when click back button, it will go sign in fragment
                // pop signUpPassword fragment stack
                supportFragmentManager.popBackStack()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.authMainFrame, fragmentId)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}