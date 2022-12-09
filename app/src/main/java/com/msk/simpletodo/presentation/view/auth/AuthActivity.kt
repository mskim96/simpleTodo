package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityAuthBinding
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * EntryPoint this Application
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: AuthViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // when username exist on datastore, nav TodoActivity
        lifecycleScope.launch(Dispatchers.IO) {
            sharedViewModel.userNameFlow.collect {
                if (it != null) {
                    val intent = Intent(this@AuthActivity, TodoActivity::class.java)
                    intent.putExtra("username", it)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                }
            }
        }

        // set signMain fragment
        supportFragmentManager.commit {
            replace<SignMainFragment>(R.id.authMainFrame)
            setReorderingAllowed(true)
        }
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

    fun navFragment(fragmentName: Fragment) {
        when (fragmentName) {
            is SignUpEmailFragment -> {
                supportFragmentManager.commit {
                    replace<SignUpEmailFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            is SignUpUsernameFragment -> {
                // pop this fragment stack, when click back button, it will go sign in fragment
                // pop signUpPassword fragment stack
                supportFragmentManager.popBackStack()
                supportFragmentManager.commit {
                    replace<SignUpUsernameFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}