package com.klabitos.pokemon.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_NAME = "com.klabitos.pokemon.sharedPreferences"  //ES UNA CLAVE PARA NOMBRE FICHERO
    val HIGHSCORE = "100"
    val NAME = "Klabitos"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var maxHighscore: String? //SOBREESCRIBIR GETTER SETTER
        get() = prefs.getString(HIGHSCORE, "")
        set(value) = prefs.edit().putString(HIGHSCORE, value).apply()

    var name: String? //SOBREESCRIBIR GETTER SETTER
        get() = prefs.getString(NAME, "")
        set(value) = prefs.edit().putString(NAME, value).apply()
}