package by.lomazki.pokemon.services

import by.lomazki.pokemon.PokemonApi
import by.lomazki.pokemon.constants.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiService {

    fun getPokemonApi(url: String = BASE_URL): PokemonApi {

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create()
    }

}