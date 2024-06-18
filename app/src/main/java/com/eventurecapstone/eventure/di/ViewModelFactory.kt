package com.eventurecapstone.eventure.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.data.pref.dataStore
import com.eventurecapstone.eventure.view.change_password.ChangePasswordViewModel
import com.eventurecapstone.eventure.view.choose_interest.choose_category.ChooseCategoryViewModel
import com.eventurecapstone.eventure.view.choose_interest.choose_event.ChooseEventViewModel
import com.eventurecapstone.eventure.view.create_post.CreatePostViewModel
import com.eventurecapstone.eventure.view.dashboard.explorer.ExplorerViewModel
import com.eventurecapstone.eventure.view.dashboard.profile.ProfileViewModel
import com.eventurecapstone.eventure.view.detail.DetailViewModel
import com.eventurecapstone.eventure.view.edit_profile.EditProfileViewModel
import com.eventurecapstone.eventure.view.login.LoginViewModel
import com.eventurecapstone.eventure.view.register.RegisterViewModel
import com.eventurecapstone.eventure.view.splash.SplashViewModel

class ViewModelFactory(
    private val pref: UserPreference
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(pref) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(pref) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(pref) as T
            modelClass.isAssignableFrom(ChooseCategoryViewModel::class.java) -> ChooseCategoryViewModel(pref) as T
            modelClass.isAssignableFrom(ChooseEventViewModel::class.java) -> ChooseEventViewModel(pref) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(pref) as T
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> EditProfileViewModel(pref) as T
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel(pref) as T
            modelClass.isAssignableFrom(CreatePostViewModel::class.java) -> CreatePostViewModel(pref) as T
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