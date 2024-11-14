package com.example.appdemo.ui.apiNonPaging


import android.graphics.Paint
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.appdemo.R
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.common.MyBaseAdapter
import com.example.appdemo.common.MyViewHolder
import com.example.appdemo.network.responseClass.Pokemon

class PokemonAdapter(
    cb: BaseListItemCallback<Pokemon>
) : MyBaseAdapter<Pokemon>(cb) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_pokemon
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val obj = getItem(position)
        obj.let {

            val name = holder.itemView.findViewById<TextView>(R.id.name)
            name.text = obj.name?:"test"
        }

    }
}
