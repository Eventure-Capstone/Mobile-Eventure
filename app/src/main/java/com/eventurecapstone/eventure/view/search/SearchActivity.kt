package com.eventurecapstone.eventure.view.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var model: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SearchViewModel::class.java]

        setupActionBar()
        setupSearchView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.show()
        binding.searchView.requestFocusAndShowKeyboard()
        binding.iconButton.setOnClickListener {
            finish()
        }
    }

    private fun setupSearchView(){
        binding.searchView.editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.searchBar.setText(binding.searchView.text)
                binding.searchView.hide()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}