package com.eventurecapstone.eventure.view.register

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
import com.eventurecapstone.eventure.databinding.ActivityRegisterBinding
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.email_verification.EmailVerificationActivity
import com.eventurecapstone.eventure.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var model: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        model = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[RegisterViewModel::class.java]

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
        binding.btnRegister.setOnClickListener {
            val name = binding.registerNameEditText.text.toString()
            val email = binding.registerEmailEditText.text.toString()
            val password = binding.registerPasswordEditText.text.toString()

            when {
                name.isEmpty() -> {
                    binding.registerNameEditTextLayout.error = "Name must be filled"
                    binding.registerNameEditTextLayout.requestFocus()
                }
                email.isEmpty() -> {
                    binding.registerEmailEditTextLayout.error = "Email must be filled"
                    binding.registerEmailEditTextLayout.requestFocus()
                }
                password.isEmpty() -> {
                    binding.registerPasswordEditTextLayout.error = "Password must be filled"
                    binding.registerPasswordEditTextLayout.requestFocus()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.registerPasswordEditTextLayout.error = "Invalid email format"
                    return@setOnClickListener
                }
                else -> {
                    model.email = email
                    model.register(name, email, password)
                }
            }
        }

        binding.tvRegisterToLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        model.isSuccess.observe(this){
            if (it) {
                intent = Intent(this@RegisterActivity, EmailVerificationActivity::class.java)
                intent.putExtra("email", model.email)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                binding.registerEmailEditTextLayout.error = "Something went wrong, please try again"
            }
        }
    }

    private fun showLoading() {
        model.isLoading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setupAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvRegisterTitle, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.tvRegisterTextName, View.ALPHA, 1f).setDuration(500)
        val nameInput = ObjectAnimator.ofFloat(binding.registerNameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tvRegisterTextEmail, View.ALPHA, 1f).setDuration(500)
        val emailInput = ObjectAnimator.ofFloat(binding.registerEmailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tvRegisterTextPassword, View.ALPHA, 1f).setDuration(500)
        val passwordInput = ObjectAnimator.ofFloat(binding.registerPasswordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val button = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val registerText = ObjectAnimator.ofFloat(binding.tvRegisterToLoginText, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.tvRegisterToLogin, View.ALPHA, 1f).setDuration(500)

        val inputAnimator = AnimatorSet().apply {
            playTogether(name, nameInput, email, emailInput, password, passwordInput)
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