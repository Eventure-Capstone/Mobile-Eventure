package com.eventurecapstone.eventure.view.dashboard.explorer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.ViewModelFactory
import com.eventurecapstone.eventure.databinding.FragmentExplorerBinding
import com.eventurecapstone.eventure.view.search.SearchActivity
import com.eventurecapstone.eventure.view.shared.EventCardListAdapter

class ExplorerFragment : Fragment() {
    private lateinit var model: ExplorerViewModel
    private var _binding: FragmentExplorerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExplorerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireActivity())
        )[ExplorerViewModel::class.java]

        setupSearchBar()
        setupRvEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSearchBar(){
        binding.searchBar.setOnClickListener {
            val searchIntent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(searchIntent)
        }
    }

    private fun setupRvEvent(){
        model.fetchEvent()

        val layoutManager = LinearLayoutManager(context)
        binding.rvEvent.layoutManager = layoutManager

        model.events.observe(viewLifecycleOwner) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
    }
}