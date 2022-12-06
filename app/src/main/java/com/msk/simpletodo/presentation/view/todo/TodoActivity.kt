package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityTodoBinding
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : AppCompatActivity() {

    private var _binding: ActivityTodoBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get username from Auth Activity and send username to TodoMainFragment
        val username = intent.getStringExtra("username")
        val bundleUsername = bundleOf("username" to username)
        supportFragmentManager.commit {
            replace<TodoMainFragment>(R.id.todoMainFrame, args = bundleUsername)
            setReorderingAllowed(true)
        }
    }

//    fun setFragment(fragmentId: Fragment){
//        when(fragmentId){
//
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}