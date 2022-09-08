package by.lomazki.pokemon

import by.lomazki.pokemon.models.GeneralInfo
import by.lomazki.pokemon.models.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon?offset=0&limit=300")
    fun getGeneralRequest(): Call<GeneralInfo>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: String): Call<Pokemon>

//    @GET(" ")
//    fun getPokemon(): Call<Pokemon>

}

/*
Закоменченый метод тоже работает. Но! надо удалить лишний аргумент в
navigation.xml, раскоментить в PokemonFragment.kt строчку 43, убрать
второй аргумент в строчке 33 файла MainFragment.kt
 */

