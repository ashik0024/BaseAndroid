package com.example.appdemo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.appdemo.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        imageView.load(url) {
            placeholder(R.drawable.placeholder)
            error(R.drawable.errorimg)
        }
    }
}