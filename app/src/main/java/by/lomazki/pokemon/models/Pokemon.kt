package by.lomazki.pokemon.models

import by.lomazki.pokemon.constants.Constants.AVATAR_URL
import com.google.gson.annotations.SerializedName

data class Pokemon(

    val name: String,

    var id: Int,

    val height: Int,

    val weight: Int,

    var url: String = "",

    var avatarUrl: String = ""

) {
    init {
        this.avatarUrl = AVATAR_URL.format(id)
    }
}
