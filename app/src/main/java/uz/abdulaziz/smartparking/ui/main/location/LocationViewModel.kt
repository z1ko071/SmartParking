package uz.abdulaziz.smartparking.ui.main.location

import androidx.lifecycle.MutableLiveData
import uz.abdulaziz.smartparking.core.base.BaseViewModel
import uz.abdulaziz.smartparking.core.model.MessageResponse
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponse
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponseItem
import uz.abdulaziz.smartparking.core.model.road.DirectionsResponse
import uz.abdulaziz.smartparking.core.repositroy.DirectionsRepository
import uz.abdulaziz.smartparking.core.repositroy.ParkingRepository
import uz.salikhdev.ecology.core.util.ResultWrapper

class LocationViewModel : BaseViewModel() {

    private val repository = ParkingRepository()
    private val repositoryMap = DirectionsRepository()
    val parkingList: MutableLiveData<ParkingResponse> = MutableLiveData()
    val parkingDetail: MutableLiveData<ParkingResponseItem> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val errorLD: MutableLiveData<Int> = MutableLiveData()
    val enterParkingLD: MutableLiveData<MessageResponse> = MutableLiveData()
    val exitParkingLD: MutableLiveData<MessageResponse> = MutableLiveData()
    val routLD: MutableLiveData<DirectionsResponse> = MutableLiveData()


    fun getParking() {

        launch {

            when (val result = repository.getParking()) {
                is ResultWrapper.ErrorResponse -> {
                    error.value = result.error.toString()
                }

                is ResultWrapper.NetworkError -> {
                    error.value = "INTERNET_ERROR"
                }

                is ResultWrapper.Success -> {
                    result.response?.let { parkingList.value = it }
                }
            }

        }

    }

    fun getParkingDetail(id: Int) {

        launch {

            when (val result = repository.getDetailParking(id)) {
                is ResultWrapper.ErrorResponse -> {
                    error.value = result.error.toString()
                }

                is ResultWrapper.NetworkError -> {
                    error.value = "INTERNET_ERROR"
                }

                is ResultWrapper.Success -> {
                    result.response?.let { parkingDetail.value = it }
                }
            }

        }

    }

    fun enterParking(id: Int, isElectro: String) {

        launch {

            when (val result = repository.enterParking(id, isElectro)) {
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
                        enterParkingLD.value = it
                    }
                }
            }

        }

    }

    fun exitParking(id: Int, isElectro: String) {

        launch {

            when (val result = repository.exitParking(id, isElectro)) {
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
                        exitParkingLD.value = it
                    }
                }
            }

        }

    }

    fun getMapData(from: String, to: String) {

        launch {
            when (val result = repositoryMap.getMapData(from, to)) {
                is ResultWrapper.ErrorResponse -> println("error")
                is ResultWrapper.NetworkError -> println("error")
                is ResultWrapper.Success -> {
                    result.response?.let {
                        routLD.value = it
                    }

                }
            }

        }

    }


}