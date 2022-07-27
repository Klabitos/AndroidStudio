package com.klabitos.pokemon

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klabitos.pokemon.databinding.ActivityMainBinding
import com.klabitos.pokemon.databinding.ActivityPlayWindowBinding
import java.io.InputStream

class PlayWindow : AppCompatActivity() {

    private lateinit var binding: ActivityPlayWindowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadImages()
    }

    private fun loadImages(){
        val imageBck: InputStream = this.resources.openRawResource(R.raw.backgroundplay)
        val imagePokeBall: InputStream = this.resources.openRawResource(R.raw.pokeball)
        val imagePokemon: InputStream = this.resources.openRawResource(R.raw.test)
        val bitmap1 = BitmapFactory.decodeStream(imageBck)
        val bitmap2 = BitmapFactory.decodeStream(imagePokeBall)
        val bitmap3 = BitmapFactory.decodeStream(imagePokemon)
        binding.imgbackgroundGuess.setImageBitmap(bitmap1)
        binding.imgbackgroundpokeball.setImageBitmap(bitmap2)
        binding.imgPokemon.setImageBitmap(bitmap3)

    }
}