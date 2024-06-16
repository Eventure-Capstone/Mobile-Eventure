package com.eventurecapstone.eventure.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity
import com.eventurecapstone.eventure.view.welcome_screen.WelcomeScreenActivity
import java.util.Locale

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var model: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(this, ViewModelFactory.getInstance(this))[SplashViewModel::class.java]

        preferenceShouldNotNull()
        viewForwarding()
    }

    private fun checkNightMode(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun checkLanguage(): Locale {
        return resources.configuration.locales[0]
    }

    private fun viewForwarding(){
        val nextIntent = if (true){
            Intent(this, DashboardActivity::class.java)
        } else {
            Intent(this, WelcomeScreenActivity::class.java)
        }
        model.doneSignal.observe(this){
            if (model.languageDone && model.themeDone){
                startActivity(nextIntent)
                finish()
            }
        }
    }

    private fun preferenceShouldNotNull(){
        model.systemTheme.observe(this){
            if (it == null) {
                model.setThemeToNight(checkNightMode())
            } else {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            model.markJobAsDone(theme = true)
        }

        model.systemLanguage.observe(this){
            if (it == null) model.setLanguage(checkLanguage())
            model.markJobAsDone(language = true)
        }
    }

    companion object {
        const val TAG = "SplashScreenActivity"
    }
}