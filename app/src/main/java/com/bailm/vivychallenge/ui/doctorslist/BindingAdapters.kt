package com.bailm.vivychallenge.ui.doctorslist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bailm.vivychallenge.BuildConfig
import com.bailm.vivychallenge.R
import com.bailm.vivychallenge.util.ImageLoader

@BindingAdapter(value = ["userId", "imageId"])
fun load(imageView: ImageView, userId: String, imageId: String?) {
    if (imageId != null) { // images without an image url
        val imageUrl: String = BuildConfig.BASE_URL + "/api/users/$userId/files/$imageId"
        ImageLoader.load(imageView, imageUrl, R.drawable.no_doctor_avatar)
    } else {
        ImageLoader.load(imageView, ImageLoader.EMPTY_IMAGE, R.drawable.no_doctor_avatar)
    }


}