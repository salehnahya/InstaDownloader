package com.galaxy.instadownloader

import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.MobileAds
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {
            Log.e("TAG","initialize")
        }

    }
}
