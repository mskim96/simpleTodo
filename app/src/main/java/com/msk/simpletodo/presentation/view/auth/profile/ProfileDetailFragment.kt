package com.msk.simpletodo.presentation.view.auth.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.ProfileDetailFragmentBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileDetailFragment :
    BaseFragment<ProfileDetailFragmentBinding>(R.layout.profile_detail_fragment) {

    private val viewModel: ProfileDetailViewModel by viewModels()
    private val args: ProfileDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        /**
         * setup toolbar
         */
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        /**
         * bind function for data binding.
         */
        bind {
            viewmodel = viewModel
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            launch { setupProfileImage() }
            launch { setupNavigation() }
        }
    }

    private suspend fun setupProfileImage() = repeatOnLifecycle(Lifecycle.State.CREATED) {
        viewModel.uiState.collectLatest {
            Glide.with(binding.root).load(it.user?.profileImage?.toUri())
                .into(binding.profileImage)
        }
    }


    private suspend fun setupNavigation() = repeatOnLifecycle(Lifecycle.State.CREATED) {
        viewModel.uiState.collectLatest {
            if (it.isEdit) {
                navigateToEditProfile()
                viewModel.navEditProfileComplete()
            }
        }
    }

    private fun navigateToEditProfile() {
        val action =
            ProfileDetailFragmentDirections.actionProfileDetailFragmentToEditProfileFragment(args.userId)
        this.findNavController().navigate(action)
    }
}