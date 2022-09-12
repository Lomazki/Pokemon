package by.lomazki.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.lomazki.pokemon.PokemonAdapter
import by.lomazki.pokemon.R
import by.lomazki.pokemon.databinding.FragmentMainBinding
import by.lomazki.pokemon.models.GeneralInfo
import by.lomazki.pokemon.models.Pokemon
import by.lomazki.pokemon.services.ApiService
import com.google.android.material.divider.MaterialDividerItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        PokemonAdapter(
            context = requireContext(),
            onPokemonClicked = {
                findNavController().navigate(
                    MainFragmentDirections.actionFragmentMainToFragmentPokemon(it.url, it.name)
                )
            })
    }
    private var currentRequest: Call<GeneralInfo>? = null
    private val apiService = ApiService()
    var currentPokemons = mutableListOf<Pokemon>()

    //-----------------------------------------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Запрос
        executeRequest()

        with(binding) {
            recyclerViewPokemon.adapter = adapter

            // Decorator
            recyclerViewPokemon.addItemDecoration(
                MaterialDividerItemDecoration(
                    requireContext(),
                    MaterialDividerItemDecoration.VERTICAL
                )
            )

            // Обрабатываем "поиск" в тулбаре
            toolbarMain
                .menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        adapter.submitList(currentPokemons.filter { it.name.contains(newText) })
                        return true
                    }
                })

            swipeRefresh.setOnRefreshListener {
                executeRequest {
                    swipeRefresh.isRefreshing = false
                }
            }
        }
        executeRequest()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //--------------------------------------------------------------------------------------------------
    // Метод для SwipeToRefresh
    private fun executeRequest(
        onRequestFinished: () -> Unit = {}
    ) {

        val finishedRequest = {
            onRequestFinished()
            currentRequest = null
        }

        currentRequest?.cancel()
        currentRequest = apiService.getPokemonApi()
            .getGeneralRequest()
            .apply {
                enqueue(object : Callback<GeneralInfo> {
                    override fun onResponse(
                        call: Call<GeneralInfo>,
                        response: Response<GeneralInfo>
                    ) {
                        if (response.isSuccessful) {
                            val pokemonList = response.body()?.pokemonList ?: return
                            currentPokemons.addAll(pokemonList)
                            adapter.submitList(currentPokemons)
                        } else {
                            handleException(HttpException(response))
                        }
                        finishedRequest()
                    }

                    override fun onFailure(call: Call<GeneralInfo>, t: Throwable) {
                        if (!call.isCanceled) {
                            handleException(t)
                        }
                        finishedRequest()
                    }
                })
            }
    }

    //--------------------------------------------------------------------------------------------------
    private fun handleException(e: Throwable) {
        Toast.makeText(requireContext(), e.message ?: "Something went wrong", Toast.LENGTH_SHORT)
            .show()
    }
}