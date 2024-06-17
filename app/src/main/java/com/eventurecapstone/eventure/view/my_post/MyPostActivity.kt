package com.eventurecapstone.eventure.view.my_post

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.databinding.ActivityMyPostBinding
import com.eventurecapstone.eventure.view.create_post.CreatePostActivity
import com.eventurecapstone.eventure.view.event_card.EventCardListAdapter

class MyPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPostBinding
    private lateinit var model: MyPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[MyPostViewModel::class.java]

        setupActionBar()
        setupRvEvent()

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    private fun setupRvEvent() {
        model.fetchMyEventList()

        val layoutManager = LinearLayoutManager(this)
        binding.rvEvent.layoutManager = layoutManager

        model.events.observe(this) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
    }
}