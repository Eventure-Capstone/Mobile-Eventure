package com.eventurecapstone.eventure.view.choose_interest.choose_category

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eventurecapstone.eventure.data.entity.Category
import com.eventurecapstone.eventure.databinding.ActivityChooseCategoryBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.choose_interest.choose_event.ChooseEventActivity

class ChooseCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseCategoryBinding
    private lateinit var adapter: ChooseCategoryAdapter
    private lateinit var model: ChooseCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChooseCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = ViewModelProvider(this, ViewModelFactory.getInstance(this)
        )[ChooseCategoryViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupView()
        setupUser()
        setupData()
        setupAction()
        setupAdapter()
        setupObserver()
    }

    fun setupView() {
        //TODO: implement view setup
    }

    fun setupUser() {
        //TODO: implement user setup
    }

    fun setupData() {
        //TODO: implement data setup
    }

    fun setupAction() {
        //TODO: implement action setup
        binding.btnChooseCategory.setOnClickListener {
            val selectedCategories = model.selectedCategories.value
            if (!selectedCategories.isNullOrEmpty()) {
                Toast.makeText(this, "Selected: ${selectedCategories.joinToString { it.name }}", Toast.LENGTH_SHORT).show()
                // Delay for 2 seconds before navigating to the main activity
                Handler(Looper.getMainLooper()).postDelayed({
                    // Intent to navigate to the MainActivity
                    val intent = Intent(this, ChooseEventActivity::class.java)
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
        val categories = listOf(
            Category(1, "Category 1"),
            Category(2, "Category 2"),
            Category(3, "Category 3")
        )
        adapter = ChooseCategoryAdapter(categories, model)
        binding.rvChooseCategory.layoutManager = GridLayoutManager(this, 2)
        binding.rvChooseCategory.adapter = adapter
    }

    fun setupObserver() {
        model.selectedCategories.observe(this) { _ ->
            adapter.notifyDataSetChanged()
        }
    }

}