package com.example.appdemo.ui.apiPaging



import com.example.appdemo.R

import com.example.appdemo.common.BasePagingDataAdapter
import com.example.appdemo.common.ItemComparator
import com.example.appdemo.common.ProviderIconCallback




class PokemonPagingAdapter<T : Any>(
    listener: ProviderIconCallback<T>,
) : BasePagingDataAdapter<T>(listener, ItemComparator()) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_pokemon
    }
}
