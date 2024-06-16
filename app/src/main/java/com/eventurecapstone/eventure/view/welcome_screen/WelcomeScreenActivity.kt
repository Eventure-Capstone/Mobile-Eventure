package com.eventurecapstone.eventure.view.welcome_screen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.databinding.ActivityWelcomeScreenBinding

class WelcomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupUser()
        setupView()
        setupAction()
        setupAnimation()
    }

    private fun setupUser() {
        // TODO: Implement user setup logic here
    }

    private fun setupView() {
        // TODO: Implement view setup logic here
    }

    private fun setupAction() {
        // TODO: Implement action setup logic here
    }

    private fun setupAnimation() {
        // TODO: Implement animation setup logic here
    }
}