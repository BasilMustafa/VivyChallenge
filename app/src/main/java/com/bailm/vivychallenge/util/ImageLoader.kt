package com.bailm.vivychallenge.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


/**
 * A wrapper class for image loading
 */
class ImageLoader {

    companion object {
        const val EMPTY_IMAGE = "";
        fun load(imageView: ImageView, imageUrl: String, @DrawableRes holderId: Int) {
            if (imageUrl != EMPTY_IMAGE) {
                Glide.with(imageView.context)
                        .load(GlideUtils.buildUrlWithHeaders(imageUrl))
                        .placeholder(holderId)
                        .error(holderId)

                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
            } else {
                Glide.with(imageView.context)
                        .load(holderId)
                        .placeholder(holderId)
                        .error(holderId)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
            }
        }

        fun clear(imageView: ImageView) {
            Glide.with(imageView.context).clear(imageView)
        }
    }


}