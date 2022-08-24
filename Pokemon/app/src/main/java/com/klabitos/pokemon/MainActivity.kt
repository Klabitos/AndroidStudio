package com.klabitos.pokemon

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.klabitos.pokemon.databinding.ActivityMainBinding
import com.klabitos.pokemon.sharedPreferences.SharedApp
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.maxScore.text= "Highscore: ${SharedApp.prefs.maxHighscore}, ${SharedApp.prefs.NAME}"
        listeners()
    }

    override fun onResume() {
        super.onResume()
        binding.maxScore.text= "Highscore: ${SharedApp.prefs.maxHighscore}, ${SharedApp.prefs.NAME}"
    }

    private fun listeners(){
        binding.btnPlay.setOnClickListener{ play() }
    }

    private fun play(){
        val intent = Intent(this, PlayWindow::class.java) //Creamos un intent con el contexto y el nombre de la pantalla/actividad a la que queremos ir
        startActivity(intent)
    }


}