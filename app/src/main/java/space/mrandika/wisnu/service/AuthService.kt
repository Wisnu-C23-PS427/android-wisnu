package space.mrandika.wisnu.service

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import space.mrandika.wisnu.model.auth.LoginResponse
import space.mrandika.wisnu.model.auth.RegisterRequest
import space.mrandika.wisnu.model.auth.RegisterResponse

interface AuthService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}