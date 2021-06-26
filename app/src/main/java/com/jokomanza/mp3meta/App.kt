package com.jokomanza.mp3meta

import android.app.Application
import ealvatag.tag.TagOptionSingleton

class App : Application() {

    override fun onCreate() {
        TagOptionSingleton.getInstance().isAndroid = true
        super.onCreate()
    }
}