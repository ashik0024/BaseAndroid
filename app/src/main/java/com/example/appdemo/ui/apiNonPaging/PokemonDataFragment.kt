package com.example.appdemo.ui.apiNonPaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.R
import com.example.appdemo.network.repository.GetPokemonService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.databinding.FragmentPokemonDataBinding
import com.example.appdemo.network.Result
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.databinding.FragmentPokemonDataPagingBinding


class PokemonDataFragment : Fragment(), BaseListItemCallback<Pokemon> {

    private var _binding: FragmentPokemonDataBinding? = null
    private val binding get() = _binding
    private lateinit var pokemonViewModel: PokemonViewModel
    private lateinit var pokemonAdapter: PokemonAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?):
            View? {
        _binding = FragmentPokemonDataBinding.inflate(layoutInflater)

        // Initialize GetPokemonService (replace with DI or actual service initialization)
        val getPokemonService = GetPokemonService()

        // Initialize the ViewModel
        pokemonViewModel = ViewModelProvider(this, PokemonViewModelFactory(getPokemonService))
            .get(PokemonViewModel::class.java)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView and Adapter
        pokemonAdapter = PokemonAdapter(this)
        binding?.pokeRec?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pokemonAdapter
        }

        observeData()
        pokemonViewModel.fetchPokemonData()
    }

    private fun observeData() {
        // Observe the LiveData from the ViewModel
        pokemonViewModel.pokemonData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    val pokemonList = result.data
                    Toast.makeText(requireContext(), "Success: ${pokemonList.size}", Toast.LENGTH_LONG).show()
                    pokemonAdapter.removeAll()
                    pokemonAdapter.addAll(pokemonList)
                    pokemonAdapter.notifyDataSetChanged()
                    Log.d("PokemonData1", "Response: ${result.data}")
                    Log.d("PokemonData2", "Response: "+pokemonList)
                }
                is Result.Error -> {
                    // Handle error
                    val error = result.exception
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> {
                    // Handle loading state (e.g., show a progress bar)
                    // Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onItemClicked(item: Pokemon) {
        super.onItemClicked(item)
        val bundleData = Bundle().apply {
            putString("name", item.name)
            putString("id",item.id.toString())
        }
        findNavController().navigate(R.id.action_to_PokemonDetails,bundleData)
    }
}
