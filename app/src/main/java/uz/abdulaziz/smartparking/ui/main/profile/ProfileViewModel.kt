package uz.abdulaziz.smartparking.ui.main.profile

import androidx.lifecycle.MutableLiveData
import uz.abdulaziz.smartparking.core.base.BaseViewModel
import uz.abdulaziz.smartparking.core.model.profile.ProfileResponse
import uz.abdulaziz.smartparking.core.repositroy.AuthRepository
import uz.salikhdev.ecology.core.util.ResultWrapper

class ProfileViewModel : BaseViewModel() {


    private val repository = AuthRepository()
    val userProfileDataLD = MutableLiveData<ProfileResponse>()


    fun getProfileData(id: Int) {

        launch {

            when (val response = repository.getUserProfileData(id)) {
                is ResultWrapper.ErrorResponse -> {

                }

                is ResultWrapper.NetworkError -> {

                }

                is ResultWrapper.Success -> {
                    response.response?.let {
                        userProfileDataLD.value = it
                    }
                }
            }


        }

    }


}