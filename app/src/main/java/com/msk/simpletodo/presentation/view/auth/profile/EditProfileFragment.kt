package com.msk.simpletodo.presentation.view.auth.profile

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentEditProfileBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()

    private var mediaRecorder: MediaRecorder? = null

    private var photoUri: Uri? = null
    private var recordUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.startRecording()
            }
        }

    private val imageByAlbum =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri.let {
                viewModel.updateProfileImage(uri)
            }
        }

    private val imageByCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { takePictured ->
            if (takePictured) {
                photoUri.let { uri ->
                    viewModel.updateProfileImage(uri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = args.userId
        photoUri = createImageUri(userId) // create uri for image with user id.
        recordUri = createRecordUri(userId) // create uri for record with user id.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        bind {
            fragment = this@EditProfileFragment
            viewmodel = viewModel
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            launch { setProfileImage() }
            launch { setNavigation() }
            launch { setUserMessage() }
            launch { setRecorder() }
        }
    }

    private fun startRecording() = viewLifecycleOwner.lifecycleScope.launch {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mediaRecorder = MediaRecorder(requireContext()).apply {
                setAudioSource((MediaRecorder.AudioSource.MIC))
                setOutputFormat((MediaRecorder.OutputFormat.MPEG_4))
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(
                    requireActivity().contentResolver.openFileDescriptor(
                        recordUri!!,
                        "w"
                    )?.fileDescriptor
                )
            }
        }

        runCatching {
            mediaRecorder?.prepare()
        }.onSuccess {
            mediaRecorder?.start()
            Toast.makeText(requireContext(), "Start Recording", Toast.LENGTH_SHORT).show()
        }.onFailure { throwable ->
            Toast.makeText(requireContext(), "${throwable.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder?.apply {
                stop()
                reset()
                release()
            }
            viewModel.stopRecording()
            viewModel.resetRecording()
            Toast.makeText(requireContext(), "Stop Recording.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Build Dialog.
     */
    fun openImageDialog() {
        val items = arrayOf("Take a photo", "Get photo from gallery", "Delete photo")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Profile image")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> getImageByLaunchedCamera()
                    1 -> getImageByLaunchedAlbum()
                    2 -> deleteProfileImage()
                }
            }.show()
    }

    fun openRecordDialog() {
        when (viewModel.uiState.value.isRecorded) {
            RecordState.START -> {
                viewModel.stopRecording()
            }
            else -> {
                val items = arrayOf("Record bio", "Delete record bio")
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Edit Record bio")
                    .setItems(items) { _, which ->
                        when (which) { // invoke startRecording function if permission granted
                            0 -> checkRecordPermission()
                            1 -> deleteRecordBio()
                        }
                    }.show()
            }
        }
    }


    /**
     * Check permission and start recording if granted it.
     */
    private fun checkRecordPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.startRecording()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> {
                Snackbar.make(requireView(), "Can't use voice bio.", Snackbar.LENGTH_SHORT).show()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    /**
     * Save User profile.
     */
    fun saveUserProfile() {
        val username = binding.username.text.toString()
        val bio = binding.bioContent.text.toString()
        viewModel.updateUsername(username)
        viewModel.updateBio(bio)
        viewModel.saveUserProfile()
    }

    private suspend fun setRecorder() =
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.uiState.collectLatest {
                when (it.isRecorded) {
                    RecordState.INIT -> {
                        binding.recordButton.setImageResource(R.drawable.ic_mic)
                        RecordState.INIT
                    }
                    RecordState.START -> {
                        binding.recordButton.setImageResource(R.drawable.ic_pause)
                        startRecording()
                    }
                    RecordState.STOP -> {
                        binding.recordButton.setImageResource(R.drawable.ic_mic)
                        stopRecording()
                    }
                }
            }
        }

    /**
     * Launch registerActivityResult for get Image by Camera app.
     */
    private fun getImageByLaunchedCamera() {
        photoUri?.let { imageByCamera.launch(photoUri) }
    }

    /**
     * Launch registerActivityResult for get Image by Album.
     */
    private fun getImageByLaunchedAlbum() {
        photoUri?.let { imageByAlbum.launch(IMAGE_MIME_TYPE) }
    }

    /**
     * Delete profile image.
     */
    private fun deleteProfileImage() {
        viewModel.deleteProfileImage()
    }

    /**
     * Delete Record bio.
     */
    private fun deleteRecordBio() {
        viewModel.deleteRecordBio()
    }

    /**
     * Set profile image from viewModel ui state.
     *
     * TODO: Use binding adapter.
     */
    private suspend fun setProfileImage() = repeatOnLifecycle(Lifecycle.State.CREATED) {
        viewModel.uiState.collectLatest {
            if (it.profileImage.isNotEmpty()) {
                Glide.with(binding.root)
                    .load(it.profileImage.toUri())
                    .into(binding.profileImage)
            } else {
                Glide.with(binding.root)
                    .load("")
                    .into(binding.profileImage)
            }
        }
    }

    /**
     * Show message for user.
     */
    private suspend fun setUserMessage() = repeatOnLifecycle(Lifecycle.State.CREATED) {
        viewModel.uiState.collectLatest {
            if (it.userMessage.isNotEmpty()) {
                Snackbar.make(requireView(), it.userMessage, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * observing uiState in viewModels.
     *
     * if profileSaved is true, navigate setting fragment.
     */
    private suspend fun setNavigation() =
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collectLatest {
                if (it.isUserSaved) {
                    navigateToDetail()
                }
            }
        }

    /**
     * Navigate setting fragment with message.
     */
    private fun navigateToDetail() {
        val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileDetailFragment(
            resources.getString(R.string.profile_edit_saved),
            args.userId,
        )
        findNavController().navigate(action)
    }

    /**
     * Create Image uri for if user take picture and save it in media store.
     *
     * @param userId userId for filename.
     * @return [Uri] for insert file in MediaStore.
     */
    private fun createImageUri(userId: String): Uri? {
        val filename = "${LocalDateTime.now()}.jpg"
        val content = ContentValues().apply {
            put(MediaStore.Images.Media._ID, userId)
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, IMAGE_MIME_TYPE)
        }
        return requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    /**
     * Create Record uri for if user record file and save it in media store.
     *
     * @param userId userId for filename.
     * @return [Uri] for insert file in MediaStore.
     */
    private fun createRecordUri(userId: String): Uri? {
        val filename = "$userId.mp3"
        val content = ContentValues().apply {
            put(MediaStore.Audio.Media._ID, userId)
            put(MediaStore.Audio.Media.DISPLAY_NAME, filename)
            put(MediaStore.Audio.Media.MIME_TYPE, RECORD_MIME_TYPE)
        }
        return requireContext().contentResolver.insert(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photoUri = null
        recordUri = null
    }

    companion object {
        const val IMAGE_MIME_TYPE = "image/*"
        const val RECORD_MIME_TYPE = "audio/mp3"
    }
}