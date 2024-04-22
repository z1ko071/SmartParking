package uz.abdulaziz.smartparking.core.utils

import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment


fun log(message: Any) {
    Log.d("TAGaaaa", "log : $message")
}


fun View.gone() {
    this.isGone = true
}


fun View.visible() {
    this.isVisible = true
}


fun Fragment.setItemStatusBarColor(@ColorInt color: Int, darkStatusBarTint: Boolean) {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return

    val window: Window = (requireActivity().window).also {
        it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        it.statusBarColor = color
    }

    val decor = window.decorView
    if (darkStatusBarTint) {
        decor.systemUiVisibility = decor.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        decor.systemUiVisibility = 0
    }

}
