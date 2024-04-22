package uz.abdulaziz.smartparking.core.repositroy

import kotlinx.coroutines.Dispatchers
import uz.abdulaziz.smartparking.core.model.edit.ProfileUpdateRequest
import uz.abdulaziz.smartparking.core.model.login.LoginRequest
import uz.abdulaziz.smartparking.core.model.login.LoginResponse
import uz.abdulaziz.smartparking.core.model.profile.ProfileResponse
import uz.abdulaziz.smartparking.core.model.register.RegisterResponse
import uz.abdulaziz.smartparking.core.network.ApiClient
import uz.salikhdev.ecology.core.util.ResultWrapper
import uz.salikhdev.ecology.core.util.parseResponse

class AuthRepository {


    private val service = ApiClient.createAuthService()

    suspend fun login(data: LoginRequest): ResultWrapper<LoginResponse?, Any> {
        return parseResponse(Dispatchers.IO) {
            service.loginUser(data)
        }
    }

    suspend fun registration(data: LoginRequest): ResultWrapper<RegisterResponse?, Any> {
        return parseResponse(Dispatchers.IO) {
            service.registerUser(data)
        }
    }

    suspend fun getUserProfileData(id: Int): ResultWrapper<ProfileResponse?, Any> {
        return parseResponse(Dispatchers.IO) {
            service.getUserProfile(id)
        }
    }

    suspend fun updateProfile(id: Int, user: ProfileUpdateRequest): ResultWrapper<Any?, Any> {
        return parseResponse(Dispatchers.IO) {
            service.updateProfile(id, user)
        }
    }

}