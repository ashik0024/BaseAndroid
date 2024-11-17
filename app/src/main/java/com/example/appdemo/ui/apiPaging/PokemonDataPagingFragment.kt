package com.example.appdemo.ui.apiPaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.R
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.databinding.FragmentPokemonDataPagingBinding
import com.example.appdemo.network.Result
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.roomDb.UserInfoDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDataPagingFragment : Fragment(),BaseListItemCallback<Pokemon> {

    private var _binding:FragmentPokemonDataPagingBinding?=null
    private val binding get() = _binding

    private lateinit var pokemonPagingAdapter:PokemonPagingAdapter
    private lateinit var userInfoDao: UserInfoDao
    private val pokemonViewModel: PokemonDataPagingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentPokemonDataPagingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonPagingAdapter = PokemonPagingAdapter(this)
        binding?.pokePagingRec?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pokemonPagingAdapter
        }

        observeData()
        pokemonViewModel.fetchPokemonDataPaging(10,0)
        pokemonViewModel.getAllPlayerInfo()
    }
    private fun observeData() {
        // Observe the LiveData from the ViewModel
        pokemonViewModel.pokemonDataPaging.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    val pokemonList = result.data
                    pokemonPagingAdapter.removeAll()
                    pokemonPagingAdapter.addAll(pokemonList)
                    pokemonViewModel.saveToDatabase(pokemonList)
                    Log.d("PokemonData2", "Response: "+ pokemonViewModel.saveToDatabase(pokemonList))

                }
                is Result.Error -> {
                    // Handle error
                    val error = result.exception
                    Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> {

                     Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onItemClicked(item: Pokemon) {
        super.onItemClicked(item)
    }
}