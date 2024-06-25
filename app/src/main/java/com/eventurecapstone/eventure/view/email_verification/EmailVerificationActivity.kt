package com.eventurecapstone.eventure.view.email_verification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.databinding.ActivityEmailVerificationBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.login.LoginActivity

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

        model.email = intent.getStringExtra("email") ?: ""

        setupVerifyButton()
        showLoading()
    }

    private fun setupVerifyButton(){
        binding.btnVerify.setOnClickListener {
            val code = binding.verificationEmailEditText.text
            model.verify(code.toString())
        }

        model.isSuccess.observe(this){
            if (it){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.verificationEmailEditTextLayout.error = "gagal varifikasi"
            }
        }
    }

    private fun showLoading() {
        model.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}