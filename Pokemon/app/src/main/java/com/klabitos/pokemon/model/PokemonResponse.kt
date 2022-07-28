package com.klabitos.pokemon.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count") var numberPokemon:String,
    @SerializedName("results") var pokemonList: List<Pokemon>
)
