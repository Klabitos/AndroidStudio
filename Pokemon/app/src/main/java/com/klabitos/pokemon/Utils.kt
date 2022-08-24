package com.klabitos.pokemon

import android.graphics.BitmapFactory
import android.widget.ImageView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream


class Utils {
    fun loadImage(image: InputStream, idBindingImage: ImageView){
        val bitmap = BitmapFactory.decodeStream(image)
        idBindingImage.setImageBitmap(bitmap)
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/pokemon/") //importante que la base acabae en /
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun capitalizeFirstLetter(word:String):String{
        return word.substring(0,1).uppercase()+word.substring(1).lowercase()
    }

}