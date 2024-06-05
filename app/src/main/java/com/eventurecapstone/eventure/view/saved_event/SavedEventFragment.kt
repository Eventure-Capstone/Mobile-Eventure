package com.eventurecapstone.eventure.view.saved_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.databinding.FragmentSavedEventBinding
import com.eventurecapstone.eventure.view.shared.EventCardListAdapter

class SavedEventFragment : Fragment() {

    private var _binding: FragmentSavedEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val savedEventViewModel =
            ViewModelProvider(this)[SavedEventViewModel::class.java]

        _binding = FragmentSavedEventBinding.inflate(inflater, container, false)

        savedEventViewModel.events.observe(viewLifecycleOwner) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
        val layoutManager = LinearLayoutManager(context)
        binding.rvEvent.layoutManager = layoutManager

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}