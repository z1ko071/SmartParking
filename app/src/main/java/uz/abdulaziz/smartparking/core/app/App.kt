package uz.abdulaziz.smartparking.core.app

import android.app.Application

class App : Application() {

    companion object {

        var instance: App? = null
            private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}