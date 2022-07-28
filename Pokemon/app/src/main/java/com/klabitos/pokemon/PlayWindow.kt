package com.klabitos.pokemon

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
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

        binding.goBack.setOnClickListener{ goBack() }

        init()
        getLAllShownPokemonRound()
        loadImages()

        for((index,pokemon) in listOfPosibleAnswers.withIndex()){
            getSinglePokemon(pokemon)
        }

    }

    private fun init(){
        listOfBtn = arrayListOf<Button>(binding.btnName1, binding.btnName2, binding.btnName3, binding.btnName4);
    }

    private fun getLAllShownPokemonRound(){
        chosenPokemon = (0..4).random()
        listOfPosibleAnswers = arrayListOf<Int>()
        for(i in 1..4){
            listOfPosibleAnswers.add((0..649).random())
        }
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
        loadImage(this.resources.openRawResource(R.raw.test), binding.imgPokemon)
    }


    private fun loadImage(image: InputStream, idBindingImage: ImageView){
        val bitmap = BitmapFactory.decodeStream(image)
        idBindingImage.setImageBitmap(bitmap)
    }

    private fun getSinglePokemon(id:Int){

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(API_Service::class.java).getAPokemon(id.toString())
            val pokemonResponse = call?.body()
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