package com.eventurecapstone.eventure.view.email_verification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.databinding.ActivityEmailVerificationBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity

class EmailVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailVerificationBinding
    private lateinit var model: EmailVerificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[EmailVerificationViewModel::class.java]

        setupVerifyButton()
    }

    private fun setupVerifyButton(){
        binding.btnVerify.setOnClickListener {
            val code = binding.verificationEmailEditText.text
            model.verify(code.toString())
        }

        model.isLoginSuccess.observe(this){
            if (it){
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.verificationEmailEditTextLayout.error = "gagal varifikasi"
            }
        }
    }
}