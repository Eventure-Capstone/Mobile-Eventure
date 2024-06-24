package com.eventurecapstone.eventure.view.event_card

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eventurecapstone.eventure.databinding.EventCardListBinding
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.view.detail.DetailActivity

class EventCardListAdapter(private val eventList: List<EventResult>): RecyclerView.Adapter<EventCardListAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding =EventCardListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(holder.itemView.context, eventList[position])
    }

    inner class EventViewHolder(private val binding: EventCardListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.eventCardList.setOnClickListener {
                val context = binding.eventCardList.context
                val toDetail = Intent(context, DetailActivity::class.java).apply {
                    putExtra("id_story", binding.eventId.text.toString())
                }
                context.startActivity(toDetail)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(context: Context, event: EventResult) {
            // TODO: foto event belum
            binding.eventId.text = event.id
            binding.title.text = event.title
            binding.location.text = event.city
            binding.date.text = event.date
            binding.category.text = event.category
//            Glide.with(context)
//                .load(event.pictureUrl)
//                .into(binding.image)
        }
    }
}