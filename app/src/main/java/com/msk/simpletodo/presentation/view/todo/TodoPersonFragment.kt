package com.msk.simpletodo.presentation.view.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentTodoPersonBinding

class TodoPersonFragment : Fragment() {

    private var _binding: FragmentTodoPersonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoPersonBinding.inflate(inflater, container, false)

        binding.navAddTodoButton.setOnClickListener {
            (activity as TodoActivity).setFragmentAddToDo()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}