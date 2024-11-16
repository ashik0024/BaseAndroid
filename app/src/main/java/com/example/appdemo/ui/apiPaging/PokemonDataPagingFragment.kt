package com.example.appdemo.ui.apiPaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.R
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.databinding.FragmentPokemonDataPagingBinding
import com.example.appdemo.network.Result
import com.example.appdemo.network.repository.GetPokemonPagingService
import com.example.appdemo.network.repository.GetPokemonService
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.roomDb.LocalDb
import com.example.appdemo.roomDb.UserInfoDao
import com.example.appdemo.ui.apiNonPaging.PokemonAdapter
import com.example.appdemo.ui.apiNonPaging.PokemonViewModel
import com.example.appdemo.ui.apiNonPaging.PokemonViewModelFactory


class PokemonDataPagingFragment : Fragment(),BaseListItemCallback<Pokemon> {

    private var _binding:FragmentPokemonDataPagingBinding?=null
    private val binding get() = _binding
    private lateinit var pokemonViewModel:PokemonDataPagingViewModel
    private lateinit var pokemonPagingAdapter:PokemonPagingAdapter
    private lateinit var userInfoDao: UserInfoDao



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentPokemonDataPagingBinding.inflate(inflater, container, false)

        // Initialize GetPokemonService (replace with DI or actual service initialization)
        val getPokemonPagingService = GetPokemonPagingService()
        userInfoDao = LocalDb.getDatabase(requireContext()).dao

        // Initialize the ViewModel
        pokemonViewModel = ViewModelProvider(this, PokemonPagingViewModelFactory(getPokemonPagingService,userInfoDao))
            .get(PokemonDataPagingViewModel::class.java)
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
//                    Toast.makeText(requireContext(), "Success: ${pokemonList.size}", Toast.LENGTH_LONG).show()
                    pokemonPagingAdapter.removeAll()
                    pokemonPagingAdapter.addAll(pokemonList)
                    pokemonViewModel.saveToDatabase(pokemonList)
                    Log.d("PokemonData2", "Response: "+ pokemonViewModel.saveToDatabase(pokemonList))
//                    Log.d("PokemonData1", "Response: ${result.data}")
//                    Log.d("PokemonData2", "Response: "+pokemonList)
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
    }
}