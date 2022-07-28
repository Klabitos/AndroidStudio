package com.klabitos.pokemon.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name") var name:String,
    @SerializedName("id") var id: String
)
