package com.example.appdemo.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

open class BasePagingDataAdapter<T: Any>(
    val callback: BaseListItemCallback<T>? = null,
    itemDiffUtil: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, MyViewHolder>(itemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        adjustLayout(binding)
        return MyViewHolder(binding)
    }

    open fun adjustLayout(binding: ViewDataBinding){}

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = getItem(position)
        obj?.let {
            holder.bind(obj, callback, position)
        }
    }

    fun getItemByIndex(idx: Int): T? {
        return getItem(idx)
    }
}