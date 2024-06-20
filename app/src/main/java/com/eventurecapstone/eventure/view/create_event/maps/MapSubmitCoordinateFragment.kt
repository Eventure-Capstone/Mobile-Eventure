package com.eventurecapstone.eventure.view.create_event.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.databinding.MapSubmitCoordinateBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.create_event.CreateEventViewModel

class MapSubmitCoordinateFragment: Fragment() {
    private var _binding: MapSubmitCoordinateBinding? = null
    private val binding get() = _binding!!
    private lateinit var model: CreateEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapSubmitCoordinateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireActivity())
        )[CreateEventViewModel::class.java]

        binding.btnPost.setOnClickListener {
            model.submitEvent()
        }

        model.isSuccess.observe(requireActivity()){
            if (it) {
                requireActivity().finish()
            }
        }
    }
}