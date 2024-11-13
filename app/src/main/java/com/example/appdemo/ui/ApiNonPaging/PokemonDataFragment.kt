package com.example.appdemo.ui.ApiNonPaging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo.Network.Repository.GetPokemonService
import com.example.appdemo.databinding.FragmentPokemonDataBinding
import com.example.appdemo.Network.Result


class PokemonDataFragment : Fragment() {
    private var _binding:FragmentPokemonDataBinding?= null
    private val binding get() = _binding
    private lateinit var pokemonViewModel: PokemonViewModel

    private lateinit var getPokemonService: GetPokemonService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPokemonDataBinding.inflate(inflater, container, false)

        // Initialize GetPokemonService here, or inject it if using DI
        getPokemonService = GetPokemonService() // replace with your actual service initialization

        // Create the ViewModel using the factory
        pokemonViewModel = ViewModelProvider(this, PokemonViewModelFactory(getPokemonService))
            .get(PokemonViewModel::class.java)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        pokemonViewModel.fetchPokemonData()
    }

    private fun observeData() {
        // Observe LiveData
        pokemonViewModel.pokemonData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    // Handle successful data loading
                    val pokemonList = result.data
                    Toast.makeText(requireContext(), "Success: ${pokemonList.count}", Toast.LENGTH_LONG).show()
                }
                is Result.Error -> {
                    // Handle error
                    val error = result.exception
                    // Show error message (e.g., using a Toast or a Snackbar)
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
                is Result.Loading ->{
//                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}