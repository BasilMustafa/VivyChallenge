package com.bailm.vivychallenge.data.api.model.search

import java.io.Serializable

data class Doctor(
    var address: String,
    var email: Any,
    var highlighted: Boolean,
    var id: String,
    var lat: Double,
    var lng: Double,
    var name: String,
    var openingHours: List<String>,
    var phoneNumber: String,
    var photoId: String,
    var rating: Float,
    var reviewCount: Int,
    var source: String,
    var specialityIds: List<Int>,
    var website: String
):Serializable