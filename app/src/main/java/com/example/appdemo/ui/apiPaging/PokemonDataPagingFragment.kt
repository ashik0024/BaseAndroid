package com.example.appdemo.ui.apiPaging

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemo.R
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.common.ProviderIconCallback
import com.example.appdemo.databinding.FragmentPokemonDataPagingBinding
import com.example.appdemo.network.Result
import com.example.appdemo.network.responseClass.Pokemon
import com.example.appdemo.roomDb.UserInfoDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PokemonDataPagingFragment : Fragment(), ProviderIconCallback<Pokemon> {

    private var _binding:FragmentPokemonDataPagingBinding?=null
    private val binding get() = _binding


    private lateinit var pokemonPagingAdapter: PokemonPagingAdapter<Pokemon>
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

        observeData(limit = 10, 0)

        pokemonViewModel.getAllPlayerInfo()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            pokemonPagingAdapter.loadStateFlow.collectLatest { loadStates ->

                val isLoading = loadStates.source.refresh is LoadState.Loading || loadStates.source.append is LoadState.Loading
                val isEmpty = pokemonPagingAdapter.itemCount <= 0 && !loadStates.source.refresh.endOfPaginationReached
                val isEndOfPagination = loadStates.source.append is LoadState.NotLoading && loadStates.source.append.endOfPaginationReached

                binding?.emptyView?.isVisible = isEmpty && !isLoading
                binding?.progressBar?.isVisible = isLoading
                binding?.pokePagingRec?.isVisible = !(isEmpty && isLoading)

                if (isEndOfPagination) {
                    Toast.makeText(context, "No More Data Available", Toast.LENGTH_SHORT).show()
                }

                val errorState = loadStates.source.refresh as? LoadState.Error
                    ?: loadStates.source.append as? LoadState.Error
                    ?: loadStates.source.prepend as? LoadState.Error

                errorState?.let { error ->
                    binding?.emptyView?.text="Error: ${error.error.message}"

                }
            }
        }

    }

    private fun observeData(limit: Int, offset: Int) {
        lifecycleScope.launch {
            pokemonViewModel.loadPokemonData(limit, offset).collectLatest { pagingData ->
                pokemonPagingAdapter.submitData(pagingData)
            }
        }
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