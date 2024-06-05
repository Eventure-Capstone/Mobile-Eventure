package com.eventurecapstone.eventure.view.explorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.databinding.FragmentExplorerBinding
import com.eventurecapstone.eventure.view.shared.EventCardListAdapter

class ExplorerFragment : Fragment() {

    private var _binding: FragmentExplorerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[ExplorerViewModel::class.java]

        _binding = FragmentExplorerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.events.observe(viewLifecycleOwner) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
        val layoutManager = LinearLayoutManager(context)
        binding.rvEvent.layoutManager = layoutManager

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}