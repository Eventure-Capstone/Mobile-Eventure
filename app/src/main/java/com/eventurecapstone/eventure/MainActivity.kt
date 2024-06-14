package com.eventurecapstone.eventure


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eventurecapstone.eventure.databinding.ActivityMainBinding
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToDashboard.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}