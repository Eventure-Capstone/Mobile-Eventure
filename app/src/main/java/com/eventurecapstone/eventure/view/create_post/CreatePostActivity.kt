package com.eventurecapstone.eventure.view.create_post

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.databinding.ActivityCreatePostBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import java.util.Calendar

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var model: CreatePostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[CreatePostViewModel::class.java]

        setupActionBar()
        setupImagePreview()
        setupInputNonText()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                model.setCurrentImageUri(uri)
            } else {
                Log.d("Photo Picker", "No media selected")
            }
        }

    private fun setupImagePreview(){
        model.currentImageUri.observe(this){
            Log.d("Image URI", "showImage: $it")
            binding.ivCreatePost.setImageURI(it)
        }
    }

    private fun setupCategoryDropDown(){
        model.category.observe(this){
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
            binding.categoryDropDown.setAdapter(adapter)
        }
    }

    private fun setupInputNonText(){
        setupCategoryDropDown()
        binding.btnUpload.setOnClickListener { startGallery() }
        binding.eventStartDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.eventStartDateEditText.clearFocus()
                showDatePicker(binding.eventStartDateEditText)
            }
        }
        binding.eventEndDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.eventEndDateEditText.clearFocus()
                showDatePicker(binding.eventEndDateEditText)
            }
        }
        binding.eventStartTimeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.eventStartTimeEditText.clearFocus()
                showTimePicker(binding.eventStartTimeEditText)
            }
        }
        binding.eventEndTimeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.eventEndTimeEditText.clearFocus()
                showTimePicker(binding.eventEndTimeEditText)
            }
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(date)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                editText.setText(time)
            }, hour, minute, true
        )
        timePickerDialog.show()
    }

}