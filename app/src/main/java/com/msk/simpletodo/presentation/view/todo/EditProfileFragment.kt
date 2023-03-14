package com.msk.simpletodo.presentation.view.todo

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.msk.simpletodo.R
import com.msk.simpletodo.databinding.FragmentEditProfileBinding
import com.msk.simpletodo.presentation.view.base.BaseFragment
import com.msk.simpletodo.presentation.viewModel.auth.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.DecimalFormat

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val auth by lazy { Firebase.auth }
    private val viewModel: EditProfileViewModel by viewModels()
    private val args: EditProfileFragmentArgs by navArgs()

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false

    private var photoUri: Uri? = null
    private var recordUri: Uri? = null

    private val imageByAlbum =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.setProfileUri(uri?.convertRealPath(FileType.GET_PICTURE))
        }

    private val imageByCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { takePictured ->
            if (takePictured) {
                photoUri.let { uri ->
                    viewModel.setProfileUri(uri?.convertRealPath(FileType.TAKE_PICTURE))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // check user id is not null.
        val userId = checkNotNull(auth.currentUser?.uid)
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

        binding.email.text = auth.currentUser?.email
        binding.editprofileButton.setOnClickListener {
            viewModel.updateProfileImage(args.userId)
        }

        binding.recordStartButton.setOnClickListener {
            checkRecordPermission()
        }

        binding.recordStopButton.setOnClickListener {
            stopRecording()
        }

        val format = DecimalFormat("00")

        lifecycleScope.launch {
            viewModel.recordTime.collectLatest {
                binding.recordSeekbar.progress = it
                binding.recordStart.text = "00:${format.format(it)}"
                if (it >= 30) {
                    stopRecording()
                }
            }
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserProfile()
        setNavigation()
        lifecycleScope.launch {
            setProfileImage()
        }
    }

    private fun startRecording() = lifecycleScope.launch {
        val fileName: String = auth.currentUser?.uid + ".mp3"
        output =
            Environment.getExternalStorageDirectory().absolutePath + "/Download/" + fileName
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(requireContext())
        } else {
            MediaRecorder()
        }
        mediaRecorder?.apply {
            setAudioSource((MediaRecorder.AudioSource.MIC))
            setOutputFormat((MediaRecorder.OutputFormat.MPEG_4))
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(output)
        }

        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            viewModel.startRecord()
            Toast.makeText(requireContext(), "Start Recording", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            mediaRecorder?.release()
            state = false
            viewModel.resetRecord()
            Toast.makeText(requireContext(), "Stop Recording.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "You are not recording.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Build Dialog.
     */
    fun openImageDialog() {
        val items = arrayOf("Take a photo", "Get photo from gallery")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Profile image")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> getImageByLaunchedCamera()
                    1 -> getImageByLaunchedAlbum()
                }
            }.show()
    }

    private fun checkRecordPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Permission is not granted
            val permissions = arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        } else {
            startRecording()
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
     * Get user profile for profile image.
     */
    private fun getUserProfile() {
        args.let {
            viewModel.getUserInfo(it.userId)
        }
    }

    /**
     * Set profile image from viewModel ui state.
     */
    private suspend fun setProfileImage() {
        viewModel.uiState.collectLatest {
            if (it.profileUri.isNotEmpty()) {
                Glide.with(binding.root).load(it.profileUri).into(binding.profileImage)
            }
        }
    }

    /**
     * observing uiState in viewModels.
     *
     * if profileSaved is true, navigate setting fragment.
     */
    private fun setNavigation() = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.uiState.collectLatest {
            if (it.profileSaved) {
                navigateToSetting()
            }
        }
    }

    /**
     * Navigate setting fragment with message.
     */
    private fun navigateToSetting() {
        val action = EditProfileFragmentDirections.actionEditProfileFragmentToTaskSettingFragment(
            resources.getString(R.string.profile_edit_saved)
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
        val filename = "$userId.jpg"
        val content = ContentValues().apply {
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
            put(MediaStore.Audio.Media.DISPLAY_NAME, filename)
            put(MediaStore.Audio.Media.MIME_TYPE, RECORD_MIME_TYPE)
        }
        return requireContext().contentResolver.insert(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    /**
     * Convert mediaStore path to real file path.
     *
     * @return real file path uri
     */
    private fun Uri.convertRealPath(fileType: FileType): Uri? {
        val cursor = createCursor(fileType, this)

        cursor.use { cursor ->
            cursor?.moveToFirst()
            val columnIndex =
                cursor?.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            return cursor?.getString(columnIndex!!)?.toUri()
        }
    }

    /**
     * Create cursor.
     */
    private fun createCursor(fileType: FileType, uri: Uri): Cursor? {
        return when (fileType) {
            FileType.GET_PICTURE -> {
                val id = DocumentsContract.getDocumentId(uri).split(":".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()[1]
                val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
                val selection = MediaStore.Files.FileColumns._ID + " = " + id
                requireActivity()
                    .contentResolver
                    .query(
                        MediaStore.Files.getContentUri("external"),
                        columns,
                        selection,
                        null,
                        null
                    )
            }
            FileType.TAKE_PICTURE -> {
                requireActivity()
                    .contentResolver.query(uri, null, null, null)
            }
        }
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

enum class FileType {
    TAKE_PICTURE,
    GET_PICTURE;
}