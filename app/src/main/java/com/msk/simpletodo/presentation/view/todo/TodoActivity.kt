package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityTodoBinding
import com.msk.simpletodo.presentation.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoActivity() : AppCompatActivity() {

    private var _binding: ActivityTodoBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            authViewModel.userNameFlow.collect {
                Log.d("TAG", "onCreate: $it")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}