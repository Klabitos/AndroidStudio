package com.klabitos.pokemon

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioRecord.MetricsConstants.SOURCE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
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
    private var chosenPokemon = 0
    private var listOfPosibleAnswers = arrayListOf<Int>()
    private var listOfBtn= arrayListOf<Button>()
    private var correctName= ""
    private var indexCounter : AtomicInteger = AtomicInteger(0)
    private var score=0
    private var clicked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        loadImages()
        getAllPokemonThisRound()
    }
    private fun init(){
        score=0
        binding.score.text="Score: $score"
        binding.goBack.setOnClickListener{ goHome(this) }
        listOfBtn = arrayListOf<Button>(binding.btnName1, binding.btnName2, binding.btnName3, binding.btnName4);
        for(button in listOfBtn){
            button.setOnClickListener { comprobarCorrecto(button) }
        }
    }


    fun getAllPokemonThisRound(){
        esconderTodasCards()
        indexCounter.set(0)
        chosenPokemon = (0..3).random()
        listOfPosibleAnswers.clear()
        listOfPosibleAnswers = arrayListOf<Int>()
        for(i in 1..4){
            listOfPosibleAnswers.add((0..649).random())
        }
        for(i in 0..3){
            getSinglePokemon(listOfPosibleAnswers[i])
        }
        binding.imgPokemon.loadSvg("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${listOfPosibleAnswers[chosenPokemon]}.svg")

    }

    private fun loadImages(){
        Utils().loadImage(this.resources.openRawResource(R.raw.backgroundplay), binding.imgbackgroundGuess)
        Utils().loadImage(this.resources.openRawResource(R.raw.pokeball), binding.imgbackgroundpokeball)

    }

    private fun getSinglePokemon(id:Int){
        Log.e("id", id.toString())
        CoroutineScope(Dispatchers.IO).launch {
            val call = Utils().getRetrofit().create(API_Service::class.java).getAPokemon(id.toString())
            val pokemonResponse = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    val pokemonName = pokemonResponse?.name ?: "To be defined"
                    if(id.toString()==listOfPosibleAnswers[chosenPokemon].toString())correctName=pokemonName
                    listOfBtn[indexCounter.getAndAdd(1)].text = pokemonName //al ser atomica espera a usarse para luego añadir lo que sea

                }
            }
        }
    }

    private fun esconderTodasCards(){
        binding.imgPokemon.brightness=0F;
        clicked=false

    }


    private fun comprobarCorrecto(button: Button){
        if(!clicked){
            clicked=true;
            binding.imgPokemon.brightness=1F;
            showDialog(button.text.toString()==correctName)
        }
    }


    private fun showDialog(haveWon : Boolean){
        AlertDialog.Builder(this).setCancelable(true).apply {
            val v = LayoutInflater.from(this@PlayWindow).inflate(R.layout.item_message, null, false)
            if (haveWon) score+=100
            v.findViewById<TextView>(R.id.scoreText).text="Score: $score"
            binding.score.text="Score: $score"
            v.findViewById<TextView>(R.id.goodAnswered).text = getString(if (haveWon) R.string.you_win else R.string.you_lose)
            setView(v) //d¡
            setOnDismissListener { getAllPokemonThisRound() }
        }.show()
    }

    private fun goHome(context: Context){
        val intent = Intent(context, MainActivity::class.java) //Creamos un intent con el contexto y el nombre de la pantalla/actividad a la que queremos ir
        startActivity(intent)
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

}