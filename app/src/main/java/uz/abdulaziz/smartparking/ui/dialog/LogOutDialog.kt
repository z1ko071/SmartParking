package uz.abdulaziz.smartparking.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import uz.abdulaziz.smartparking.R


class LogOutDialog(context: Context) : Dialog(context) {

    var onYesClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.log_out_dialog)
        val v = window!!.decorView
        v.setBackgroundResource(android.R.color.transparent)

        findViewById<TextView>(R.id.yes_btn).setOnClickListener {
            onYesClick?.invoke()
            dismiss()
        }

        findViewById<TextView>(R.id.no_btn).setOnClickListener {
            dismiss()
        }
    }


    override fun onBackPressed() {
        cancel()
        super.onBackPressed()
    }


}
