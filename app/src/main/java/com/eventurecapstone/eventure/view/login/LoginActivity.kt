package com.eventurecapstone.eventure.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.databinding.ActivityLoginBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity
import com.eventurecapstone.eventure.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var model: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[LoginViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        setupAction()
        setupAnimation()
        showLoading()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmailEditText.text.toString()
            val password = binding.loginPasswordEditText.text.toString()
            if (email.isEmpty()) {
                binding.loginEmailEditTextLayout.error = "Email cannot be empty"
                return@setOnClickListener
            } else {
                model.login(email, password)
            }
        }

        binding.tvLoginToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        model.isSuccess.observe(this){
            if (it) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.loginEmailEditTextLayout.error = "email or password is invalid"
            }
        }
    }

    private fun showLoading() {
        model.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setupAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvLoginTitle, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tvLoginTextEmail, View.ALPHA, 1f).setDuration(500)
        val emailInput = ObjectAnimator.ofFloat(binding.loginEmailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tvLoginTextPassword, View.ALPHA, 1f).setDuration(500)
        val passwordInput = ObjectAnimator.ofFloat(binding.loginPasswordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val registerText = ObjectAnimator.ofFloat(binding.tvLoginToRegisterText, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.tvLoginToRegister, View.ALPHA, 1f).setDuration(500)

        val inputAnimator = AnimatorSet().apply {
            playTogether(email, emailInput, password, passwordInput)
        }

        val registerAnimator = AnimatorSet().apply {
            playTogether(registerText, register)
        }

        AnimatorSet().apply {
            playSequentially(title, inputAnimator, button, registerAnimator)
            start()
        }
    }
}