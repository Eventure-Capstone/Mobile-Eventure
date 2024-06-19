package com.eventurecapstone.eventure.helper

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.entity.EventDetailResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.entity.Login
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.repository.UserRepository
import okhttp3.MultipartBody

object DataDummy {

    private fun haveToken(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    private var eventList = mutableListOf(
        Event(
            id = 1,
            title = "taylor swift",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.924970,
            longitude = 110.142390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        Event(
            id = 2,
            title = "BMTH",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.964970,
            longitude = 110.192390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        Event(
            id = 3,
            title = "A7F",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.924970,
            longitude = 110.112390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        Event(
            id = 4,
            title = "SO7",
            location = "indonesia",
            startDate = "20-02-2024",
            startTime = "19.00",
            latitude = -7.934970,
            longitude = 110.152390,
            category = "concert",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
    )

    fun getEvent(token: String?): EventResponse?{
        if (!haveToken(token)){
            return EventResponse(
                success = false,
                message = "denied, no token for authentication",
                event = null
            )
        }
        return EventResponse(
            success = true,
            message = "getting data successfully",
            event = eventList
        )
    }

    fun getEventDetail(token: String?, id: Int): EventDetailResponse? {
        if (!haveToken(token)){
            return EventDetailResponse(
                success = false,
                message = "denied, no token for authentication",
                event = null
            )
        }
        return EventDetailResponse(
            success = true,
            message = "getting event detail for id $id",
            event = eventList[id-1]
        )
    }

    fun addNewEvent(token: String?, event: Event): EventDetailResponse? {
        if (!haveToken(token)){
            return EventDetailResponse(
                success = false,
                message = "denied, no token for authentication",
                event = null
            )
        }
        eventList.add(event)
        return EventDetailResponse(
            success = true,
            message = "add event success",
            event = eventList[eventList.size - 1]
        )
    }

    fun deleteEvent(token: String?, id: Int): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList.removeAt(id-1)
        return BasicResponse(
            success = true,
            message = "delete data successfully"
        )
    }

    fun updateEvent(token: String?, id: Int, event: Event) : BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList[id - 1] = event
        return BasicResponse(
            success = true,
            message = "update data successfully"
        )
    }

    fun removeFromFavorite(token: String?, id: Int): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList[id - 1].favorite = false
        return BasicResponse(
            success = true,
            message = "remove data from favorite successfully"
        )
    }

    fun addToFavorite(token: String?, id: Int): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList[id - 1].favorite = true
        return BasicResponse(
            success = true,
            message = "remove data from favorite successfully"
        )
    }

    data class MyProfile(
        val id: Int,
        var name: String,
        var email: String,
        var password: String,
        var isVerify: Boolean,
        val photoUrl: String,
    )

    private var myProfile = MyProfile(
        id = 1,
        name = "john",
        email = "john@gmail.com",
        password = "12341234",
        isVerify = false,
        photoUrl = "https://t3.ftcdn.net/jpg/00/67/73/14/360_F_67731473_GAsdRUCBh7XEhM3X0tpzbIYDgHirJAgP.jpg"
    )

    fun login(login: UserRepository.Login): LoginResponse? {
        if (login.email == myProfile.email && login.password == myProfile.password){
            return LoginResponse(
                success = true,
                message = "login successfully",
                loginResult = Login(
                    name = myProfile.name,
                    userId = myProfile.id,
                    email = myProfile.email,
                    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSW1hbSBTdWJla3RpIiwiaWQiOjEyM30.1xdkYtdRNZ54dzckraIyqQvQakvhhD8n7I7R22ldDRk"
                )
            )
        }
        return LoginResponse(
            success = false,
            message = "login failed",
            loginResult = null
        )
    }

    fun register(register: UserRepository.Register): BasicResponse? {
        if (register.email == myProfile.email){
            return BasicResponse(
                success = false,
                message = "create new account failed, email already exist"
            )
        }
        return BasicResponse(
            success = true,
            message = "create new account successfully"
        )
    }

    fun updateProfile(token: String?, profile: UserRepository.Profile, photo: MultipartBody.Part? = null): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        myProfile.email = profile.email
        myProfile.name = profile.name
        return BasicResponse(
            success = true,
            message = "update profile successfully"
        )
    }

    fun updatePassword(token: String?, password: String): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        myProfile.password = password
        return BasicResponse(
            success = true,
            message = "update password successfully"
        )
    }

    fun verifyAccount(token: String?, verifyAccount: UserRepository.VerifyAccount): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        return BasicResponse(
            success = true,
            message = "verify account successfully"
        )
    }

    fun setInterestByCategory(token: String?, categories: List<String>): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        return BasicResponse(
            success = true,
            message = "update interest successfully"
        )
    }

    fun setInterestByEvent(token: String?, events: List<String>): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        return BasicResponse(
            success = true,
            message = "update interest successfully"
        )
    }
}