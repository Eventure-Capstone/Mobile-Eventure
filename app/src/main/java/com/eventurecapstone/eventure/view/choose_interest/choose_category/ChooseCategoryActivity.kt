package com.eventurecapstone.eventure.view.choose_interest.choose_category

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eventurecapstone.eventure.databinding.ActivityChooseCategoryBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.login.LoginActivity

class ChooseCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseCategoryBinding
    private lateinit var adapter: ChooseCategoryAdapter
    private lateinit var model: ChooseCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = ViewModelProvider(this, ViewModelFactory.getInstance(this)
        )[ChooseCategoryViewModel::class.java]

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
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: Call finish() if you want to close the current activity
                }, 1000)
                // You can also pass the data to another activity or fragment
            } else {
                Toast.makeText(this, "Choose at least one category", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setupAdapter() {
        model.categoryList.observe(this){
            adapter = ChooseCategoryAdapter(it, model)
            binding.rvChooseCategory.adapter = adapter
        }
        adapter = ChooseCategoryAdapter(listOf(), model)
        binding.rvChooseCategory.adapter = adapter
        binding.rvChooseCategory.layoutManager = GridLayoutManager(this, 2)
    }

    fun setupObserver() {
        model.selectedCategories.observe(this) { _ ->
            adapter.notifyDataSetChanged()
        }
    }

}