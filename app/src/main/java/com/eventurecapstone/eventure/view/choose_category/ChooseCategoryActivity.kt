package com.eventurecapstone.eventure.view.choose_category

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eventurecapstone.eventure.databinding.ActivityChooseCategoryBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity

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

    private fun setupView() {
        //TODO: implement view setup
    }

    private fun setupUser() {
        //TODO: implement user setup
    }

    private fun setupData() {
        //TODO: implement data setup
    }

    private fun setupAction() {
        //TODO: implement action setup
        binding.btnChooseCategory.setOnClickListener {
            val selectedCategories = model.selectedCategories.value
            if (!selectedCategories.isNullOrEmpty()) {
                model.updateCategory()
            } else {
                Toast.makeText(this, "Choose at least one category", Toast.LENGTH_SHORT).show()
            }
        }

        model.isSuccess.observe(this){
            if (it == true){
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setupAdapter() {
        model.categoryList.observe(this){
            adapter = ChooseCategoryAdapter(it, model, this)
            binding.rvChooseCategory.adapter = adapter
        }
        adapter = ChooseCategoryAdapter(listOf(), model, this)
        binding.rvChooseCategory.adapter = adapter
        binding.rvChooseCategory.layoutManager = GridLayoutManager(this, 2)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {
        model.selectedCategories.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }

}