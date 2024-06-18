package com.eventurecapstone.eventure.view.choose_interest.choose_event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.databinding.ItemChooseEventBinding

class ChooseEventAdapter(
    private val events: List<Event>,
    private val viewModel: ChooseEventViewModel
) : RecyclerView.Adapter<ChooseEventAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: ItemChooseEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.ivEventTitle.text = event.title

            // Observe the selection changes
            viewModel.selectedItems.observeForever { selectedItems ->
                if (selectedItems.contains(event)) {
                    binding.layoutEvent.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green_70))
                } else {
                    binding.layoutEvent.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.transparent))
                }
            }

            // Handle item click
            binding.root.setOnClickListener {
                viewModel.toggleSelection(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemChooseEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size
}