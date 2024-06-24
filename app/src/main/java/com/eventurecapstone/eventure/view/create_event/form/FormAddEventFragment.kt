package com.eventurecapstone.eventure.view.create_event.form

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.databinding.FragmentFormAddEventBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.create_event.CreateEventViewModel
import com.eventurecapstone.eventure.view.create_event.maps.MapsFragment
import java.util.Calendar

class FormAddEventFragment : Fragment() {
    private var _binding: FragmentFormAddEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var model: CreateEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireActivity())
        )[CreateEventViewModel::class.java]

        setupImagePreview()
        setupInputNonText()
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
        model.currentImageUri.observe(viewLifecycleOwner){
            Log.d("Image URI", "showImage: $it")
            binding.ivCreatePost.setImageURI(it)
        }
    }

    private fun setupCategoryDropDown(){
        model.category.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
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
        binding.btnPost.setOnClickListener {
            with(binding){
                model.setEvent(
                    EventResult(
                    title = eventNameEditText.text.toString(),
                    category = categoryDropDown.text.toString(),
                    description = eventDescriptionEditText.text.toString(),
                    fullAddress = eventLocationEditText.text.toString(),
                    date = eventStartDateEditText.text.toString()
                )
                )
            }
            val fragmentManager = parentFragmentManager.beginTransaction()
            fragmentManager.replace(com.eventurecapstone.eventure.R.id.fragment_container, MapsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
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
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                editText.setText(time)
            }, hour, minute, true
        )
        timePickerDialog.show()
    }
}