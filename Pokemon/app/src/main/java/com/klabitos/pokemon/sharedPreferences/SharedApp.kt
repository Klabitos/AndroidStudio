package com.klabitos.pokemon.sharedPreferences

import android.app.Application

class SharedApp: Application() {
    companion object { //static
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}