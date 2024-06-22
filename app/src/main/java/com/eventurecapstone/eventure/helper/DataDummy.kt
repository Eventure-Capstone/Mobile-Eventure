package com.eventurecapstone.eventure.helper

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.data.entity.EventDetailResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.entity.Login
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.network.event.entity.Recommend
import com.eventurecapstone.eventure.data.network.event.entity.RecommendResponse
import com.eventurecapstone.eventure.data.network.user.entity.Category
import com.eventurecapstone.eventure.data.network.user.entity.CategoryResponse
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

        val x = Event(
            id = 1,
            title = "Festival Kota Lama",
            location = "Semarang - Jl. Letjen Suprapto No.59, Tj. Mas, Kec. Semarang Utara, Kota Semarang, Jawa Tengah 50174\\n, Semarang, Indonesia",
            startDate = "2024-09-24",
            startTime = "19.00",
            latitude = -6.9681868,
            longitude = 110.4284722,
            category = "Budaya",
            pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "no description"
        )

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
            event = x
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
        event.id = eventList[eventList.size-1].id?.plus(1)
        event.pictureUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s"
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

    fun getSavedEvent(): RecommendResponse? {
        val data = emptyList<Recommend>()

        return RecommendResponse(
            success = true,
            message = "ldadsa",
            data = data
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

    fun verifyEmail(token: String?, code: String): BasicResponse? {
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

    fun getCategory(): CategoryResponse? {
        return CategoryResponse(
            success = true,
            message = "",
            data = listOf(
                Category(
                    id = "d0f4f9e4-1c37-4051-9dc1-54e78ca20257",
                    name = "Lainnya",
                    pictureUrl = "https://i.imgur.com/ebfGG3y.png"
                ),
                Category(
                    id = "54018ce6-fd6d-4ab7-9199-1ad587def43b",
                    name = "Wisata",
                    pictureUrl = "https://i.imgur.com/sjP24KK.png"
                ),
                Category(
                    id = "959da3da-82de-42ae-b8a5-4dcde38e2f65",
                    name = "Bisnis",
                    pictureUrl = "https://i.imgur.com/PUSv5Yh.png"
                ),
                Category(
                    id = "e584a5a8-3b51-4137-a3de-a43cf064dc63",
                    name = "Budaya",
                    pictureUrl = "https://i.imgur.com/FalDzlJ.png"
                ),
                Category(
                    id = "0136bbed-c131-4f94-ad41-dfaf0a5a9740",
                    name = "Hiburan",
                    pictureUrl = "https://i.imgur.com/NwUuSpn.png"
                ),
                Category(
                    id = "8a88558b-0ee0-4459-ada7-6c7215b1a60e",
                    name = "Olahraga",
                    pictureUrl = "https://i.imgur.com/8yMaH1D.png"
                ),
                Category(
                    id = "e519e4fd-ff70-4dc5-8a7c-2065c177fbdf",
                    name = "Musik",
                    pictureUrl = "https://i.imgur.com/6wtRV3E.png"
                ),
                Category(
                    id = "7409910e-fed7-4c49-a380-4b5f4dc56fab",
                    name = "Edukasi",
                    pictureUrl = "https://i.imgur.com/lySSZxj.png"
                ),
            ))
    }
}