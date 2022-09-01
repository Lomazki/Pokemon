package by.lomazki.pokemon.models

import by.lomazki.pokemon.constants.Constants.Companion.AVATAR_URL
import com.google.gson.annotations.SerializedName

data class Pokemon(

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    var id: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("url")
    var url: String = "",

    val avatarUrl: String = AVATAR_URL.format(id)


)
