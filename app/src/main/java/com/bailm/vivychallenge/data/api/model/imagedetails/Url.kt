package com.bailm.vivychallenge.data.api.model.imagedetails

data class Url(
    val authority: String,
    val content: Content,
    val defaultPort: Int,
    val `file`: String,
    val host: String,
    val path: String,
    val port: Int,
    val protocol: String,
    val query: String,
    val ref: String,
    val userInfo: String
)