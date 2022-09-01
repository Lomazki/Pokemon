package by.lomazki.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.lomazki.pokemon.databinding.FragmentPokemonBinding
import by.lomazki.pokemon.models.Pokemon
import by.lomazki.pokemon.services.ApiService
import coil.load
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<PokemonFragmentArgs>()

    private var currentPokemon: Pokemon? = null

    private val apiService = ApiService()

    //--------------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPokemonBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val urlPokemon = args.urlPokemon
        apiService.getPokemonApi(urlPokemon).getPokemon()
            .apply {
                enqueue(object : Callback<Pokemon> {
                    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                        if (response.isSuccessful) {
                            val body = requireNotNull(response.body())
                            currentPokemon = Pokemon(
                                body.name,
                                body.id,
                                body.height,
                                body.weight
                            )
                        }
                        with(binding) {
                            ivPokemon.load(currentPokemon?.avatarUrl)
                            tvNamePokemon.text = currentPokemon?.name
                            tvHeight.text = currentPokemon?.height.toString()
                            tvWeight.text = currentPokemon?.weight.toString()
                        }
                    }

                    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}