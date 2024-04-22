package uz.abdulaziz.smartparking.ui.edit_profile

import androidx.lifecycle.MutableLiveData
import uz.abdulaziz.smartparking.core.base.BaseViewModel
import uz.abdulaziz.smartparking.core.model.edit.ProfileUpdateRequest
import uz.abdulaziz.smartparking.core.model.profile.ProfileResponse
import uz.abdulaziz.smartparking.core.repositroy.AuthRepository
import uz.salikhdev.ecology.core.util.ResultWrapper

class EditProfileViewModel : BaseViewModel() {


    private val repository = AuthRepository()
    val userProfileDataLD = MutableLiveData<ProfileResponse>()
    val succedData = MutableLiveData<Any>()
    val error = MutableLiveData<Any>()


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

    fun updateProfile(id: Int, profileData: ProfileUpdateRequest) {

        launch {
            when (val resutl = repository.updateProfile(id, profileData)) {
                is ResultWrapper.ErrorResponse -> {
                    error.value = resutl.error.toString()
                }

                is ResultWrapper.NetworkError -> {
                    error.value = (800).toString()
                }

                is ResultWrapper.Success -> {
                    resutl.response?.let {
                        succedData.value = it
                    }
                }
            }
        }


    }


}