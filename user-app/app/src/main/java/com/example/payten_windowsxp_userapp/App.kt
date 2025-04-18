package com.example.payten_windowsxp_userapp

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("393173f8-e6b7-473f-9f78-c731275da8d7")
    }
}