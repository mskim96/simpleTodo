package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ActivityTodoBinding
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

    fun setListFragment(position: Int) {
        supportFragmentManager.commit {
            replace<TodoListFragment>(R.id.todoMainFrame, args = bundleOf("position" to position))
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun setFragmentAddToDo(position: Int) {
        supportFragmentManager.commit {
//            addSharedElement(v, "addToButton")
            replace<TodoAddTodoFragment>(
                R.id.todoMainFrame,
                args = bundleOf("position" to position)
            )
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun completeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        fragment.onDestroy()
        fragment.onDetach()
        supportFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
