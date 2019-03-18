package com.bailm.vivychallenge.data.api.model.imagedetails

data class ImageDetailResponse(
    val description: String,
    val `file`: File,
    val filename: String,
    val inputStream: InputStream,
    val `open`: Boolean,
    val readable: Boolean,
    val uri: Uri,
    val url: Url
)