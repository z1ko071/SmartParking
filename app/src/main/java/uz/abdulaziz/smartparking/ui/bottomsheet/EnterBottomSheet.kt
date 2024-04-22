package uz.abdulaziz.smartparking.ui.bottomsheet

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.core.view.isGone
import coil.load
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.core.model.parking.ParkingResponseItem
import uz.abdulaziz.smartparking.core.utils.gone
import uz.abdulaziz.smartparking.core.utils.visible
import uz.abdulaziz.smartparking.databinding.BottomSheedLayoutBinding
import uz.abdulaziz.smartparking.ui.dialog.PayDialog


class EnterBottomSheet(context: Context) : BottomSheetDialog(context) {

    private val binding by lazy { BottomSheedLayoutBinding.inflate(layoutInflater) }
    private var isElectro = "False"
    private var localStorage: LocalStorage
    var onEnterButton: ((id: Int, isElectro: String, latLng: LatLng) -> Unit)? = null
    var onExitButton: ((id: Int) -> Unit)? = null

    var dialogYes: (() -> Unit)? = null

    init {
        localStorage = LocalStorage(context)
        setContentView(binding.root)
        binding.normal.setOnClickListener {
            isElectro = "False"
        }
        binding.electro.setOnClickListener {
            isElectro = "True"
        }
        show()
    }

    @SuppressLint("SetTextI18n")
    fun setData(model: ParkingResponseItem) {
        binding.loader.gone()
        binding.imageDialog.load("https://parking.salikhdev.uz/${model.image}") {
            placeholder(
                R.drawable.placeholder
            )
        }
        binding.titleDialog.text = model.title
        binding.phoneDialog.text = "Tel: ${model.phoneNumber}"
        binding.ratingDialog.rating = model.rating.toFloat()
        binding.carCount.text = "Parking car count : ${model.emptyCount}/${model.maxCount}"
        binding.elctrocarCount.text =
            "Parking electro car count : ${model.emptyElectroCount}/${model.electroCount}"

        if (model.maxCount == model.emptyCount) {
            binding.carCount.setTextColor(Color.parseColor("#F75555"))
        } else {
            binding.carCount.setTextColor(Color.parseColor("#4ADE80"))
        }

        if (model.electroCount == model.emptyElectroCount) {
            binding.elctrocarCount.setTextColor(Color.parseColor("#F75555"))
        } else {
            binding.elctrocarCount.setTextColor(Color.parseColor("#4ADE80"))
        }

        if (model.electroCount == 0) {
            binding.electro.gone()
        }

        if (localStorage.isParking) {
            binding.radioGroup.isGone = true
        }

        if (localStorage.isParking && model.id == localStorage.parkingId.toInt()) {
            binding.enter.text = "Exit Parking"
            binding.enter.setBackgroundResource(R.drawable.exit_to_shape)
            binding.enter.isClickable = true
        } else if (!localStorage.isParking) {
            binding.enter.text = "Enter Parking"
            binding.enter.setBackgroundResource(R.drawable.go_to_shape)
            binding.enter.isClickable = true
        } else {
            binding.enter.text = "You already is parking"
            binding.enter.setBackgroundResource(R.drawable.alrady_to_shape)
            binding.enter.isClickable = false
        }


        binding.enter.setOnClickListener {


            if (localStorage.isParking && model.id == localStorage.parkingId.toInt()) {
                localStorage.isParking = false
                localStorage.parkingId = ""
                binding.loader.visible()
                onExitButton?.invoke(model.id)
            } else if (!localStorage.isParking) {

                if (localStorage.cardNum.isBlank()) {

                    val dialog = PayDialog(context)
                    dialog.onYesClick = {
                        dialogYes?.invoke()
                    }
                    dialog.show()

                } else {
                    localStorage.isParking = true
                    localStorage.parkingId = model.id.toString()
                    localStorage.isElectro = isElectro
                    binding.loader.visible()
                    onEnterButton?.invoke(
                        model.id,
                        localStorage.isElectro,
                        LatLng(model.lat.toDouble(), model.lon.toDouble())
                    )
                }
            }
        }

    }

}