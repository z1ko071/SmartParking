package uz.abdulaziz.smartparking.ui.auth.register

import androidx.lifecycle.MutableLiveData
import uz.abdulaziz.smartparking.core.base.BaseViewModel
import uz.abdulaziz.smartparking.core.model.login.LoginRequest
import uz.abdulaziz.smartparking.core.model.register.RegisterResponse
import uz.abdulaziz.smartparking.core.repositroy.AuthRepository
import uz.salikhdev.ecology.core.util.ResultWrapper

class RegisterViewModel : BaseViewModel() {

    private val repository = AuthRepository()
    val successLD = MutableLiveData<RegisterResponse>()
    val errorLD = MutableLiveData<String>()

    fun register(data: LoginRequest) {

        launch {
            when (val result = repository.registration(data)) {
                is ResultWrapper.ErrorResponse -> {
                    errorLD.value = result.error.toString()
                }

                is ResultWrapper.NetworkError -> {
                    errorLD.value = "INTERNET_ERROR"
                }

                is ResultWrapper.Success -> {
                    result.response?.let {
                        successLD.value = it
                    }
                }
            }
        }

    }


}