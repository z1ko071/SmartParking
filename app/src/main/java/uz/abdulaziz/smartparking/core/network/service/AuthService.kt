package uz.abdulaziz.smartparking.core.network.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import uz.abdulaziz.smartparking.core.model.edit.ProfileUpdateRequest
import uz.abdulaziz.smartparking.core.model.login.LoginRequest
import uz.abdulaziz.smartparking.core.model.login.LoginResponse
import uz.abdulaziz.smartparking.core.model.profile.ProfileResponse
import uz.abdulaziz.smartparking.core.model.register.RegisterResponse

interface AuthService {

    @POST("/user/login/")
    suspend fun loginUser(@Body data: LoginRequest): Response<LoginResponse?>

    @POST("/user/register/")
    suspend fun registerUser(@Body data: LoginRequest): Response<RegisterResponse?>

    @GET("/user/details/{id}/")
    suspend fun getUserProfile(@Path("id") id: Int): Response<ProfileResponse?>

    @PATCH("/user/profile/update/{id}/")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Body body: ProfileUpdateRequest
    ): Response<Any?>


}