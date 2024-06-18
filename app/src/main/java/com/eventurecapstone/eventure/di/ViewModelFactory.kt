package com.eventurecapstone.eventure.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.pref.dataStore
import com.eventurecapstone.eventure.data.repository.EventRepository
import com.eventurecapstone.eventure.data.repository.PreferenceRepository
import com.eventurecapstone.eventure.data.repository.UserRepository
import com.eventurecapstone.eventure.view.dashboard.explorer.ExplorerViewModel
import com.eventurecapstone.eventure.view.dashboard.profile.ProfileViewModel
import com.eventurecapstone.eventure.view.dashboard.saved_event.SavedEventViewModel
import com.eventurecapstone.eventure.view.detail.DetailViewModel
import com.eventurecapstone.eventure.view.my_post.MyPostViewModel
import com.eventurecapstone.eventure.view.search.SearchViewModel
import com.eventurecapstone.eventure.view.splash.SplashViewModel

class ViewModelFactory(
    private val prefRepo: PreferenceRepository,
    private val eventRepo: EventRepository,
    private val userRepo: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(prefRepo) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(prefRepo) as T
            modelClass.isAssignableFrom(ExplorerViewModel::class.java) -> ExplorerViewModel(prefRepo, eventRepo) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(prefRepo, eventRepo) as T
            modelClass.isAssignableFrom(SavedEventViewModel::class.java) -> SavedEventViewModel(eventRepo) as T
            modelClass.isAssignableFrom(MyPostViewModel::class.java) -> MyPostViewModel(eventRepo) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(eventRepo) as T
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
                val prefRepo = PreferenceRepository.getInstance(pref)
                val eventRepo = EventRepository.getInstance(pref)
                val userRepo = UserRepository.getInstance(pref)

                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(prefRepo, eventRepo, userRepo)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}