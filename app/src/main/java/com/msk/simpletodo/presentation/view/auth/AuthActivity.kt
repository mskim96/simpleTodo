package com.msk.simpletodo.presentation.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * EntryPoint this Application
 */
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { Firebase.auth }
    private lateinit var getResult: ActivityResultLauncher<Intent>

    private var googleSignInClient: GoogleSignInClient? = null

    // when username exist on datastore, nav TodoActivity
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            val intent = Intent(this@AuthActivity, TodoActivity::class.java)
//            intent.putExtra("username", currentUser.displayName)
//            // send username and nav to todoActivity
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googleSignInResult()

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


    fun googleSignIn() {
        val signInIntent = googleSignInClient?.signInIntent
        getResult.launch(signInIntent)
    }

    fun googleSignInResult() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseWithGoogle(account)
                } catch (e: ApiException) {
                    Log.d("TAG", "onCreate: ${e.message}")
                }
            }
        }
    }

    fun firebaseWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // TODO: after, save room
                    val user = auth.currentUser
                } else {
                    Log.d("TAG", "firebaseWithGoogle: failed")
                }
            }
    }

    fun navFragment(fragmentName: Fragment) {
        when (fragmentName) {
            is SignUpEmailFragment -> {
                supportFragmentManager.commit {
                    replace<SignUpEmailFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

            is SignUpPwFragment -> {
                supportFragmentManager.commit {
                    replace<SignUpPwFragment>(R.id.authMainFrame)
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

            is SignInFragment -> {
                supportFragmentManager.commit {
                    replace<SignInFragment>(R.id.authMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }
    }

    fun activityFullScreen(i: Int) {
        val window = window
        when (i) {
            0 -> {
                window.setFlags(
                    // activity full screen
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                )
            }
            1 -> {
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                )
            }
        }
    }

    fun signUpBottomSheet() {
        val bottomSheet = SignUpBottomSheet()
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    fun bottomSheetBH(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        fragment.onDestroy()
        fragment.onDetach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}