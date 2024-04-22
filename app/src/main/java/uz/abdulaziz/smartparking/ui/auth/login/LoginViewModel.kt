package uz.abdulaziz.smartparking.ui.auth.login

import androidx.lifecycle.MutableLiveData
import uz.abdulaziz.smartparking.core.base.BaseViewModel
import uz.abdulaziz.smartparking.core.model.login.LoginRequest
import uz.abdulaziz.smartparking.core.model.login.LoginResponse
import uz.abdulaziz.smartparking.core.repositroy.AuthRepository
import uz.salikhdev.ecology.core.util.ResultWrapper

class LoginViewModel : BaseViewModel() {

    private val repository = AuthRepository()
    val successLD = MutableLiveData<LoginResponse>()
    val errorLD = MutableLiveData<Int>()

    fun login(data: LoginRequest) {

        launch {
            when (val result = repository.login(data)) {
                is ResultWrapper.ErrorResponse -> {
                    result.code?.let {
                        errorLD.value = it
                    }
                }

                is ResultWrapper.NetworkError -> {
                    errorLD.value = 800
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