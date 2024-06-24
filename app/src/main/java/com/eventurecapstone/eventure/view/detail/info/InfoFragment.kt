package com.eventurecapstone.eventure.view.detail.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.databinding.FragmentInfoBinding
import com.eventurecapstone.eventure.view.detail.DetailViewModel

class InfoFragment : Fragment() {
    private lateinit var model: DetailViewModel
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireActivity())
        )[DetailViewModel::class.java]

        attachDataToView()
        setupSaveButton()
    }

    private fun attachDataToView(){
        model.event.observe(viewLifecycleOwner){
            with(binding){
                locationText.text = it.fullAddress
                dateText.text = it.date
                eventDescription.text = it.description ?: "no description"
            }
        }
    }

    private fun setupSaveButton(){
        model.eventIsSaved.observe(viewLifecycleOwner){ eventIsSaved ->
            binding.saveButton.setOnClickListener {
                if (eventIsSaved) {
                    model.unSaveEvent()
                } else {
                    model.saveEvent()
                }
            }
            binding.saveButton.text = if (eventIsSaved){
                getString(R.string.saved)
            } else {
                getString(R.string.save)
            }
        }
    }
}