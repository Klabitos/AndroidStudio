package com.klabitos.pokemon.service

import com.klabitos.pokemon.model.Pokemon
import com.klabitos.pokemon.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface API_Service {
    @GET(".") //SI NO PASAMOS PARAMENTROS
    suspend fun getAllPokemon(): Response<PokemonResponse>

    @GET //SI NO PASAMOS PARAMENTROS
    suspend fun getAPokemon(@Url id:String): Response<Pokemon>
}
