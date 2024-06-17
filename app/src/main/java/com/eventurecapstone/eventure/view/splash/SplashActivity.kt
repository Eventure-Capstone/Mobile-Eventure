package com.eventurecapstone.eventure.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.helper.RequestLocation
import com.eventurecapstone.eventure.view.dashboard.DashboardActivity
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var model: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(this, ViewModelFactory.getInstance(this))[SplashViewModel::class.java]

        checkNightMode()
        checkLanguage()
        checkLocation()

        viewForwarding()
    }

    private fun checkNightMode() {
        val currentTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isNightModeActive = currentTheme == Configuration.UI_MODE_NIGHT_YES

        model.systemTheme.observe(this){
            if (it == null) {
                model.setThemeToNight(isNightModeActive)
            } else {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            model.markJobAsDone(theme = true)
        }
    }

    private fun checkLanguage() {
        model.systemLanguage.observe(this){
            if (it == null) model.setLanguage(resources.configuration.locales[0])
            model.markJobAsDone(language = true)
        }
    }

    private fun checkLocation(){
        lifecycleScope.launch {
            val coordinate = RequestLocation.getLocation(this@SplashActivity)
            if (coordinate != null){
                model.setCoordinate(coordinate)
            }
            model.markJobAsDone(location = true)
        }
    }

    private fun viewForwarding(){
        lateinit var nextIntent: Intent

        model.token.observe(this){
            nextIntent = if (it.isNullOrBlank()){
                //Intent(this, WelcomeScreenActivity::class.java)
                Intent(this, DashboardActivity::class.java)
            } else {
                Intent(this, DashboardActivity::class.java)
            }
            model.markJobAsDone(token = true)
        }

        model.doneSignal.observe(this){
            with(model.doneList){
                val language = this["language"] ?: false
                val theme = this["theme"] ?: false
                val location = this["location"] ?: false
                val token = this["token"] ?: false
                if (language && theme && location && token){
                    startActivity(nextIntent)
                    finish()
                }
            }
        }
    }

    companion object {
        const val TAG = "SplashScreenActivity"
    }
}