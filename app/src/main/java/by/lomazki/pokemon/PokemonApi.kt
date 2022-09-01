package by.lomazki.pokemon

import by.lomazki.pokemon.models.GeneralInfo
import by.lomazki.pokemon.models.Pokemon
import retrofit2.Call
import retrofit2.http.GET

interface PokemonApi {

    @GET("pokemon?offset=0&limit=300")
    fun getGeneralRequest(): Call<GeneralInfo>

    @GET(" ")
    fun getPokemon(): Call<Pokemon>

}