package uz.abdulaziz.smartparking.core.cache

import android.content.Context
import uz.abdulaziz.smartparking.core.utils.BooleanPreference
import uz.abdulaziz.smartparking.core.utils.StringPreference

class LocalStorage(context: Context) {


    private val KEY = "SDAFASFD24534T2EGFDSD3OI4JFEPOIJEF0394TGJPEOIWJ"
    private val securePref = context.getSharedPreferences("shared_cache", Context.MODE_PRIVATE)

    var onBoarding: Boolean by BooleanPreference(securePref, true)
    var isFirst: Boolean by BooleanPreference(securePref, true)
    var parkingId: String by StringPreference(securePref, "")
    var isParking: Boolean by BooleanPreference(securePref, false)
    var isElectro: String by StringPreference(securePref, "False")
    var user_id: String by StringPreference(securePref, "")


    // card
    var cardHolder: String by StringPreference(securePref, "")
    var cardNum: String by StringPreference(securePref, "")
    var cardDate: String by StringPreference(securePref, "")
    var cardCVV: String by StringPreference(securePref, "")

}