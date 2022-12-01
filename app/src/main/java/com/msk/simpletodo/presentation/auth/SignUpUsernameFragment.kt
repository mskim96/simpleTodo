package com.msk.simpletodo.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.msk.simpletodo.databinding.FragmentSignUpUsernameBinding

class SignUpUsernameFragment : Fragment() {

    private var _binding: FragmentSignUpUsernameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpUsernameBinding.inflate(inflater, container, false)
        var listArray = mutableListOf<String?>()

        // Get Email data from email Fragment
        setFragmentResultListener("userEmail") { _, bundle ->
            listArray.add(bundle.getString("email"))

            setFragmentResultListener("userPassword") { _, bundle ->
                listArray.add(bundle.getString("password"))
                Log.d("TAG", "in: $listArray")
            }
        }

        // TODO: Set Username and impl signup logic

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}