package com.eventurecapstone.eventure

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.repository.DataStoreRepository
import com.eventurecapstone.eventure.repository.dataStore
import com.eventurecapstone.eventure.view.dashboard.profile.ProfileViewModel
import com.eventurecapstone.eventure.view.splash.SplashViewModel

class ViewModelFactory(
    private val pref: DataStoreRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(pref) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(pref) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                val pref = DataStoreRepository.getInstance(context.dataStore)
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}