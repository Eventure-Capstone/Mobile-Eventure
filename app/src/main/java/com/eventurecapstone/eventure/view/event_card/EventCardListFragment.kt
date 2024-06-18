package com.eventurecapstone.eventure.view.event_card

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.eventurecapstone.eventure.data.entity.Event

import com.eventurecapstone.eventure.databinding.EventCardListBinding
import com.eventurecapstone.eventure.view.detail.DetailActivity

class EventCardListFragment: Fragment() {
    private var _binding: EventCardListBinding? = null
    private val binding get() = _binding!!
    private var event: Event? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventCardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventId.text = event?.id.toString()
        binding.title.text = event?.title
        binding.location.text = event?.location
        binding.date.text = event?.startDate
        Glide.with(requireContext())
            .load(event?.pictureUrl)
            .into(binding.image)

        binding.eventCardList.setOnClickListener {
            val toDetail = Intent(context, DetailActivity::class.java).apply {
                putExtra("id_story", event?.id)
            }
            startActivity(toDetail)
        }
    }

    fun showData(event: Event){
        this.event = event
    }
}