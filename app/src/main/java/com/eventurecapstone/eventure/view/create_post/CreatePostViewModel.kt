package com.eventurecapstone.eventure.view.create_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.repository.PreferenceRepository

class CreatePostViewModel(private val preferenceRepository: PreferenceRepository): ViewModel() {

    private val _post = MutableLiveData<Event>()
    val post: LiveData<Event> get() = _post

    fun createPost(
        pictureUri: String,
        eventName: String,
        category: String,
        description: String,
        location: String,
        date: String,
        time: String,
        postLink: String
    ) {
        _post.value = Event(
            0,
            eventName,
            pictureUri,
            location,
            0.1,
            0.2,
            date,
            null,
            time,
            null,
            description,
            null,
            category,
            postLink )
    }
}