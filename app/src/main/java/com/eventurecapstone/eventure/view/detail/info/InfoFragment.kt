package com.eventurecapstone.eventure.view.detail.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.ViewModelFactory
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
    }

    private fun attachDataToView(){
        model.event.observe(viewLifecycleOwner){
            with(binding){
                locationText.text = it.location
                dateText.text = if (it.endDate != null) {
                    "${it.startDate} sampai ${it.endDate}"
                } else {
                    it.startDate
                }
                timeText.text = if (it.endTime != null) {
                    "${it.startTime} sampai ${it.endTime}"
                } else {
                    it.startTime
                }
                eventDescription.text = it.description
            }
        }
    }
}