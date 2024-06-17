package com.eventurecapstone.eventure.view.dashboard.saved_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.databinding.FragmentSavedEventBinding
import com.eventurecapstone.eventure.view.event_card.EventCardListAdapter

class SavedEventFragment : Fragment() {
    private lateinit var model: SavedEventViewModel
    private var _binding: FragmentSavedEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this)[SavedEventViewModel::class.java]

        setupFilterButton()
        setupRvEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupFilterButton() {
        val upcomingEvent = binding.buttonUpcoming
        val pastEvent = binding.buttonPastEvent

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    R.id.button_upcoming -> model.setEventStatus(SavedEventViewModel.ButtonState.UPCOMING)
                    R.id.button_past_event -> model.setEventStatus(SavedEventViewModel.ButtonState.PAST)
                }
            }
        }

        model.eventStatus.observe(viewLifecycleOwner){
            when(it) {
                SavedEventViewModel.ButtonState.UPCOMING -> {
                    upcomingEvent.isChecked = true
                    pastEvent.isChecked = false
                    model.fetchUpcomingEvent()
                }
                SavedEventViewModel.ButtonState.PAST -> {
                    upcomingEvent.isChecked = false
                    pastEvent.isChecked = true
                    model.fetchPastEvent()
                }
                else -> {}
            }
        }
    }

    private fun setupRvEvent() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvEvent.layoutManager = layoutManager

        model.events.observe(viewLifecycleOwner) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
    }
}