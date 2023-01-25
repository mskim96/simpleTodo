package com.msk.simpletodo.presentation.view.auth.oauth

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.presentation.view.todo.TodoActivity
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel

class LoginWithGoogle(val activity: ComponentActivity) {
    private var googleSignInClient: GoogleSignInClient? = null
    private val auth by lazy { Firebase.auth }
    private lateinit var getResult: ActivityResultLauncher<Intent>

    private val authViewModel: AuthViewModel by activity.viewModels()

    fun googleSignIn() {
        val signInIntent = googleSignInClient?.signInIntent
        getResult.launch(signInIntent)
    }

    fun googleSignInResult() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        getResult =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
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

    private fun firebaseWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(activity, TodoActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)
                    activity.finish()
                }
            }
    }
}