package com.example.appdemo

import android.widget.Button
import android.widget.TextView
import com.example.appdemo.common.BaseListItemCallback
import com.example.appdemo.common.MyBaseAdapter
import com.example.appdemo.common.MyViewHolder
import com.example.appdemo.common.ProviderIconCallback
import com.example.appdemo.roomDb.UserInfo


class ContactAdapter(
    callback: BaseListItemCallback<UserInfo>? = null
) : MyBaseAdapter<UserInfo>(callback) {

    // Define the layout ID to be used for each item (from item_contact.xml)
    override fun getLayoutIdForPosition(position: Int): Int {
        return R.layout.item_contact // Make sure you have an item_contact.xml layout in your resources
    }


}