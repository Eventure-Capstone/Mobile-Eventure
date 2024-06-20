package com.eventurecapstone.eventure.view.create_event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.view.create_event.form.FormAddEventFragment

class CreateEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        setupActionBar()

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, FormAddEventFragment())
            commit()
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
}