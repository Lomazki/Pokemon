package by.lomazki.pokemon

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.lomazki.pokemon.databinding.ItemPokemonBinding
import by.lomazki.pokemon.models.Pokemon

class PokemonAdapter(
    context: Context,
    private val onPokemonClicked: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            binding = ItemPokemonBinding.inflate(layoutInflater, parent, false),
            onPokemonClicked = onPokemonClicked
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.height == newItem.height &&
                        oldItem.weight == newItem.weight
            }
        }
    }
}


class PokemonViewHolder(
    private val binding: ItemPokemonBinding,
    val onPokemonClicked: (Pokemon) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon) {
        with(binding) {
            tvNamePokemon.text = pokemon.name
            root.setOnClickListener { onPokemonClicked(pokemon) }
        }

    }
}
