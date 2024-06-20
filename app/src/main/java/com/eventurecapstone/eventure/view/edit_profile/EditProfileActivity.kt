package com.eventurecapstone.eventure.view.edit_profile

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.repository.UserRepository
import com.eventurecapstone.eventure.databinding.ActivityEditProfileBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity
import com.eventurecapstone.eventure.view.login.LoginViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Suppress("DEPRECATION")
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[EditProfileViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupObserver()
        setupAction()
    }

    private fun setupObserver() {
        // Observe ViewModel LiveData
        viewModel.name.observe(this, Observer { name ->
            binding.profileEditName.setText(name)
        })

        viewModel.email.observe(this, Observer { email ->
            binding.profileEditEmail.setText(email)
        })
        viewModel.imageUri.observe(this, Observer { uri ->
            showImage(uri)
        })


        viewModel.updateProfileResponse.observe(this, Observer { response ->
            if (response != null) {
                showAlertDialog()
            }
        })
    }

    private fun setupAction() {
        binding.btnSaveEditProfile.setOnClickListener {
            val name = binding.profileEditName.text.toString()
            val email = binding.profileEditEmail.text.toString()
            val photoUri = viewModel.imageUri.value
            val photo: MultipartBody.Part? = photoUri?.toMultipartBody(this)
            val profile = UserRepository.Profile(name, email)
            viewModel.setProfile(profile, photo)
        }

        binding.btnUpdateEditProfile.setOnClickListener {
            startGallery()
        }
        binding.btnRemoveEditProfile.setOnClickListener {
            viewModel.updateImageUri(null)
            binding.ivPreviewImage.setImageResource(R.drawable.ic_person)
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.profile_updated))
            .setMessage(getString(R.string.profile_update_success_message))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                navigateToDashboard()
            }
            .show()
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showImage(uri: Uri?) {
        Log.d("Image URI", "showImage: $uri")
        binding.ivPreviewImage.setImageURI(uri)
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.updateImageUri(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    // Function to convert Uri to MultipartBody.Part
    fun getFileFromUri(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, context.getFileName(uri))
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        return file
    }

    fun Uri.toMultipartBody(context: Context, name: String = "file"): MultipartBody.Part? {
        val file = getFileFromUri(context, this)
        val requestFile = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            file
        )
        return MultipartBody.Part.createFormData(name, file.name, requestFile)
    }


    // Extension function to get file name from Uri
    fun Context.getFileName(uri: Uri): String {
        var name = ""
        val returnCursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex >= 0 && returnCursor.moveToFirst()) {
                name = returnCursor.getString(nameIndex)
            }
            returnCursor.close()
        }
        return name
    }
}