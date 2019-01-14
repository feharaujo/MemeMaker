package com.fearaujo.mememaker

import androidx.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds
import com.squareup.leakcanary.LeakCanary

class AppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this, ADMOB_ID)

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}