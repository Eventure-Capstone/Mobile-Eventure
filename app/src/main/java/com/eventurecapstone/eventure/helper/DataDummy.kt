package com.eventurecapstone.eventure.helper

import com.eventurecapstone.eventure.data.entity.BasicResponse
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.entity.EventSingleResponse
import com.eventurecapstone.eventure.data.entity.EventResponse
import com.eventurecapstone.eventure.data.entity.LoginResponse
import com.eventurecapstone.eventure.data.entity.LoginResult
import com.eventurecapstone.eventure.data.entity.Recommend
import com.eventurecapstone.eventure.data.entity.RecommendResponse
import com.eventurecapstone.eventure.data.entity.PreferenceResponse
import com.eventurecapstone.eventure.data.entity.PreferenceResult
import com.eventurecapstone.eventure.data.repository.UserRepository
import okhttp3.MultipartBody

object DataDummy {

    private fun haveToken(token: String?): Boolean {
        return !token.isNullOrBlank()
    }

    private var eventList = mutableListOf(
        EventResult(
            id = "1",
            title = "taylor swift",
            city = "indonesia",
            date = "20-02-2024",
            latitude = -7.924970,
            longitude = 110.142390,
            category = "concert",
            banner = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        EventResult(
            id = "2",
            title = "BMTH",
            city = "indonesia",
            date = "20-02-2024",
            latitude = -7.964970,
            longitude = 110.192390,
            category = "concert",
            banner = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        EventResult(
            id = "3",
            title = "A7F",
            city = "indonesia",
            date = "20-02-2024",
            latitude = -7.924970,
            longitude = 110.112390,
            category = "concert",
            banner = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
        EventResult(
            id = "4",
            title = "SO7",
            city = "indonesia",
            date = "20-02-2024",
            latitude = -7.934970,
            longitude = 110.152390,
            category = "concert",
            banner = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "lorem ipsum aje lah ya wkwkwk"
        ),
    )

    fun getEvent(token: String?): EventResponse?{
        if (!haveToken(token)){
            return EventResponse(
                success = false,
                message = "denied, no token for authentication",
                data = null
            )
        }
        return EventResponse(
            success = true,
            message = "getting data successfully",
            data = eventList
        )
    }

    fun getEventDetail(token: String?, id: String): EventSingleResponse? {

        val x = EventResult(
            id = "1",
            title = "Festival Kota Lama",
            fullAddress = "Semarang - Jl. Letjen Suprapto No.59, Tj. Mas, Kec. Semarang Utara, Kota Semarang, Jawa Tengah 50174\\n, Semarang, Indonesia",
            date = "2024-09-24",
            latitude = -6.9681868,
            longitude = 110.4284722,
            category = "Budaya",
            banner = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s",
            description = "no description"
        )

        if (!haveToken(token)){
            return EventSingleResponse(
                success = false,
                message = "denied, no token for authentication",
                data = null
            )
        }
        return EventSingleResponse(
            success = true,
            message = "getting event detail for id $id",
            data = x
        )
    }

    fun addNewEvent(token: String?, event: EventResult): EventSingleResponse? {
        if (!haveToken(token)){
            return EventSingleResponse(
                success = false,
                message = "denied, no token for authentication",
                data = null
            )
        }
        event.id = eventList[eventList.size-1].id?.plus(1)
        event.banner = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRiEJCyHvhAbVrcte8Eqcb5WG_RO0Rnwid7A&s"
        eventList.add(event)
        return EventSingleResponse(
            success = true,
            message = "add event success",
            data = eventList[eventList.size - 1]
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

    fun updateEvent(token: String?, id: Int, event: EventResult) : BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList[0] = event
        return BasicResponse(
            success = true,
            message = "update data successfully"
        )
    }

    fun removeFromFavorite(token: String?, id: String): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList[0].favorite = false
        return BasicResponse(
            success = true,
            message = "remove data from favorite successfully"
        )
    }

    fun addToFavorite(token: String?, id: String): BasicResponse? {
        if (!haveToken(token)){
            return BasicResponse(
                success = false,
                message = "denied, no token for authentication"
            )
        }
        eventList[0].favorite = true
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
                data = LoginResult(
                    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSW1hbSBTdWJla3RpIiwiaWQiOjEyM30.1xdkYtdRNZ54dzckraIyqQvQakvhhD8n7I7R22ldDRk"
                )
            )
        }
        return LoginResponse(
            success = false,
            message = "login failed",
            data = null
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

    fun getCategory(): PreferenceResponse? {
        return PreferenceResponse(
            success = true,
            message = "",
            data = listOf(
                PreferenceResult(
                    id = "d0f4f9e4-1c37-4051-9dc1-54e78ca20257",
                    name = "Lainnya",
                    pictureUrl = "https://i.imgur.com/ebfGG3y.png"
                ),
                PreferenceResult(
                    id = "54018ce6-fd6d-4ab7-9199-1ad587def43b",
                    name = "Wisata",
                    pictureUrl = "https://i.imgur.com/sjP24KK.png"
                ),
                PreferenceResult(
                    id = "959da3da-82de-42ae-b8a5-4dcde38e2f65",
                    name = "Bisnis",
                    pictureUrl = "https://i.imgur.com/PUSv5Yh.png"
                ),
                PreferenceResult(
                    id = "e584a5a8-3b51-4137-a3de-a43cf064dc63",
                    name = "Budaya",
                    pictureUrl = "https://i.imgur.com/FalDzlJ.png"
                ),
                PreferenceResult(
                    id = "0136bbed-c131-4f94-ad41-dfaf0a5a9740",
                    name = "Hiburan",
                    pictureUrl = "https://i.imgur.com/NwUuSpn.png"
                ),
                PreferenceResult(
                    id = "8a88558b-0ee0-4459-ada7-6c7215b1a60e",
                    name = "Olahraga",
                    pictureUrl = "https://i.imgur.com/8yMaH1D.png"
                ),
                PreferenceResult(
                    id = "e519e4fd-ff70-4dc5-8a7c-2065c177fbdf",
                    name = "Musik",
                    pictureUrl = "https://i.imgur.com/6wtRV3E.png"
                ),
                PreferenceResult(
                    id = "7409910e-fed7-4c49-a380-4b5f4dc56fab",
                    name = "Edukasi",
                    pictureUrl = "https://i.imgur.com/lySSZxj.png"
                ),
            ))
    }
}