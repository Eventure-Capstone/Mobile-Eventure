package com.eventurecapstone.eventure.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.pref.dataStore
import com.eventurecapstone.eventure.view.dashboard.explorer.ExplorerViewModel
import com.eventurecapstone.eventure.view.dashboard.profile.ProfileViewModel
import com.eventurecapstone.eventure.view.detail.DetailViewModel
import com.eventurecapstone.eventure.view.splash.SplashViewModel

class ViewModelFactory(
    private val pref: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(pref) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(pref) as T
            modelClass.isAssignableFrom(ExplorerViewModel::class.java) -> ExplorerViewModel(pref) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(pref) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                val pref = UserPreference.getInstance(context.dataStore)
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}