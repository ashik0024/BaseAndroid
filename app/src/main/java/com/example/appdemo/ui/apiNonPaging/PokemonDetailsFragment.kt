package com.example.appdemo.ui.apiNonPaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.appdemo.R
import com.example.appdemo.databinding.FragmentPokemonDetailsBinding
import com.example.appdemo.network.Result
import com.example.appdemo.ui.apiPaging.PokemonDataPagingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding?=null
    private val binding get() = _binding
    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentPokemonDetailsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        observePokemonDetails()
        pokemonDetailsViewModel.fetchPokemonData(name.toString())

    }

    private fun observePokemonDetails() {
        pokemonDetailsViewModel.pokemonDetailsData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    val pokemonDatta = result.data
                    Log.d("pokemonDatta ", "pokemonDatta: "+pokemonDatta.height)
                    binding?.data=pokemonDatta

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
}