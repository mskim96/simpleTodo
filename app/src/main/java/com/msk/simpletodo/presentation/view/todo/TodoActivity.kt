package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityTodoBinding
import com.msk.simpletodo.domain.todo.model.TodoType
import com.msk.simpletodo.presentation.viewModel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : AppCompatActivity() {

    private var _binding: ActivityTodoBinding? = null
    private val binding get() = _binding!!

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

    fun setFragment(todoType: TodoType) {
        when (todoType) {
            is TodoType.Person -> {
                supportFragmentManager.commit {
                    replace<TodoPersonFragment>(R.id.todoMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            is TodoType.Work -> {
                supportFragmentManager.commit {
                    replace<TodoWorkFragment>(R.id.todoMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            is TodoType.Study -> {
                supportFragmentManager.commit {
                    replace<TodoStudyFragment>(R.id.todoMainFrame)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }
    }

    fun setFragmentAddToDo() {
        supportFragmentManager.commit {
            replace<TodoAddTodoFragment>(R.id.todoMainFrame)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}