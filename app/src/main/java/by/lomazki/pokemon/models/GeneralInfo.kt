package by.lomazki.pokemon.models

import com.google.gson.annotations.SerializedName

data class GeneralInfo(

    @SerializedName("results")
    val pokemonList: List<Pokemon> = emptyList()

)
