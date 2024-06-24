package com.eventurecapstone.eventure.view.edit_profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.repository.UserRepository
import com.eventurecapstone.eventure.databinding.ActivityEditProfileBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.helper.toMultipartBody
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity
import okhttp3.MultipartBody

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[EditProfileViewModel::class.java]
        setupObserver()
        setupAction()
    }

    private fun setupObserver() {
        // Observe ViewModel LiveData
        viewModel.name.observe(this) { name ->
            binding.profileEditName.setText(name)
        }

        viewModel.email.observe(this) { email ->
            binding.profileEditEmail.setText(email)
        }

        viewModel.imageUri.observe(this) { uri ->
            showImage(uri)
        }


        viewModel.updateProfileResponse.observe(this) { response ->
            if (response != null) showAlertDialog()
        }
    }

    private fun setupAction() {
        binding.btnSaveEditProfile.setOnClickListener {
            val name = binding.profileEditName.text.toString()
            val email = binding.profileEditEmail.text.toString()
            val photoUri = viewModel.imageUri.value
            val photo: MultipartBody.Part? = photoUri?.toMultipartBody(this)

            if (name.isEmpty()) {
                binding.profileEditName.error = "Name cannot be empty"
                return@setOnClickListener
            } else if (email.isEmpty()) {
                binding.profileEditEmail.error = "Email cannot be empty"
                return@setOnClickListener
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.profileEditEmail.error = "Invalid email format"
                return@setOnClickListener
            } else {
                val profile = UserRepository.Profile(name, email)
                viewModel.setProfile(profile, photo)
            }
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
        if (uri != null) {
            binding.ivPreviewImage.setImageURI(uri)
        } else {
            binding.ivPreviewImage.setImageResource(R.drawable.ic_person)
        }
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
}