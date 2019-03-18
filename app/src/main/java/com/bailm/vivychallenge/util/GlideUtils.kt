package com.bailm.vivychallenge.util

import com.bailm.vivychallenge.data.DoctorsRepository
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

object GlideUtils{
    //
    fun buildUrlWithHeaders(url:String):GlideUrl{
        return GlideUrl(url, LazyHeaders.Builder()
            .addHeader("Authorization","Bearer ${DoctorsRepository.token}")
            .build())
    }
}