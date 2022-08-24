package com.klabitos.pokemon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.klabitos.pokemon.databinding.ActivityPlayWindowBinding
import com.klabitos.pokemon.service.API_Service
import com.klabitos.pokemon.sharedPreferences.SharedApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            val highscore = SharedApp.prefs.maxHighscore!! <score.toString()
            if (haveWon){
                val v = LayoutInflater.from(this@PlayWindow).inflate(R.layout.correct_message, null, false)
                score+=100
                v.findViewById<TextView>(R.id.scoreText).text="Score: $score"
                setView(v)
                setOnDismissListener { getAllPokemonThisRound() }
            }else{
                val v = LayoutInflater.from(this@PlayWindow).inflate(R.layout.fail_message, null, false)
                var personHighscore = v.findViewById<TextView>(R.id.personHighscore)
                if(highscore) {
                    v.findViewById<TextView>(R.id.newRecord).visibility=View.VISIBLE
                    personHighscore.visibility=View.VISIBLE
                }
                v.findViewById<TextView>(R.id.scoreText).text="Score: $score"
                v.findViewById<TextView>(R.id.correctAnswer).text="The correct answer was: ${Utils().capitalizeFirstLetter(correctName)}"

                setView(v)

                setOnDismissListener {
                    if(highscore) newHighscore(personHighscore.text.toString())
                    finish()
                }
            }
            binding.score.text="Score: $score"
        }.show()
    }

    private fun newHighscore(name:String){
        Log.e("Nombre",name)
        SharedApp.prefs.maxHighscore=score.toString()
        if(name!=""){
            SharedApp.prefs.name=name
        }else{
            SharedApp.prefs.name="DEFAULT"
        }

    }

    private fun goHome(context: Context){
        finish()
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