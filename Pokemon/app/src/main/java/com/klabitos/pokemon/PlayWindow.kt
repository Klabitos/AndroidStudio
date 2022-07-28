package com.klabitos.pokemon

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioRecord.MetricsConstants.SOURCE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.klabitos.pokemon.databinding.ActivityPlayWindowBinding
import com.klabitos.pokemon.model.Pokemon
import com.klabitos.pokemon.service.API_Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.util.concurrent.atomic.AtomicInteger

class PlayWindow : AppCompatActivity() {

    private lateinit var binding: ActivityPlayWindowBinding
    private var chosenPokemon = -1
    private var listOfPosibleAnswers = arrayListOf<Int>()
    private var listOfBtn= arrayListOf<Button>()
    private var indexCounter : AtomicInteger = AtomicInteger(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getLAllPokemonThisRound()
    }

    private fun init(){
        binding.goBack.setOnClickListener{ goBack() }
        listOfBtn = arrayListOf<Button>(binding.btnName1, binding.btnName2, binding.btnName3, binding.btnName4);
    }

    private fun getLAllPokemonThisRound(){
        chosenPokemon = (0..3).random()
        listOfPosibleAnswers = arrayListOf<Int>()
        for(i in 1..4){
            listOfPosibleAnswers.add((0..649).random())
        }
        for(pokemon in listOfPosibleAnswers){
            getSinglePokemon(pokemon)
        }
        loadImages()
        Log.e("listOfShown", listOfPosibleAnswers.toString())
        Log.e("chosen", chosenPokemon.toString())
    }

    private fun goBack(){
        val intent = Intent(this, MainActivity::class.java) //Creamos un intent con el contexto y el nombre de la pantalla/actividad a la que queremos ir
        startActivity(intent)
    }

    private fun loadImages(){
        loadImage(this.resources.openRawResource(R.raw.backgroundplay), binding.imgbackgroundGuess)
        loadImage(this.resources.openRawResource(R.raw.pokeball), binding.imgbackgroundpokeball)
        binding.imgPokemon.loadSvg("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${listOfPosibleAnswers[chosenPokemon]}.svg")
    }


    private fun loadImage(image: InputStream, idBindingImage: ImageView){
        val bitmap = BitmapFactory.decodeStream(image)
        idBindingImage.setImageBitmap(bitmap)
    }



    fun AppCompatImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }




    private fun getSinglePokemon(id:Int){

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(API_Service::class.java).getAPokemon(id.toString())
            val pokemonResponse = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    val pokemonName = pokemonResponse?.name ?: "To be defined"
                    Log.e("name", pokemonName)
                    listOfBtn[indexCounter.getAndAdd(1)].text = pokemonName
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/") //importante que la base acabae en /
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}