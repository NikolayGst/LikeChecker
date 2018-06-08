package com.niko.likechecker

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.vk.sdk.VKSdk
import io.fabric.sdk.android.Fabric

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(this)
        Fabric.with(this, Crashlytics())
    }

}