package com.eventurecapstone.eventure.view.my_post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.databinding.ActivityMyPostBinding
import com.eventurecapstone.eventure.view.shared.EventCardListAdapter

class MyPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPostBinding
    private lateinit var myPostViewModel: MyPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myPostViewModel = ViewModelProvider(this)[MyPostViewModel::class.java]

        setupActionBar()

        setupEventList()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    private fun setupEventList() {
        myPostViewModel.events.observe(this) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rvEvent.layoutManager = layoutManager
    }
}