package com.eventurecapstone.eventure.view.choose_interest.choose_event

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.databinding.ActivityChooseEventBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity

class ChooseEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseEventBinding
    private lateinit var adapter: ChooseEventAdapter
    private lateinit var model: ChooseEventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChooseEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = ViewModelProvider(this, ViewModelFactory.getInstance(this)
        )[ChooseEventViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUser()
        setupData()
        setupAction()
        setupAdapter()
        setupObserver()
    }

    fun setupUser() {
        //TODO: implement user setup
    }

    fun setupData() {
        //TODO: implement data setup
    }

    fun setupAction() {
        //TODO: implement action setup
        binding.btnChooseEvent.setOnClickListener {
            val selectedItems = model.selectedItems.value
            if (!selectedItems.isNullOrEmpty()) {
                Toast.makeText(this, "Selected: ${selectedItems.joinToString { it.title }}", Toast.LENGTH_SHORT).show()
                // Delay for 2 seconds before navigating to the main activity
                Handler(Looper.getMainLooper()).postDelayed({
                    // Intent to navigate to the MainActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: Call finish() if you want to close the current activity
                }, 2000)
                // You can also pass the data to another activity or fragment
            } else {
                Toast.makeText(this, "Choose at least one category", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setupAdapter() {
        val events = listOf(
            Event(
                id = 1,
                title = "testing 1",
                location = "Lendah, Kulon Progo",
                latitude = -7.924970,
                longitude = 110.192390,
                startDate = "15-06-2024",
                startTime = "14.00",
                category = "concert",
                description = "lorem ipsum wae lah wkwkwk"
            ),
            Event(
                id = 2,
                title = "testing 2",
                location = "Lendah, Kulon Progo",
                latitude = -7.924970,
                longitude = 110.192390,
                startDate = "15-06-2024",
                startTime = "14.00",
                category = "concert",
                description = "lorem ipsum wae lah wkwkwk"
            ),
            Event(
                id = 3,
                title = "testing 3",
                location = "Lendah, Kulon Progo",
                latitude = -7.924970,
                longitude = 110.192390,
                startDate = "15-06-2024",
                startTime = "14.00",
                category = "concert",
                description = "lorem ipsum wae lah wkwkwk"
            )
        )

        adapter = ChooseEventAdapter(events, model)
        binding.rvChooseEvent.apply {
            adapter = this@ChooseEventActivity.adapter
            layoutManager = GridLayoutManager(this@ChooseEventActivity, 2)
        }
    }

    fun setupObserver() {
        model.selectedItems.observe(this) { _ ->
            adapter.notifyDataSetChanged()
        }
    }
}