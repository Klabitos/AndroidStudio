package com.klabitos.pokemon

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.klabitos.pokemon.databinding.ActivityMainBinding
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeners()
        loadImages()
    }

    private fun listeners(){
        binding.btnPlay.setOnClickListener{ play() }
    }

    private fun play(){
        val intent = Intent(this, PlayWindow::class.java) //Creamos un intent con el contexto y el nombre de la pantalla/actividad a la que queremos ir
        startActivity(intent)
    }

    private fun loadImages(){
        val imageStream: InputStream = this.resources.openRawResource(R.raw.whoisthat)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        binding.imgMenu.setImageBitmap(bitmap)
    }
}