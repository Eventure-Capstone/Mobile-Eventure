package com.eventurecapstone.eventure.view.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventurecapstone.eventure.databinding.ActivitySearchBinding
import com.eventurecapstone.eventure.view.shared.EventCardListAdapter

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var model: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SearchViewModel::class.java]

        setupSearchBar()
        setupBackAction()
        setupRvEvent()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupSearchBar() {
        with(binding.searchView){
            setupWithSearchBar(binding.searchBar)
            show()
            requestFocusAndShowKeyboard()

            editText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    model.setSearchValue(text.toString())
                    hide()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        model.searchValue.observe(this){
            binding.searchBar.setText(it)
            model.fetchEventBySearch(it)
        }
    }

    private fun setupBackAction(){
        binding.iconButton.setOnClickListener { finish() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (binding.searchView.isShowing) {
                    binding.searchView.hide()
                } else {
                    finish()
                }
            }
        })

        binding.searchView.viewTreeObserver.addOnGlobalLayoutListener {
            val isSearchViewVisible = binding.searchView.isShowing
            val isSearchBarIsEmpty = model.searchValue.value == null
            if (!isSearchViewVisible && isSearchBarIsEmpty){
               finish()
            }
        }
    }

    private fun setupRvEvent(){
        val layoutManager = LinearLayoutManager(this)
        binding.rvEvent.layoutManager = layoutManager

        model.events.observe(this) {
            binding.rvEvent.adapter = EventCardListAdapter(it)
        }
    }
}